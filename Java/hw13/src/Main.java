import expression.exceptions.ExpressionParser;

public class Main {

    public static void main(String[] args) {
        ExpressionParser exp1 = new ExpressionParser();
        System.out.println(exp1.parse("(1)"));
        ExpressionParser exp2 = new ExpressionParser();
        System.out.println(exp2.parse("-2147483648 - x").evaluate(-20));
    }
}
