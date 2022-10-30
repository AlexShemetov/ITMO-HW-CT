package expression;

public class Add extends Binary {
    public Add(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "+");
    }

    public int evaluate(int x) {
        return evaluate(x,0,0);
    }

    public int evaluate(int x, int y, int z) {
        return firstExp.evaluate(x, y, z) + secondExp.evaluate(x, y, z);
    }
}
