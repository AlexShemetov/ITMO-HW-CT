package expression;

import java.util.Objects;

public class Const implements UltimateExpression {

    private final int constant;

    public Const(int constant) {
        this.constant = constant;
    }

    @Override
    public int evaluate(int ev) {
        return constant;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constant;
    }

    public boolean equals(UltimateExpression o) {
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
        return String.valueOf(constant);
    }
}
