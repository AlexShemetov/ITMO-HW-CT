package expression.generic.solver;

import expression.generic.Solver;

public class Minimum<T> extends Binary<T> {
    public Minimum(TripleExpression<T> firstExp, TripleExpression<T> secondExp, Solver<T> solver) {
        super(firstExp, secondExp, "min", solver);
    }

    public T evaluate(T x, T y, T z) {
        return solver.min(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }
}