package expression.generic.solver;

import expression.generic.Solver;

public class Count<T> implements TripleExpression<T> {

    private final TripleExpression<T> expr;
    private final Solver<T> solver;

    public Count(TripleExpression<T> expr, Solver<T> solver) {
        this.expr = expr;
        this.solver = solver;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return solver.count(expr.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "count(" + expr.toString() + ")";
    }
}
