#pragma once

#include <iostream>

struct Phonebook
{
	const bool operator>(const Phonebook &p) const;
	friend std::ostream &operator<<(std::ostream &os, const Phonebook &p);
	friend std::istream &operator>>(std::istream &is, Phonebook &p);
	Phonebook(std::string surname, std::string name, std::string patronymic, unsigned long long number);
	Phonebook();

  private:
	std::string name;
	std::string surname;
	std::string patronymic;
	unsigned long long number;
};
std::ostream &operator<<(std::ostream &os, const Phonebook &p);
std::istream &operator>>(std::istream &is, Phonebook &p);