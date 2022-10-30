package expression;

public class CheckedSubtract extends Binary {
    public CheckedSubtract(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "-");
    }

    private int check(int first, int second) {
        if (second < 0) {
            if (first <= Integer.MAX_VALUE + second) {
                return first - second;
            } else {
                throw new ArithmeticException("Overflow");
            }
        } else {
            if (Integer.MIN_VALUE + second <= first) {
                return first - second;
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
