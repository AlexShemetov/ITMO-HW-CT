package expression.generic;

import java.util.Map;

import expression.generic.solver.TripleExpression;

public class GenericTabulator implements Tabulator {
    private final Map<String, Solver<?>> mode = Map.of(
        "i", new CheckedIntegerSolver(),
        "d", new DoubleSolver(),
        "bi", new BigIntegerSolver()
    );

    
    @Override
    public Object[][][] tabulate(final String mode, final String expression, final int x1, final int x2, final int y1, final int y2, final int z1, final int z2) throws Exception {
        final Solver<?> solver = this.mode.get(mode);
        if (solver == null) {
            throw new IllegalArgumentException("Mode not supported");
        }
        return generateTable(solver, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T>Object[][][] generateTable(final Solver<T> solver, final String expression, final int x1, final int x2, final int y1, final int y2, final int z1, final int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpression<T> expr;
        ExpressionParser<T> parser = new ExpressionParser<>(solver);
        try {
            expr = parser.parse(expression);
        } catch (IllegalArgumentException e) {
            return result;
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = expr.evaluate(
                                solver.valueForInt(i),
                                solver.valueForInt(j),
                                solver.valueForInt(k)
                        );
                    } catch (Exception e) {
                        result[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}
