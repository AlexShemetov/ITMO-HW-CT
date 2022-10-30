package expression.exceptions;

public class IncorrectSymbolException extends ParserException {
    public IncorrectSymbolException() {
        super("Incorrect symbol");
    }

    public IncorrectSymbolException(Throwable cause) {
        super("Incorrect symbol", cause);
    }
}
