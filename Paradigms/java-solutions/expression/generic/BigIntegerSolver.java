package expression.generic;

import java.math.BigInteger;

public class BigIntegerSolver implements Solver<BigInteger> {
    @Override
    public BigInteger add(final BigInteger first, final BigInteger second) {
        return first.add(second);
    }

    @Override
    public BigInteger sub(final BigInteger first, final BigInteger second) {
        return first.subtract(second);
    }

    @Override
    public BigInteger div(final BigInteger first, final BigInteger second) {
        return first.divide(second);
    }

    @Override
    public BigInteger mul(final BigInteger first, final BigInteger second) {
        return first.multiply(second);
    }

    @Override
    public BigInteger min(final BigInteger first, final BigInteger second) {
        return first.min(second);
    }

    @Override
    public BigInteger max(final BigInteger first, final BigInteger second) {
        return first.max(second);
    }

    @Override
    public BigInteger neg(final BigInteger num) {
        return num.negate();
    }

    @Override
    public BigInteger count(final BigInteger num) {
        return new BigInteger(Integer.toString(num.bitCount()));
    }

    @Override
    public BigInteger parse(final String num) {
        return new BigInteger(num);
    }

    @Override
    public String toString(final BigInteger num) {
        return num.toString();
    }

    @Override
    public BigInteger valueForInt(final Integer num) {
        return new BigInteger(num.toString());
    }
}
