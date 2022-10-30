package expression.exceptions;

public class SpaceInNumberException extends ParserException {
    public SpaceInNumberException() {
        super("Space in number");
    }

    public SpaceInNumberException(Throwable cause) {
        super("Space in number", cause);
    }
}
