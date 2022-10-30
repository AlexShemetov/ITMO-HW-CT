#include "phonebook.h"

Phonebook::Phonebook(std::string surname, std::string name, std::string patronymic, unsigned long long number) :
	surname(std::move(surname)), name(std::move(name)), patronymic(std::move(patronymic)), number(number)
{
}

Phonebook::Phonebook() {}

const bool Phonebook::operator>(const Phonebook &p) const
{
	if (surname != p.surname)
	{
		return surname > p.surname;
	}
	else
	{
		if (name != p.name)
		{
			return name > p.name;
		}
		else
		{
			if (patronymic != p.patronymic)
			{
				return patronymic > p.patronymic;
			}
			else
			{
				return number > p.number;
			}
		}
	}
}

std::ostream &operator<<(std::ostream &os, const Phonebook &p)
{
	return os << p.surname << " " << p.name << " " << p.patronymic << " " << p.number;
}

std::istream &operator>>(std::istream &is, Phonebook &p)
{
	return is >> p.surname >> p.name >> p.patronymic >> p.number;
}