package expression;

public class Minimum extends Binary {
    public Minimum(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "min");
    }

    public int evaluate(int x) {
        return firstExp.evaluate(x) < secondExp.evaluate(x) ? firstExp.evaluate(x) : secondExp.evaluate(x);
    }

    public int evaluate(int x, int y, int z) {
        return firstExp.evaluate(x,y,z) < secondExp.evaluate(x,y,z) ? firstExp.evaluate(x,y,z) : secondExp.evaluate(x,y,z);
    }
}