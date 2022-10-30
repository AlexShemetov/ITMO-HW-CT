#pragma once

#include <math.h>
#include <stdio.h>
#include <stdlib.h>

const float EPS = 1.19e-7f;

/**
 * @brief
 *		maximum of 2 abs(numbers)
 * @param first first number
 * @param second second number
 *
 * @return (fabs(first) > fabs(second)) ? fabs(first) : fabs(second)
 */
float maxAbs(const float first, const float second);

/**
 * @brief
 *		checks if a number is 0 or close to it
 * @param resAbs
 * @param maxAbs
 *
 * @return 1 if number equal 0
 * @return 0 if number not equal 0
 */
int isZero(const float resAbs, const float maxAbs);

/**
 * @brief
 * 		calculates the difference of two numbers
 * @param first first number
 * @param second second number
 *
 * @return difference of two numbers
 */
float sub(const float first, const float second);

/**
 * @brief
 *      solves a system of linear equations using the Gaussian method
 *
 * @param n size of system of linear equations
 * @param matrix matrixficients for unknowns
 * @param ans answer column
 * @param eps array of epsilon
 *
 * @return -1 if the system has no solution
 * @return 0 if the system has a unique solution
 * @return 1 if the system has an infinite number of solutions
 */
int gauss(const size_t n, float **matrix);

/**
 * @brief
 *      checks how many solutions the system has
 *
 * @param n size of system of linear equations
 * @param matrix matrixficients for unknowns
 * @param ans answer column
 *
 * @return -1 if the system has no solution
 * @return 0 if the system has a unique solution
 * @return 1 if the system has an infinite number of solutions
 */
int how_many_solutions(const size_t n, float **matrix);

/**
 * @brief
 *      looks for a non-0 element in a column
 *
 * @param n size of system of linear equations
 * @param matrix matrixficients for unknowns
 * @param ans answer column
 * @param eps array of epsilon
 * @param indexStart index of the line from which the algorithm starts
 *
 * @return 1 if the column has a non-0 element
 * @return 0 if the column has not a non-0 element
 */
int search_not_zero(const size_t n, float **matrix, const size_t indexStart);

float maxAbs(const float first, const float second)
{
	return (fabs(first) > fabs(second)) ? fabs(first) : fabs(second);
}

int how_many_solutions(const size_t n, float **matrix)
{
	for (size_t i = 0; i < n; i++)
	{
		if (fabs(matrix[i][i]) == 0)
		{
			if (fabs(matrix[i][n]) == 0)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
	}

	return 0;
}

int isZero(const float resAbs, const float maxAbs)
{
	if (resAbs <= maxAbs * EPS)
	{
		return 1;
	}
	return 0;
}

float sub(const float first, const float second)
{
	if (isZero(fabs(first - second), maxAbs(fabs(first), fabs(second))))
	{
		return 0;
	}
	return first - second;
}

int gauss(const size_t n, float **matrix)
{
	for (size_t i = 0; i < n; i++)
	{
		if (sub(matrix[i][i], 0) == 0 && !search_not_zero(n, matrix, i))
		{
			continue;
		}

		float param = matrix[i][i];
		for (size_t j = i; j < n + 1; j++)
		{
			matrix[i][j] = matrix[i][j] / param;
		}

		for (size_t j = i + 1; j < n; j++)
		{
			for (size_t k = i + 1; k < n + 1; k++)
			{
				matrix[j][k] = sub(matrix[j][k], matrix[i][k] * matrix[j][i]);
			}
			matrix[j][i] = 0;
		}
	}

	for (int i = n - 1; i > -1; i--)
	{
		for (int j = i - 1; j > -1; j--)
		{
			matrix[j][n] = sub(matrix[j][n], matrix[i][n] * matrix[j][i]);
			matrix[j][i] = 0;
		}
	}

	return how_many_solutions(n, matrix);
}

int search_not_zero(const size_t n, float **matrix, const size_t indexStart)
{
	for (size_t i = indexStart + 1; i < n; i++)
	{
		if (sub(matrix[i][indexStart], 0) != 0)
		{
			float *swapLines = matrix[i];
			matrix[i] = matrix[indexStart];
			matrix[indexStart] = swapLines;
			return 1;
		}
	}
	return 0;
}