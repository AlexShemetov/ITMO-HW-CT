package expression.generic.solver;

import expression.generic.Solver;

public class Multiply<T> extends Binary<T> {
    public Multiply(TripleExpression<T> firstExp, TripleExpression<T> secondExp, Solver<T> solver) {
        super(firstExp, secondExp, "*", solver);
    }

    public T evaluate(T x, T y, T z) {
        return solver.mul(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }
}
