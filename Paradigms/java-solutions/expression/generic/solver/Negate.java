package expression.generic.solver;

import java.security.Principal;
import java.util.Objects;

import expression.generic.Solver;

public class Negate<T> implements TripleExpression<T> {

    private final TripleExpression<T> exp;
    private final Solver<T> solver;

    public Negate(TripleExpression<T> exp, Solver<T> solver) {
        this.exp = exp;
        this.solver = solver;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return solver.neg(exp.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negate negate = (Negate) o;
        return exp.equals(negate.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp);
    }

    @Override
    public String toString() {
//        if (exp.equals(new Const(0))) {
//            return exp.toString();
//        }
        return "-(" + exp + ")";
    }
}
