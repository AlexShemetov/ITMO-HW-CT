#include "return_codes.h"
#include "solution.h"

int main(int argc, char **argv)
{
	if (argc != 3)
	{
		printf("Usage: <source file> <fileIn> <fileOut>");
		return ERROR_INVALID_PARAMETER;
	}

	FILE *in;
	in = fopen(argv[1], "r");

	if (in == NULL)
	{
		printf("File %s not found", argv[1]);
		return ERROR_FILE_NOT_FOUND;
	}

	size_t n;
	fscanf(in, "%zu", &n);

	int err = ERROR_SUCCESS;
	float **matrix = malloc(sizeof(float *) * n);
	if (matrix == NULL)
	{
		fclose(in);
		printf("Not enough memory");
		return ERROR_NOT_ENOUGH_MEMORY;
	}
	else
	{
		for (size_t i = 0; i < n; i++)
		{
			matrix[i] = malloc(sizeof(float) * (n + 1));
			if (matrix[i] == NULL)
			{
				err = ERROR_NOT_ENOUGH_MEMORY;
			}
		}
	}

	if (err == ERROR_SUCCESS)
	{
		for (size_t i = 0; i < n; i++)
		{
			for (size_t j = 0; j < n + 1; j++)
			{
				fscanf(in, "%f", &matrix[i][j]);
			}
		}
	}
	fclose(in);

	if (err != ERROR_SUCCESS)
	{
		goto error;
	}

	int result = gauss(n, matrix);

	FILE *out;
	out = fopen(argv[2], "w");
	if (out == NULL)
	{
		err = ERROR_FILE_EXISTS;
		goto error;
	}

	switch (result)
	{
	case -1:
		fprintf(out, "%s\n", "no solution");
		break;
	case 0:
		for (size_t i = 0; i < n; i++)
		{
			fprintf(out, "%g\n", matrix[i][n]);
		}
		break;
	case 1:
		fprintf(out, "%s\n", "many solutions");
		break;
	default:
		err = ERROR_UNKNOWN;
	}

	fclose(out);

error:
	for (size_t i = 0; i < n; i++)
	{
		free(matrix[i]);
	}
	free(matrix);
	switch (err)
	{
	case ERROR_SUCCESS:
		return ERROR_SUCCESS;
	case ERROR_NOT_ENOUGH_MEMORY:
		printf("Not enough memory");
		return ERROR_NOT_ENOUGH_MEMORY;
	case ERROR_FILE_EXISTS:
		printf("File out %s not open", argv[2]);
		return ERROR_FILE_EXISTS;
	default:
		printf("Unknow error");
		return ERROR_UNKNOWN;
	}
}