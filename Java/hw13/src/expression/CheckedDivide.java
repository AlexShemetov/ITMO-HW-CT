package expression;

public class CheckedDivide extends Binary {
    public CheckedDivide(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "/");
    }

    private int check(int first, int second) {
        if (second == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return first / second;
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
