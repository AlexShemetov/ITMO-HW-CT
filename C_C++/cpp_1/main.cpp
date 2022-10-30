#include "phonebook.h"
#include "quicksort.h"
#include "return_codes.h"
#include <fstream>

template< typename T >
void read(std::ifstream &in, T *data, std::size_t size)
{
	for (std::size_t i = 0; i < size; i++)
	{
		in >> data[i];
	}
	in.close();
}

template< typename T >
int write(T *data, std::size_t size, char *path_out)
{
	std::ofstream out(path_out);
	if (!out.is_open())
	{
		fprintf(stderr, "%s\n", "File already exists");
		return ERROR_ALREADY_EXISTS;
	}
	for (std::size_t i = 0; i < size; i++)
	{
		out << data[i] << "\n";
	}
	out.close();
	return ERROR_SUCCESS;
}

template< typename T, bool descending >
int sort(std::ifstream &in, std::size_t size, char *path_out)
{
	int err_code = ERROR_SUCCESS;
	try
	{
		T *data = new T[size];
		read< T >(in, data, size);
		quicksort< T, descending >(data, 0, size - 1);
		err_code = write< T >(data, size, path_out);
		delete[] data;
	} catch (const std::bad_array_new_length e)
	{
		fprintf(stderr, "Not enough memory\n");
		return ERROR_NOT_ENOUGH_MEMORY;
	}
	return err_code;
}

int main(int argc, char **argv)
{
	if (argc != 3)
	{
		fprintf(stderr, "Usage: <currFile> <inputFile> <outputFile>\n");
		return ERROR_INVALID_PARAMETER;
	}

	std::ifstream in(argv[1]);
	if (!in.is_open())
	{
		fprintf(stderr, "File not found\n");
		return ERROR_FILE_NOT_FOUND;
	}

	std::string type, mode;
	std::size_t size;
	in >> type >> mode >> size;
	bool descending;
	if (mode == "descending")
	{
		descending = true;
	}
	else if (mode == "ascending")
	{
		descending = false;
	}
	else
	{
		fprintf(stderr, "%s", "Invalid data\n");
		return ERROR_INVALID_DATA;
	}

	if (type == "int")
	{
		if (descending)
		{
			return sort< int, true >(in, size, argv[2]);
		}
		else
		{
			return sort< int, false >(in, size, argv[2]);
		}
	}
	else if (type == "float")
	{
		if (descending)
		{
			return sort< float, true >(in, size, argv[2]);
		}
		else
		{
			return sort< float, false >(in, size, argv[2]);
		}
	}
	else if (type == "phonebook")
	{
		if (descending)
		{
			return sort< Phonebook, true >(in, size, argv[2]);
		}
		else
		{
			return sort< Phonebook, false >(in, size, argv[2]);
		}
	}
	else
	{
		return ERROR_INVALID_DATA;
	}
}