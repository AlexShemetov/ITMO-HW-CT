package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move makeMove(Position position) {
        final ReversiBoard board = (ReversiBoard) position;
        while (true) {
            final Move move = new Move(
                    random.nextInt(board.getRowSize()),
                    random.nextInt(board.getColumnSize()),
                    position.getTurn()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
