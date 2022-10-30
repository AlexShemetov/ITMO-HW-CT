#pragma once
#include "return_codes.h"
#include <string_view>

#include <climits>
#include <iomanip>
#include <iostream>
#include <limits>
#include <stdlib.h>
#include <vector>

#define LN_BASE 1000000000

const std::size_t MAX_SIZE = std::numeric_limits< std::size_t >::max();

class LN
{
  public:
	LN(long long num = 0LL);
	explicit LN(const std::string_view &str);
	explicit LN(const char chrs[]);
	LN(const LN &ln);
	LN(LN &&ln) noexcept;

	LN operator+(const LN &ln) const;
	LN operator-(const LN &ln) const;
	LN operator-() const;
	LN operator*(const LN &ln) const;
	LN operator/(const LN &ln) const;
	LN operator/(int num) const;
	LN operator%(const LN &ln) const;

	LN &operator=(LN &&ln) noexcept;
	LN &operator=(const LN &ln);
	LN &operator+=(const LN &ln);
	LN &operator-=(const LN &ln);
	LN &operator*=(const LN &ln);
	LN &operator/=(const LN &ln);
	LN &operator/=(int num);
	LN &operator%=(const LN &ln);

	bool operator==(const LN &ln) const;
	bool operator!=(const LN &ln) const;
	bool operator>(const LN &ln) const;
	bool operator>=(const LN &ln) const;
	bool operator<(const LN &ln) const;
	bool operator<=(const LN &ln) const;

	operator bool() const;
	operator long long() const;

	friend std::ostream &operator<<(std::ostream &os, const LN &ln);
	friend std::istream &operator>>(std::istream &is, LN &ln);
	friend LN sqrt(const LN &ln);

  private:
	std::pair< LN, LN > divmod(const LN &ln1, const LN &ln2);
	LN abs_ln(const LN &ln);

	bool _sign = false;
	std::vector< int > _num;
	bool _is_NaN = false;
	int _get_num(std::size_t index) const { return index < _num.size() ? _num[index] : 0; };
	bool _is_zero() const;
	void _delete_zeroes();
};

LN operator"" _ln(const char num[]);