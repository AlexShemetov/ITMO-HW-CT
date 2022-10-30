package expression;

import java.util.Objects;

public class CheckedNegate implements UltimateExpression {
    private final UltimateExpression exp;

    public CheckedNegate(UltimateExpression exp) {
        this.exp = exp;
    }

    private int check(int val) {
        if (val == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow");
        }
        return -val;
    }

    @Override
    public int evaluate(int ev) {
        return check(exp.evaluate(ev));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return check(exp.evaluate(x,y,z));
    }

    public boolean equals(UltimateExpression o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckedNegate aConst = (CheckedNegate) o;
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
