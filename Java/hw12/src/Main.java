import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        ExpressionParser exp = new ExpressionParser();
        System.out.println(exp.parse("x*(x-2)*x+1").evaluate(1));
    }
}
