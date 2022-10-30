package expression.generic;

import expression.generic.solver.*;

public interface Parser<T> {
    TripleExpression<T> parse(String expression);
}
