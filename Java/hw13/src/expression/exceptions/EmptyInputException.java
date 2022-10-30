package expression.exceptions;

public class EmptyInputException extends ParserException {
    public EmptyInputException() {
        super("Empty input");
    }

    public EmptyInputException(Throwable cause) {
        super("Empty input", cause);
    }
}
