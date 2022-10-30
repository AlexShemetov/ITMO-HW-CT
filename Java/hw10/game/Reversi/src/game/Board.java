package game;

public interface Board {
    Position getPosition();

    int makeMove(Move move);
}
