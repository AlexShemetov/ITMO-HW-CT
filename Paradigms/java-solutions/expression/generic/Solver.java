package expression.generic;

public interface Solver<T> {
    T parse(final String num);
    T add(final T first, final T second);
    T sub(final T first, final T second);
    T mul(final T first, final T second);
    T div(final T first, final T second);
    T min(final T first, final T second);
    T max(final T first, final T second);
    T neg(final T num);
    T count(final T num);
    T valueForInt(final Integer num);
    String toString(final T num);
}
