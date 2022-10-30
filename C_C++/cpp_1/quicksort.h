#pragma once

#include <iostream>

template< typename T, bool descending >
void quicksort(T *data, std::size_t left, std::size_t right);
template< typename T, bool descending >
std::size_t part_ind(T *data, std::size_t left, std::size_t right);
template< typename T >
void swap(T *data, std::size_t left, std::size_t right);
template< typename T, bool descending >
std::size_t move_ind(T *data, std::size_t ind, T elem, int step);

template< typename T, bool descending >
void quicksort(T *data, std::size_t left, std::size_t right)
{
	while (left < right)
	{
		std::size_t ind = part_ind< T, descending >(data, left, right);
		if (ind - left < right - ind)
		{
			quicksort< T, descending >(data, left, ind);
			left = ind + 1;
		}
		else
		{
			quicksort< T, descending >(data, ind + 1, right);
			right = ind;
		}
	}
}

template< typename T, bool descending >
std::size_t part_ind(T *data, std::size_t left, std::size_t right)
{
	T mid = data[(left + right) / 2];
	while (left <= right)
	{
		left = move_ind< T, descending >(data, left, mid, 1);
		right = move_ind< T, true ^ descending >(data, right, mid, -1);
		if (left >= right)
		{
			break;
		}
		swap< T >(data, left++, right--);
	}
	return right;
}

template< typename T, bool descending >
std::size_t move_ind(T *data, std::size_t ind, T elem, int step)
{
	if (descending)
	{
		while (data[ind] > elem)
		{
			ind += step;
		}
	}
	else
	{
		while (elem > data[ind])
		{
			ind += step;
		}
	}
	return ind;
}

template< typename T >
void swap(T *data, std::size_t left, std::size_t right)
{
	T swapper = data[left];
	data[left] = data[right];
	data[right] = swapper;
}