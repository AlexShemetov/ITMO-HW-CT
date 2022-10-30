package expression;

public class Minimum extends Binary {
    public Minimum(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "min");
    }

    public int evaluate(int x) {
        return Math.min(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return Math.min(firstExp.evaluate(x,y,z), secondExp.evaluate(x,y,z));
    }
}