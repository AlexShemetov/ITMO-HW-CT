package expression;

import java.util.Objects;

public class l0 implements UltimateExpression {
    private final UltimateExpression exp;

    public l0(UltimateExpression exp) {
        this.exp = exp;
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0,0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int count = 0;
        int num = exp.evaluate(x,y,z);
        if (num < 0) {
            return 0;
        }
        num = ~num;
        while (num != 0) {
            if (num % 2 == 0) {
                count = 0;
            } else {
                count++;
            }
            num = num >>> 1;
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        l0 l0 = (l0) o;
        return exp.equals(l0.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp);
    }

    @Override
    public String toString() {
        return "l0(" + exp + ")";
    }
}