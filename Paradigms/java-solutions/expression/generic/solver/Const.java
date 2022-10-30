package expression.generic.solver;

import java.util.Objects;

import expression.generic.Solver;

public class Const<T> implements TripleExpression<T> {

    private final T constant;
    private final Solver<T> solver;

    public Const(T constant, Solver<T> solver) {
        this.constant = constant;
        this.solver = solver;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return constant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return constant == aConst.constant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant);
    }

    @Override
    public String toString() {
        return solver.toString(constant);
    }
}
