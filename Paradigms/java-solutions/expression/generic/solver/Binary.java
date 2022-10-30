package expression.generic.solver;

import java.util.Objects;

import expression.generic.Solver;

public abstract class Binary<T> implements TripleExpression<T> {
    protected final TripleExpression<T> firstExp, secondExp;
    protected final Solver<T> solver;
    private final String op;

    protected Binary(TripleExpression<T> firstExp, TripleExpression<T> secondExp, String op, Solver<T> solver) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
        this.op = op;
        this.solver = solver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Binary binary = (Binary) o;
        return Objects.equals(op, binary.op) && Objects.equals(firstExp, binary.firstExp) && Objects.equals(secondExp, binary.secondExp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstExp, secondExp, op);
    }

    @Override
    public String toString() {
        return "(" + firstExp.toString() + " " + op + " " + secondExp.toString() + ")";
    }
}
