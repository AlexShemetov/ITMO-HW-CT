package expression.exceptions;

public class NoClosingParenthesis extends ParserException {
    public NoClosingParenthesis() {
        super("No closing parenthesis");
    }

    public NoClosingParenthesis(Throwable cause) {
        super("No closing parenthesis", cause);
    }
}
