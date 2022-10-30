#include "LN.h"

std::pair< LN, LN > LN::divmod(const LN &ln1, const LN &ln2)
{
	if (ln2._is_zero() || ln1._is_NaN || ln2._is_NaN)
	{
		LN ln;
		ln._is_NaN = true;
		return { ln, ln };
	}
	LN l = 0_ln, r = std::max(ln1, ln2);
	while (r - l > 1_ln)
	{
		LN mid = (l + r) / 2;
		if (mid * ln2 > ln1)
		{
			r = mid;
		}
		else
		{
			l = mid;
		}
	}
	r = (r + 1_ln) * ln2 <= ln1 ? (r + 1_ln) * ln2 : r;
	if (r * ln2 > ln1)
	{
		r -= 1_ln;
	}
	r._delete_zeroes();
	return { r, ln1 - r * ln2 };
}

LN sqrt(const LN &ln)
{
	LN l = 0_ln, r = ln;
	if (ln < 0_ln || ln._is_NaN)
	{
		l._is_NaN = true;
		return l;
	}
	while (r - l > 1_ln)
	{
		LN mid = (l + r) / 2;
		if (mid * mid >= ln)
		{
			r = mid;
		}
		else
		{
			l = mid;
		}
	}
	r = (r + 1_ln) * (r + 1_ln) <= ln ? (r + 1_ln) * (r + 1_ln) : r;
	if (r * r > ln)
	{
		r -= 1_ln;
	}
	r._delete_zeroes();
	return r;
}

LN LN::abs_ln(const LN &ln)
{
	if (ln < 0_ln)
	{
		return -ln;
	}
	return ln;
}

LN::LN(long long num)
{
	if (num == 0)
	{
		try
		{
			_num.push_back(0);
		} catch (std::bad_alloc &e)
		{
			throw ERROR_NOT_ENOUGH_MEMORY;
		}
	}
	else
	{
		if (num < 0)
		{
			_sign = true;
			num *= -1;
		}
		while (num > 0)
		{
			try
			{
				_num.push_back(num % LN_BASE);
			} catch (std::bad_alloc &e)
			{
				throw ERROR_NOT_ENOUGH_MEMORY;
			}
			num /= LN_BASE;
		}
	}
}

LN::LN(const char num[])
{
	*this = LN(std::string_view(num));
}

LN::LN(const std::string_view &str)
{
	std::size_t skip_zeroes = 0;
	while (skip_zeroes < str.size() && str[skip_zeroes] == '0')
	{
		skip_zeroes++;
	}
	if (skip_zeroes == str.size())
	{
		try
		{
			_num.push_back(0);
		} catch (std::bad_alloc &e)
		{
			throw ERROR_NOT_ENOUGH_MEMORY;
		}
	}
	else
	{
		std::string_view num_str = str.substr(skip_zeroes, str.size());
		for (std::size_t i = num_str.size() - 1; i != MAX_SIZE;)
		{
			int curr_value = 0;
			for (int step = 1; step < LN_BASE && i != MAX_SIZE; step *= 10, i--)
			{
				if (num_str[i] == '-')
				{
					_sign = true;
					try
					{
						_num.push_back(curr_value);
					} catch (std::bad_alloc &e)
					{
						throw ERROR_NOT_ENOUGH_MEMORY;
					}
					return;
				}
				curr_value += (num_str[i] - '0') * step;
			}
			try
			{
				_num.push_back(curr_value);
			} catch (std::bad_alloc &e)
			{
				throw ERROR_NOT_ENOUGH_MEMORY;
			}
		}
	}
}

LN::LN(const LN &ln)
{
	*this = ln;
}

LN::LN(LN &&ln) noexcept
{
	*this = std::move(ln);
}

LN &LN::operator=(LN &&ln) noexcept
{
	if (this != &ln)
	{
		std::vector< int >().swap(_num);
		_num = std::move(ln._num);
		_sign = ln._sign;
		_is_NaN = ln._is_NaN;

		ln._sign = false;
		ln._is_NaN = false;
	}
	return *this;
}

LN &LN::operator=(const LN &ln)
{
	_sign = ln._sign;
	_num = ln._num;
	_is_NaN = ln._is_NaN;
	return *this;
}

LN LN::operator+(const LN &ln) const
{
	LN sum(*this);
	sum += ln;
	return sum;
}

LN LN::operator-(const LN &ln) const
{
	LN sub(*this);
	sub -= ln;
	return sub;
}

LN LN::operator-() const
{
	LN ln(*this);
	ln._sign ^= true;
	return ln;
}

LN LN::operator*(const LN &ln) const
{
	LN res;
	if (_is_NaN || ln._is_NaN)
	{
		res._is_NaN = true;
		return res;
	}
	for (std::size_t i = 0; i < _num.size(); i++)
	{
		int last_part = 0;
		for (std::size_t j = 0; j < ln._num.size() || last_part; j++)
		{
			if (i + j >= res._num.size())
			{
				try
				{
					res._num.push_back(0);
				} catch (std::bad_alloc &e)
				{
					throw ERROR_NOT_ENOUGH_MEMORY;
				}
			}
			long long mul_part = res._num[i + j] + static_cast< long long >(_num[i]) * ln._get_num(j) + last_part;
			res._num[i + j] = mul_part % LN_BASE;
			last_part = mul_part / LN_BASE;
		}
	}
	res._sign = _sign ^ ln._sign;
	res._delete_zeroes();
	return res;
}

LN LN::operator/(const LN &ln) const
{
	LN div(*this);
	div /= ln;
	return div;
}

LN LN::operator/(const int num) const
{
	LN div(*this);
	div /= num;
	return div;
}

LN LN::operator%(const LN &ln) const
{
	LN mod(*this);
	mod %= ln;
	return mod;
}

LN &LN::operator+=(const LN &ln)
{
	if (_is_NaN || ln._is_NaN)
	{
		_is_NaN = true;
		return *this;
	}
	if (_sign != ln._sign)
	{
		*this -= -ln;
	}
	else
	{
		bool last_part = 0;
		for (std::size_t i = 0; i < std::max(_num.size(), ln._num.size()) || last_part; i++)
		{
			if (i == _num.size())
			{
				try
				{
					_num.push_back(0);
				} catch (std::bad_alloc &e)
				{
					throw ERROR_NOT_ENOUGH_MEMORY;
				}
			}
			_num[i] += last_part + ln._get_num(i);
			last_part = _num[i] >= LN_BASE;
			if (last_part)
			{
				_num[i] -= LN_BASE;
			}
		}
	}
	return *this;
}

LN &LN::operator-=(const LN &ln)
{
	if (_is_NaN || ln._is_NaN)
	{
		_is_NaN = true;
		return *this;
	}
	if (_sign != ln._sign)
	{
		*this = _sign ? -(-*this + ln) : *this + (-ln);
		return *this;
	}

	if ((*this < ln && !_sign) || (_sign && *this > ln))
	{
		*this = -(ln - *this);
		return *this;
	}
	bool last_part = 0;
	for (std::size_t i = 0; i < ln._num.size() || last_part; i++)
	{
		_num[i] -= last_part + ln._get_num(i);
		last_part = _num[i] < 0;
		if (last_part)
		{
			_num[i] += LN_BASE;
		}
	}
	_delete_zeroes();
	return *this;
}

LN &LN::operator*=(const LN &ln)
{
	*this = *this * ln;
	return *this;
}

LN &LN::operator/=(const LN &ln)
{
	*this = divmod(abs_ln(*this), abs_ln(ln)).first;
	_sign ^= ln._sign;
	return *this;
}

LN &LN::operator/=(int num)
{
	if (_is_NaN)
	{
		return *this;
	}
	if (LN_BASE <= abs(num))
	{
		*this /= LN(num);
		return *this;
	}
	if (num == 0)
	{
		_is_NaN = true;
		return *this;
	}
	if (num < 0)
	{
		_sign ^= true;
		num = -num;
	}
	int last_part = 0;
	for (std::size_t i = _num.size() - 1; i != MAX_SIZE; i--)
	{
		long long curr_num = static_cast< long long >(last_part) * LN_BASE + _num[i];
		last_part = curr_num % num;
		_num[i] = curr_num / num;
	}
	return *this;
}

LN &LN::operator%=(const LN &ln)
{
	*this = divmod(abs_ln(*this), abs_ln(ln)).second;
	return *this;
}

bool LN::operator==(const LN &ln) const
{
	if (ln._is_NaN || _is_NaN)
	{
		return false;
	}
	std::size_t max_size = std::max(_num.size(), ln._num.size());
	if (_is_zero() && ln._is_zero())
	{
		return true;
	}
	for (std::size_t i = max_size - 1u; i != MAX_SIZE; i--)
	{
		if (_get_num(i) != ln._get_num(i))
		{
			return false;
		}
	}
	return _sign == ln._sign;
}

bool LN::operator!=(const LN &ln) const
{
	if (_is_NaN || ln._is_NaN)
	{
		return false;
	}
	return !(*this == ln);
}

bool LN::operator>(const LN &ln) const
{
	if (_is_NaN || ln._is_NaN)
	{
		return false;
	}
	std::size_t max_size = std::max(_num.size(), ln._num.size());
	if (_is_zero() && ln._is_zero())
	{
		return false;
	}
	for (std::size_t i = max_size - 1u; i != MAX_SIZE; i--)
	{
		if (_get_num(i) == ln._get_num(i))
		{
			continue;
		}
		if (_sign == ln._sign)
		{
			return _sign ^ (_get_num(i) > ln._get_num(i));
		}
		else
		{
			return !_sign;
		}
	}
	return _sign < ln._sign;
}

bool LN::operator>=(const LN &ln) const
{
	if (_is_NaN || ln._is_NaN)
	{
		return _is_NaN;
	}
	return *this > ln || *this == ln;
}

bool LN::operator<(const LN &ln) const
{
	if (_is_NaN || ln._is_NaN)
	{
		return _is_NaN;
	}
	return !(*this >= ln);
}

bool LN::operator<=(const LN &ln) const
{
	if (_is_NaN || ln._is_NaN)
	{
		return _is_NaN;
	}
	return !(*this > ln);
}

LN::operator bool() const
{
	if (_is_zero() || _is_NaN)
	{
		return false;
	}
	return true;
}

LN::operator long long() const
{
	LN max_ll = LLONG_MAX, min_ll = LLONG_MIN;
	if (*this > max_ll || *this < min_ll)
	{
		throw ERROR_INVALID_DATA;
	}
	long long ll_num = 0;
	for (std::size_t i = _num.size() - 1; i != MAX_SIZE; i--)
	{
		ll_num = ll_num * LN_BASE + _num[i];
	}
	return ll_num;
}

std::ostream &operator<<(std::ostream &os, const LN &ln)
{
	if (ln._is_NaN)
	{
		os << "NaN";
		return os;
	}
	if (ln._is_zero())
	{
		os << 0;
		return os;
	}
	if (ln._sign)
	{
		os << "-";
	}
	os << ln._num[ln._num.size() - 1];
	for (std::size_t i = ln._num.size() - 2; i != MAX_SIZE; i--)
	{
		os << std::setfill('0') << std::setw(9) << ln._num[i];
	}
	return os;
}

void LN::_delete_zeroes()
{
	while (_num.size() > 1 && _num.back() == 0)
	{
		_num.pop_back();
	}
}

bool LN::_is_zero() const
{
	if (_is_NaN)
	{
		return false;
	}
	for (std::size_t i = 0; i < _num.size(); i++)
	{
		if (_num[i] != 0)
		{
			return false;
		}
	}
	return true;
}

LN operator"" _ln(const char num[])
{
	return LN(num);
}