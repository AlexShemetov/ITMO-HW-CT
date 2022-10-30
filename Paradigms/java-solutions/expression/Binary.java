package expression;

import java.util.Objects;

public abstract class Binary implements UltimateExpression {
    protected final UltimateExpression firstExp;
    protected final UltimateExpression secondExp;
    private final String op;

    protected Binary(UltimateExpression firstExp, UltimateExpression secondExp, String op) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
        this.op = op;
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
