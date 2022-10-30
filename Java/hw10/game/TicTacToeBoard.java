package game;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0",
            Cell.M, "-",
            Cell.P, "|"
    );

    private final int rowSize;
    private final int columnSize;
    private final int cellsForWin;
    private final int totalPlayers;
    private final Cell[][] field;
    private Cell turn;

    public TicTacToeBoard(int rowSize, int columnSize, int cellsForWin, int totalPlayers) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.cellsForWin = cellsForWin;
        this.totalPlayers = totalPlayers;
        field = new Cell[rowSize][columnSize];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        //turn = turn == Cell.X ? Cell.O : Cell.X;
        if (turn == Cell.X) {
            turn = Cell.O;
        } else if (turn == Cell.O) {
            if (totalPlayers == 2) {
                turn = Cell.X;
            } else {
                turn = Cell.M;
            }
        } else if (turn == Cell.M) {
            if (totalPlayers == 3) {
                turn = Cell.X;
            } else {
                turn = Cell.P;
            }
        } else {
            turn = Cell.X;
        }
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        int count = 0;
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < columnSize; c++) {
                if (field[r][c] == Cell.E) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        for (int r = 0; r < rowSize; r++) {
            for (int i = 0; i < columnSize; i++) {
                int count = 0;
                for (int c = i; c < columnSize; c++) {
                    if (field[r][c] == turn) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == cellsForWin) {
                    return true;
                }
            }
        }
        for (int c = 0; c < columnSize; c++) {
            for (int j = 0; j < columnSize; j++) {
                int count = 0;
                for (int r = j; r < rowSize; r++) {
                    if (field[r][c] == turn) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == cellsForWin) {
                    return true;
                }
            }
        }

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int count = 0;
                for (int r = i, c = j; r < rowSize && c < columnSize; r++, c++) {
                    if (field[r][c] == turn) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == cellsForWin){
                    return true;
                }
            }
        }

        for (int i = rowSize - 1; i > -1; i--) {
            for (int j = 0; j < columnSize; j++) {
                int count = 0;
                for (int r = i, c = j; r > -1 && c < columnSize; r--, c++) {
                    if (field[r][c] == turn) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == cellsForWin){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < rowSize
                && 0 <= move.getCol() && move.getCol() < columnSize
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        //final StringBuilder sb = new StringBuilder(" 123").append(System.lineSeparator());
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int c = 0; c < columnSize; c++) {
            sb.append(c + 1);
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < rowSize; r++) {
            sb.append(r + 1);
            for (Cell cell : field[r]) {
                sb.append(CELL_TO_STRING.get(cell));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
