package expression.generic.solver;

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z);
}
