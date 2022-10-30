package expression;

public class CheckedMultiply extends Binary {
    public CheckedMultiply(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "*");
    }

    private int check(int first, int second) {
        if (first > 0) {
            if (second <= Integer.MAX_VALUE / first) {
                return first * second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        } else {
            if (second <= Integer.MIN_VALUE / first) {
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
