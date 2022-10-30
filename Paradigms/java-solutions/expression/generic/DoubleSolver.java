package expression.generic;

public class DoubleSolver implements Solver<Double> {
    @Override
    public Double add(final Double first, final Double second) {
        return first + second;
    }

    @Override
    public Double sub(final Double first, final Double second) {
        return first - second;
    }

    @Override
    public Double mul(final Double first, final Double second) {
        return first * second;
    }

    @Override
    public Double div(final Double first, final Double second) {
        return first / second;
    }

    @Override
    public Double min(final Double first, final Double second) {
        return Double.min(first, second);
    }

    @Override
    public Double max(final Double first, final Double second) {
        return Double.max(first, second);
    }

    @Override
    public Double neg(final Double num) {
        return -num;
    }

    @Override
    public Double count(final Double num) {
        return (double) Long.bitCount(Double.doubleToLongBits(num));
    }

    @Override
    public Double valueForInt(final Integer num) {
        return num.doubleValue();
    }

    @Override
    public Double parse(final String num) {
        return Double.parseDouble(num);
    }

    @Override
    public String toString(final Double num) {
        return num.toString();
    }
}
