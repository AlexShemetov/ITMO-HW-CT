package expression.generic.solver;

import expression.generic.Solver;

public class Maximum<T> extends Binary<T> {
    public Maximum(TripleExpression<T> firstExp, TripleExpression<T> secondExp, Solver<T> solver) {
        super(firstExp, secondExp, "max", solver);
    }

    public T evaluate(T x, T y, T z) {
        return solver.max(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }
}