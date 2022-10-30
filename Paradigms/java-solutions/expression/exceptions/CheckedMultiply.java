package expression.exceptions;

import expression.Binary;
import expression.UltimateExpression;

public class CheckedMultiply extends Binary {
    public CheckedMultiply(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "*");
    }

    private int check(int first, int second) {
        if (first == 0) {
            return 0;
        }
        if (first > 0) {
            if (Integer.MIN_VALUE / first <= second && second <= Integer.MAX_VALUE / first) {
                return first * second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        } else {
            if (first == -1 && second > Integer.MIN_VALUE) {
                return first * second;
            }
            else if (Integer.MAX_VALUE / first <= second && second <= Integer.MIN_VALUE / first) {
                return first * second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        }
    }

    @Override
    public int evaluate(int x) {
        return check(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return check(firstExp.evaluate(x,y,z), secondExp.evaluate(x,y,z));
    }
}
