package expression;

public class Minimum extends Binary {
    public Minimum(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "min");
    }

    public int evaluate(int x) {
        return evaluate(x, 0,0);
    }

    public int evaluate(int x, int y, int z) {
        return firstExp.evaluate(x,y,z) < secondExp.evaluate(x,y,z) ? firstExp.evaluate(x,y,z) : secondExp.evaluate(x,y,z);
    }
}