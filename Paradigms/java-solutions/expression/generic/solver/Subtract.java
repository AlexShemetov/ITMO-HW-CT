package expression.generic.solver;

import expression.generic.Solver;

public class Subtract<T> extends Binary<T> {
    public Subtract(TripleExpression<T> firstExp, TripleExpression<T> secondExp, Solver<T> solver) {
        super(firstExp, secondExp, "-", solver);
    }

    public T evaluate(T x, T y, T z) {
        return solver.sub(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }
}