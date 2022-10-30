package expression.exceptions;

public class NoOpeningParenthesis extends ParserException {
    public NoOpeningParenthesis() {
        super("No opening parenthesis");
    }

    public NoOpeningParenthesis(Throwable cause) {
        super("No opening parenthesis", cause);
    }
}
