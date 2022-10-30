package expression;

public class Main {
    public static void main(String[] args) {
        UltimateExpression exp = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("y")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("z")
                        )
                ),
                new Const(3)
        );
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        System.out.println(exp.evaluate(x,y,z));
        System.out.println(exp.toString());
    }
}
