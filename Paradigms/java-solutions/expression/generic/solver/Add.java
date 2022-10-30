package expression.generic.solver;

import expression.generic.Solver;

public class Add<T> extends Binary<T> {
    public Add(TripleExpression<T> firstExp, TripleExpression<T> secondExp, Solver<T> solver) {
        super(firstExp, secondExp, "+", solver);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return solver.add(firstExp.evaluate(x, y, z), secondExp.evaluate(x, y, z));
    }
}