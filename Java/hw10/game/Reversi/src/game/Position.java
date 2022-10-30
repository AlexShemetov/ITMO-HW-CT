package game;

public interface Position {
    int getTurn();

    boolean isValid(Move move);

    int getCell(int row, int column);
}
