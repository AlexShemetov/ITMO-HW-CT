#include "error_messages.h"
#include "return_codes.h"
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

#if defined(ZLIB)
	#include <zlib.h>
#elif defined(LIBDEFLATE)
	#include <libdeflate.h>
#elif defined(ISAL)
	#include <include/igzip_lib.h>
#else
	#error "Not sopported library"
#endif

typedef unsigned char hex;
typedef unsigned int uint;

int isIHDR(const hex *name)
{
	return name[0] == 'I' && name[1] == 'H' && name[2] == 'D' && name[3] == 'R';
}

int isIDAT(const hex *name)
{
	return name[0] == 'I' && name[1] == 'D' && name[2] == 'A' && name[3] == 'T';
}

int isIEND(const hex *name)
{
	return name[0] == 'I' && name[1] == 'E' && name[2] == 'N' && name[3] == 'D';
}

int isPLTE(const hex *name)
{
	return name[0] == 'P' && name[1] == 'L' && name[2] == 'T' && name[3] == 'E';
}

uint hexToInt(const hex *num, const int startIndex, const int size)
{
	int res = 0;
	int step = 1;
	for (int i = size + startIndex - 1; i > startIndex - 1; i--, step *= 256)
	{
		res += ((int)num[i]) * step;
	}
	return res;
}

typedef struct
{
	hex *data;
	size_t size, lastPos;
} compressData;

typedef struct
{
	char *type;
	hex *data;
	int bpp;
	size_t width, height;
} PNM;

typedef struct
{
	int code;
	char *message;
} ERROR;

uint getNextChunkLen(FILE *filePNG, ERROR *err)
{
	hex len[4];
	if (fread(len, sizeof(hex), 4, filePNG) != 4)
	{
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
		return 0;
	}
	return hexToInt(len, 0, 4);
}

void getNextChunkName(FILE *filePNG, hex *name, ERROR *err)
{
	if (fread(name, sizeof(hex), 4, filePNG) != 4)
	{
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
	}
}

int isPNG(FILE *filePNG)
{
	const hex magicNumbers[] = { 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };
	hex fileMagicNumbers[8];
	if (fread(fileMagicNumbers, sizeof(hex), 8, filePNG) != 8)
	{
		return 0;
	}

	for (int i = 0; i < 8; i++)
	{
		if (magicNumbers[i] != fileMagicNumbers[i])
		{
			return 0;
		}
	}
	return 1;
}

void readHeader(FILE *filePNG, ERROR *err, PNM *pnm)
{
	hex buff[17];
	if (fread(buff, sizeof(hex), 17, filePNG) != 17)
	{
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
		return;
	}
	pnm->width = hexToInt(buff, 0, 4);
	pnm->height = hexToInt(buff, 4, 4);
	if (buff[8] != 0x08)
	{
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
		return;
	}
	switch (buff[9])
	{
	case 0x00:
		pnm->type = "P5";
		pnm->bpp = 1;
		break;
	case 0x02:
		pnm->type = "P6";
		pnm->bpp = 3;
		break;
	default:
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
		return;
	}
}

void useFilterSub(size_t indexStart, PNM *pnm)
{
	for (size_t i = pnm->bpp + 1 + indexStart; i < pnm->width * pnm->bpp + 1 + indexStart; i++)
	{
		pnm->data[i] += pnm->data[i - pnm->bpp];
	}
}

void useFilterUp(size_t indexStart, PNM *pnm)
{
	for (size_t i = 1 + indexStart; i < pnm->width * pnm->bpp + 1 + indexStart; i++)
	{
		pnm->data[i] += pnm->data[i - (pnm->width * pnm->bpp + 1)];
	}
}

void useFilterAverage(size_t indexStart, PNM *pnm)
{
	for (size_t i = 1 + indexStart; i < pnm->bpp + indexStart + 1; i++)
	{
		pnm->data[i] += pnm->data[i - (pnm->width * pnm->bpp + 1)] / 2;
	}

	for (size_t i = 1 + pnm->bpp + indexStart; i < pnm->width * pnm->bpp + 1 + indexStart; i++)
	{
		pnm->data[i] += (pnm->data[i - pnm->bpp] + pnm->data[i - (pnm->width * pnm->bpp + 1)]) / 2;
	}
}

hex paethPredictor(hex left, hex above, hex upperLeft)
{
	int p = (int)left + (int)above - (int)upperLeft;
	int pl = abs(p - left);
	int pa = abs(p - above);
	int pu = abs(p - upperLeft);
	if (pl <= pa && pl <= pu)
	{
		return left;
	}
	else if (pa <= pu)
	{
		return above;
	}
	else
	{
		return upperLeft;
	}
}

void useFilterPaeth(size_t indexStart, PNM *pnm)
{
	for (size_t i = 1 + indexStart; i < pnm->bpp + indexStart + 1; i++)
	{
		pnm->data[i] += paethPredictor(0, pnm->data[i - (pnm->width * pnm->bpp + 1)], 0);
	}

	for (size_t i = 1 + pnm->bpp + indexStart; i < pnm->width * pnm->bpp + 1 + indexStart; i++)
	{
		pnm->data[i] += paethPredictor(
			pnm->data[i - pnm->bpp],
			pnm->data[i - (pnm->width * pnm->bpp + 1)],
			pnm->data[i - (pnm->width * pnm->bpp + 1) - pnm->bpp]);
	}
}

void readData(size_t lenIDAT, FILE *filePNG, ERROR *err, compressData *compDATA)
{
	if (compDATA->size == 0)
	{
		compDATA->lastPos = 0;
		compDATA->size = lenIDAT;
		compDATA->data = malloc(sizeof(hex) * lenIDAT);
		if (compDATA->data == NULL)
		{
			err->code = ERROR_NOT_ENOUGH_MEMORY;
			err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
			return;
		}
	}
	else
	{
		compDATA->lastPos = compDATA->size;
		compDATA->size += lenIDAT;
		hex *arr = realloc(compDATA->data, compDATA->size);
		if (arr)
		{
			compDATA->data = arr;
		}
		else
		{
			err->code = ERROR_NOT_ENOUGH_MEMORY;
			err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
			return;
		}
	}
	hex *data = malloc(sizeof(hex) * lenIDAT);
	if (data == NULL)
	{
		free(compDATA->data);
		err->code = ERROR_NOT_ENOUGH_MEMORY;
		err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
		return;
	}
	if (fread(data, sizeof(hex), lenIDAT, filePNG) != lenIDAT)
	{
		free(compDATA->data);
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
	}
	for (int i = 0; i < lenIDAT; i++)
	{
		compDATA->data[i + compDATA->lastPos] = data[i];
	}
}

void decompressData(compressData *compData, PNM *pnm, ERROR *err)
{
	pnm->data = malloc(sizeof(hex *) * pnm->height * (pnm->width * pnm->bpp + 1));
	if (pnm->data == NULL)
	{
		err->code = ERROR_NOT_ENOUGH_MEMORY;
		err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
		free(compData->data);
		return;
	}
#if defined(ZLIB)
	size_t size = pnm->height * (pnm->width * pnm->bpp + 1);
	switch (uncompress(pnm->data, &size, compData->data, compData->size))
	{
	case (Z_MEM_ERROR):
		err->code = ERROR_NOT_ENOUGH_MEMORY;
		err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
		break;
	case (Z_BUF_ERROR):
		err->code = ERROR_NOT_ENOUGH_MEMORY;
		err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
		break;
	case (Z_DATA_ERROR):
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
		break;
	}
#elif defined(LIBDEFLATE)
	switch (libdeflate_zlib_decompress(libdeflate_alloc_decompressor(), compData->data, compData->size, pnm->data, pnm->height * (pnm->width * pnm->bpp + 1), NULL))
	{
	case (LIBDEFLATE_BAD_DATA):
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
		break;
	case (LIBDEFLATE_SHORT_OUTPUT):
		err->code = ERROR_NOT_ENOUGH_MEMORY;
		err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
		break;
	case (LIBDEFLATE_INSUFFICIENT_SPACE):
		err->code = ERROR_NOT_ENOUGH_MEMORY;
		err->message = ERRORMESSAGE_NOT_ENOUGH_MEMORY;
		break;
	}
#elif defined(ISAL)
	struct inflate_state uncomData;
	isal_inflate_init(&uncomData);
	uncomData.next_in = compData->data;
	uncomData.avail_in = compData->size;
	uncomData.next_out = pnm->data;
	uncomData.avail_out = pnm->height * (pnm->width * pnm->bpp + 1);
	uncomData.crc_flag = IGZIP_ZLIB;
	if (isal_inflate(&uncomData) != COMP_OK)
	{
		err->code = ERROR_INVALID_DATA;
		err->message = ERRORMESSAGE_INVALID_DATA;
	}
#endif
	free(compData->data);
	if (err->code != ERROR_SUCCESS)
	{
		free(pnm->data);
	}
}

void useFilter(PNM *pnm, ERROR *err)
{
	for (size_t i = 0; i < pnm->height * (pnm->width * pnm->bpp + 1); i += pnm->width * pnm->bpp + 1)
	{
		switch (pnm->data[i])
		{
		case (0):
			break;
		case (1):
			useFilterSub(i, pnm);
			break;
		case (2):
			useFilterUp(i, pnm);
			break;
		case (3):
			useFilterAverage(i, pnm);
			break;
		case (4):
			useFilterPaeth(i, pnm);
			break;
		default:
			err->code = ERROR_INVALID_DATA;
			err->message = ERRORMESSAGE_INVALID_DATA;
			return;
		}
	}
}

void writePNM(PNM *pnm, char *filePath, ERROR *err)
{
	FILE *out = fopen(filePath, "wb");
	if (out == NULL)
	{
		err->code = ERROR_FILE_EXISTS;
		err->message = ERRORMESSAGE_ALREADY_EXISTS;
		free(pnm->data);
		return;
	}
	fprintf(out, "%s\n%i %i\n%i\n", pnm->type, pnm->width, pnm->height, 255);
	for (int i = 0; i < pnm->height * (pnm->width * pnm->bpp + 1); i++)
	{
		if (i % (pnm->width * pnm->bpp + 1))
		{
			putc(pnm->data[i], out);
		}
	}
	fclose(out);
	free(pnm->data);
}

int main(int argc, char **argv)
{
	ERROR err = { ERROR_SUCCESS, ERRORMESSAGE_SUCCESS };
	if (argc != 3)
	{
		err.code = ERROR_INVALID_PARAMETER;
		err.message = ERRORMESSAGE_INVALID_PARAMETER;
		goto error;
	}

	FILE *filePNG = fopen(argv[1], "rb");
	if (filePNG == NULL)
	{
		err.code = ERROR_FILE_NOT_FOUND;
		err.message = ERRORMESSAGE_FILE_NOT_FOUND;
		goto error;
	}

	if (!isPNG(filePNG))
	{
		fclose(filePNG);
		err.code = ERROR_INVALID_DATA;
		err.message = ERRORMESSAGE_INVALID_DATA;
		goto error;
	}

	size_t len = getNextChunkLen(filePNG, &err);
	if (err.code != ERROR_SUCCESS)
	{
		fclose(filePNG);
		goto error;
	}
	hex chunkName[4];
	getNextChunkName(filePNG, chunkName, &err);
	if (err.code != ERROR_SUCCESS)
	{
		fclose(filePNG);
		goto error;
	}
	if (!isIHDR(chunkName))
	{
		fclose(filePNG);
		err.code = ERROR_INVALID_DATA;
		err.message = ERRORMESSAGE_INVALID_DATA;
		goto error;
	}

	PNM pnm;
	readHeader(filePNG, &err, &pnm);
	if (err.code != ERROR_SUCCESS)
	{
		fclose(filePNG);
		goto error;
	}

	compressData compData;
	compData.size = 0;
	len = getNextChunkLen(filePNG, &err);
	if (err.code != ERROR_SUCCESS)
	{
		fclose(filePNG);
		goto error;
	}
	getNextChunkName(filePNG, chunkName, &err);
	if (err.code != ERROR_SUCCESS)
	{
		fclose(filePNG);
		goto error;
	}
	while (!isIEND(chunkName))
	{
		if (isIDAT(chunkName))
		{
			readData(len, filePNG, &err, &compData);
			if (err.code != ERROR_SUCCESS)
			{
				fclose(filePNG);
				goto error;
			}
		}
		else
		{
			if (isPLTE(chunkName))
			{
				err.code = ERROR_INVALID_DATA;
				err.message = "Unsupported file";
				free(compData.data);
				fclose(filePNG);
				goto error;
			}
			else
			{
				if (fseek(filePNG, sizeof(hex) * len, 1))
				{
					err.code = ERROR_INVALID_DATA;
					err.message = ERRORMESSAGE_INVALID_DATA;
					free(compData.data);
					fclose(filePNG);
					goto error;
				}
			}
		}
		if (fseek(filePNG, sizeof(hex) * 4, 1))
		{
			err.code = ERROR_INVALID_DATA;
			err.message = ERRORMESSAGE_INVALID_DATA;
			free(compData.data);
			fclose(filePNG);
			goto error;
		}
		len = getNextChunkLen(filePNG, &err);
		if (err.code != ERROR_SUCCESS)
		{
			fclose(filePNG);
			goto error;
		}
		getNextChunkName(filePNG, chunkName, &err);
		if (err.code != ERROR_SUCCESS)
		{
			fclose(filePNG);
			goto error;
		}
	}
	if (fseek(filePNG, sizeof(hex) * 4, 1))
	{
		err.code = ERROR_INVALID_DATA;
		err.message = ERRORMESSAGE_INVALID_DATA;
		free(compData.data);
		fclose(filePNG);
		goto error;
	}
	fclose(filePNG);

	decompressData(&compData, &pnm, &err);
	if (err.code != ERROR_SUCCESS)
	{
		goto error;
	}
	useFilter(&pnm, &err);
	if (err.code != ERROR_SUCCESS)
	{
		goto error;
	}
	writePNM(&pnm, argv[2], &err);

error:
	fprintf(stderr, "%s", err.message);
	return err.code;
}