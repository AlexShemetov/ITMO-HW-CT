package expression.exceptions;

import expression.Const;
import expression.UltimateExpression;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckedNegate that = (CheckedNegate) o;
        return Objects.equals(exp, that.exp);
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
