package expression.exceptions;

public class MissingArgumentException extends ParserException {
    public MissingArgumentException() {
        super("Missing argument");
    }

    public MissingArgumentException(String message, Throwable cause) {
        super("Missing argument", cause);
    }
}
