package expression;

import java.util.Objects;

public class t0 implements UltimateExpression {
    private final UltimateExpression exp;

    public t0(UltimateExpression exp) {
        this.exp = exp;
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x,0,0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int count = 0;
        int n = exp.evaluate(x,y,z);
        if (n == 0) {
            return 32;
        }
        while (n % 2 == 0) {
            count++;
            n /= 2;
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        t0 t0 = (t0) o;
        return Objects.equals(exp, t0.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp);
    }

    @Override
    public String toString() {
        return "t0(" + exp + ")";
    }
}
