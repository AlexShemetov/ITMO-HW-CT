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
        if (var.equals("x") || var.equals("X"))
            return x;
        if (var.equals("y") || var.equals("Y"))
            return y;
        return z;
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
