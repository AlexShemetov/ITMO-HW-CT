package expression;

public class Maximum extends Binary {
    public Maximum(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "max");
    }

    public int evaluate(int x) {
        return Math.max(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return Math.max(firstExp.evaluate(x,y,z), secondExp.evaluate(x,y,z));
    }
}