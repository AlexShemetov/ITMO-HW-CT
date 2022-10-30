package expression;

public class Maximum extends Binary {
    public Maximum(UltimateExpression firstExp, UltimateExpression secondExp) {
        super(firstExp, secondExp, "max");
    }

    public int evaluate(int x) {
        return evaluate(x,0,0);
    }

    public int evaluate(int x, int y, int z) {
        return firstExp.evaluate(x,y,z) > secondExp.evaluate(x,y,z) ? firstExp.evaluate(x,y,z) : secondExp.evaluate(x,y,z);
    }
}