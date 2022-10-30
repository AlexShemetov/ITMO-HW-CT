package game;

import java.util.Scanner;

public class CheatingPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        final TicTacToeBoard board = (TicTacToeBoard) position;
        Move first = null;
        for (int r = 0; r < board.getRowSize(); r++) {
            for (int c = 0; c < board.getColumnSize(); c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    if (first == null) {
                        first = move;
                    } else {
                        board.makeMove(move);
                    }
                }
            }
        }
        return first;
    }
}
