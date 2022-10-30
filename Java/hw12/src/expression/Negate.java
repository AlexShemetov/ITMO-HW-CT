package expression;

import java.util.Objects;

public class Negate implements UltimateExpression {

    private final UltimateExpression exp;

    public Negate(UltimateExpression exp) {
        this.exp = exp;
    }

    @Override
    public int evaluate(int ev) {
        return -exp.evaluate(ev);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -exp.evaluate(x,y,z);
    }

    public boolean equals(UltimateExpression o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negate aConst = (Negate) o;
        return exp == aConst.exp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp);
    }

    @Override
    public String toString() {
        if (exp.equals(new Const(0)))
            return exp.toString();
        return "(-" + exp + ")";
    }
}
