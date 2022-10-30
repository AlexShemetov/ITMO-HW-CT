package expression.generic;

public class CheckedIntegerSolver implements Solver<Integer> {
    @Override
    public Integer parse(final String num) throws NumberFormatException {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(num);
        }
    }

    @Override
    public Integer add(final Integer first, final Integer second) throws ArithmeticException {
        if (first > 0) {
            if (second <= Integer.MAX_VALUE - first) {
                return first + second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        } else {
            if (Integer.MIN_VALUE - first <= second) {
                return first + second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        }
    }

    @Override
    public Integer sub(final Integer first, final Integer second) throws ArithmeticException {
        if (second < 0) {
            if (first <= Integer.MAX_VALUE + second) {
                return first - second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        } else {
            if (Integer.MIN_VALUE + second <= first) {
                return first - second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        }
    }

    @Override
    public Integer mul(final Integer first, final Integer second) throws ArithmeticException {
        if (first == 0) {
            return 0;
        }
        if (first > 0) {
            if (Integer.MIN_VALUE / first <= second && second <= Integer.MAX_VALUE / first) {
                return first * second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        } else {
            if (first == -1 && second > Integer.MIN_VALUE) {
                return first * second;
            }
            else if (Integer.MAX_VALUE / first <= second && second <= Integer.MIN_VALUE / first) {
                return first * second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        }
    }

    @Override
    public Integer div(final Integer first, final Integer second) throws ArithmeticException {
        if (second == 0) {
            throw new ArithmeticException("Division by zero");
        } else if (first == Integer.MIN_VALUE && second == -1) {
            throw new ArithmeticException("Overflow");
        }
        return first / second;
    }

    @Override
    public Integer neg(final Integer num) throws ArithmeticException {
        if (num == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow");
        }
        return -num;
    }

    @Override
    public Integer min(final Integer first, final Integer second) {
        return first < second ? first : second;
    }

    @Override
    public Integer max(final Integer first, final Integer second) {
        return first > second ? first : second;
    }

    @Override
    public Integer count(final Integer num) {
        return Integer.bitCount(num);
    }

    @Override
    public Integer valueForInt(final Integer num) {
        return num;
    }

    @Override
    public String toString(final Integer num) {
        return num.toString();
    }
}