package expression;

public class Subtract extends Binary {
    public Subtract(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "-");
    }


    public int evaluate(int x) {
        return firstExp.evaluate(x) - secondExp.evaluate(x);
    }

    public int evaluate(int x, int y, int z) {
        return firstExp.evaluate(x, y, z) - secondExp.evaluate(x, y, z);
    }
}