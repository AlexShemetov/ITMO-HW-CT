#include "LN.h"
#include "return_codes.h"

#include <fstream>
#include <iostream>

std::pair< LN, LN > read(std::vector< LN > &stack)
{
	LN r = stack.back();
	stack.pop_back();
	LN l = stack.back();
	stack.pop_back();
	return { l, r };
}

void bin_opp_void(const char opp, std::vector< LN > &stack)
{
	std::pair< LN, LN > lns = read(stack);
	try
	{
		switch (opp)
		{
		case '+':
			stack.push_back(lns.first + lns.second);
			break;
		case '-':
			stack.push_back(lns.first - lns.second);
			break;
		case '*':
			stack.push_back(lns.first * lns.second);
			break;
		case '/':
			stack.push_back(lns.first / lns.second);
			break;
		case '%':
			stack.push_back(lns.first % lns.second);
			break;
		}
	} catch (std::bad_alloc &e)
	{
		throw ERROR_NOT_ENOUGH_MEMORY;
	}
}

bool bin_opp_bool(const char opp[], std::vector< LN > &stack)
{
	std::pair< LN, LN > lns = read(stack);
	switch (opp[0])
	{
	case '=':
		return lns.first == lns.second;
	case '>':
		if (opp[1] == '=')
		{
			return lns.first >= lns.second;
		}
		return lns.first > lns.second;
	case '<':
		if (opp[1] == '=')
		{
			return lns.first <= lns.second;
		}
		return lns.first > lns.second;
	case '!':
		return lns.first != lns.second;
	default:
		throw ERROR_UNKNOWN;
	}
}

void un_opp_void(const char opp, std::vector< LN > &stack)
{
	LN ln = stack.back();
	stack.pop_back();
	if (opp == '_')
	{
		try
		{
			stack.push_back(-ln);
		} catch (std::bad_alloc &e)
		{
			throw ERROR_NOT_ENOUGH_MEMORY;
		}
	}
	else
	{
		try
		{
			stack.push_back(sqrt(ln));
		} catch (std::bad_alloc &e)
		{
			throw ERROR_NOT_ENOUGH_MEMORY;
		}
	}
}

int main(int argc, char **argv)
{
	if (argc != 3)
	{
		std::cerr << "Usage <curr_file_name> <file_input> <file_output>\n";
		return ERROR_INVALID_PARAMETER;
	}

	std::ifstream in(argv[1]);
	if (!in.is_open())
	{
		std::cerr << "File not found\n";
		return ERROR_FILE_NOT_FOUND;
	}
	std::ofstream out(argv[2]);
	if (!in.is_open())
	{
		std::cerr << "File already exists\n";
		return ERROR_ALREADY_EXISTS;
	}

	std::vector< LN > stack;
	std::string str;
	try
	{
		while (in >> str)
		{
			char opp[2] = { 0, 0 };
			switch (str[0])
			{
			case '+':
			case '*':
			case '/':
			case '%':
				bin_opp_void(str[0], stack);
				break;
			case '>':
			case '<':
			case '=':
			case '!':
				opp[0] = str[0];
				if (str.size() == 2)
				{
					opp[1] = str[1];
				}
				out << bin_opp_bool(opp, stack) << "\n";
				break;
			case '_':
			case '~':
				un_opp_void(str[0], stack);
				break;
			default:
				if (str[0] == '-' && str.size() == 1)
				{
					bin_opp_void(str[0], stack);
				}
				else
				{
					stack.push_back(LN(str));
				}
				break;
			}
		}
	} catch (const int code)
	{
		in.close();
		out.close();
		switch (code)
		{
		case ERROR_NOT_ENOUGH_MEMORY:
			std::cerr << "Not enough memory\n";
			return ERROR_NOT_ENOUGH_MEMORY;
		default:
			std::cerr << "unknown error\n";
			return ERROR_UNKNOWN;
		}
	}

	in.close();

	while (!stack.empty())
	{
		out << stack.back() << "\n";
		stack.pop_back();
	}

	out.close();

	return ERROR_SUCCESS;
}