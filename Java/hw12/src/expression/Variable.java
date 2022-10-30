package expression;

import java.util.Objects;

public class Variable implements UltimateExpression {

    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int ev) {
        return ev;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (var) {
            case "x" -> x;
            case "-x" -> -x;
            case "y" -> y;
            case "-y" -> -y;
            case "z" -> z;
            case "-z" -> -z;
            default -> throw new IllegalArgumentException("Unexpected value: " + var);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return var.equals(variable.var);
    }

    @Override
    public int hashCode() {
        return Objects.hash(var);
    }

    @Override
    public String toString() {
        return var;
    }
}
