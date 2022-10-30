package game;

import java.util.Arrays;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.TextUI;

public class ReversiBoard implements Board, Position {
    private final int rowSize;
    private final int columnSize;
    private final int totalPlayers;
    private final int[][] field;
    private int[] points;
    private int turn;

    public ReversiBoard(int rowSize, int columnSize, int totalPlayers) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.totalPlayers = totalPlayers;
        field = new int[rowSize][columnSize];
        for (int[] row : field) {
            Arrays.fill(row, 0);
        }

        turn = 1;
        for (int i = 0; i < totalPlayers; i++) {
            for (int j = 0; j < totalPlayers; j++) {
                field[rowSize / 2 - totalPlayers / 2 + i][rowSize / 2 - totalPlayers / 2 + j] = turn;
                turn = (turn + 1) % (totalPlayers + 1);
                if (turn == 0) {
                    turn = 1;
                }
            }
            turn = (turn + 1) % (totalPlayers + 1);
            if (turn == 0) {
                turn = 1;
            }
        }
        turn = 1;

        points = new int[totalPlayers];
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    @Override
    public int getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public int makeMove(Move move) {
        if (!isValid(move)) {
            return -2;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        boolean find = false;
        for (int i = 0; i < move.getRow(); i++) {
            for (int j = 0; j < move.getCol(); j++) {
                if (field[i][j] == move.getValue() && field[i + 1][j + 1] != 0) {
                    for (; i < move.getRow(); i++) {
                        for (; j < move.getCol(); j++) {
                            field[i][j] = move.getValue();
                        }
                    }
                    find = true;
                }
                if (find) {
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        find = false;
        for (int i = rowSize - 1; i > move.getRow(); i--) {
            for (int j = columnSize - 1; j > move.getCol(); j--) {
                if (field[i][j] == move.getValue() && field[i - 1][j - 1] != 0) {
                    for (; i > move.getRow(); i--) {
                        for (; j > move.getCol(); j--) {
                            field[i][j] = move.getValue();
                        }
                    }
                    find = true;
                }
                if (find) {
                    break;
                }
            }
            if (find) {
                break;
            }
        }


        find = false;
        for (int i = 0; i < move.getRow(); i++) {
            for (int j = columnSize - 1; j > move.getCol(); j--) {
                if (field[i][j] == move.getValue() && field[i + 1][j - 1] != 0) {
                    for (; i < move.getRow(); i++) {
                        for (; j > move.getCol(); j--) {
                            field[i][j] = move.getValue();
                        }
                    }
                    find = true;
                }
                if (find) {
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        find = false;
        for (int i = rowSize - 1; i > move.getRow(); i--) {
            for (int j = 0; j < move.getCol(); j++) {
                if (field[i][j] == move.getValue() && field[i - 1][j + 1] != 0) {
                    for (; i > move.getRow(); i--) {
                        for (; j < move.getCol(); j++) {
                            field[i][j] = move.getValue();
                        }
                    }
                    find = true;
                }
                if (find) {
                    break;
                }
            }
            if (find) {
                break;
            }
        }

        find = false;
        for (int i = 0; i < move.getRow(); i++) {
            if (field[i][move.getCol()] == move.getValue() && field[i + 1][move.getCol()] != 0) {
                for (; i < move.getRow(); i++) {
                    field[i][move.getCol()] = move.getValue();
                }
                find = true;
            }
            if (find) {
                break;
            }
        }
        find = false;
        for (int i = rowSize - 1; i > move.getRow(); i--) {
            if (field[i][move.getCol()] == move.getValue() && field[i - 1][move.getCol()] != 0) {
                for (; i > move.getRow(); i--) {
                    field[i][move.getCol()] = move.getValue();
                }
                find = true;
            }
            if (find) {
                break;
            }
        }

        find = false;
        for (int i = 0; i < move.getCol(); i++) {
            if (field[move.getRow()][i] == move.getValue() && field[move.getRow()][i + 1] != 0) {
                for (; i < move.getCol(); i++) {
                    field[move.getRow()][i] = move.getValue();
                }
                find = true;
            }
            if (find) {
                break;
            }
        }
        find = false;
        for (int i = columnSize - 1; i > move.getCol(); i--) {
            if (field[move.getRow()][i] == move.getValue() && field[move.getRow()][i - 1] != 0) {
                for (; i > move.getCol(); i--) {
                    field[move.getRow()][i] = move.getValue();
                }
                find = true;
            }
            if (find) {
                break;
            }
        }

        if (checkFull()) {
            return winner();
        }

        turn = (turn + 1) % (totalPlayers + 1);
        if (turn == 0) {
            turn++;
        }
        return -1;
    }

    private int winner() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                points[field[i][j] - 1]++;
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < totalPlayers; i++) {
            if (points[maxIndex] < points[i]) {
                maxIndex = i;
            }
        }
        for (int i = 0; i < totalPlayers; i++) {
            if (points[maxIndex] == points[i] && i != maxIndex) {
                return 0;
            }
        }
        return maxIndex + 1;
    }

    private boolean checkFull() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (field[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(final Move move) {
        if (0 <= move.getRow()
                && move.getRow() < rowSize
                && 0 <= move.getCol() && move.getCol() < columnSize
                && field[move.getRow()][move.getCol()] == 0) {
            if (move.getRow() + 1 < rowSize && field[move.getRow() + 1][move.getCol()] != move.getValue() && field[move.getRow() + 1][move.getCol()] != 0) {
                for (int i = move.getRow() + 2; i < rowSize; i++) {
                    if (field[i][move.getCol()] == 0) break;
                    if (field[i][move.getCol()] == move.getValue()) return true;
                }
            }
            if (move.getRow() - 1 >= 0 && field[move.getRow() - 1][move.getCol()] != move.getValue() && field[move.getRow() - 1][move.getCol()] != 0) {
                for (int i = move.getRow() - 2; i >= 0; i--) {
                    if (field[i][move.getCol()] == 0) break;
                    if (field[i][move.getCol()] == move.getValue()) return true;
                }
            }
            if (move.getCol() + 1 < columnSize && field[move.getRow()][move.getCol() + 1] != move.getValue() && field[move.getRow()][move.getCol()+1] != 0) {
                for (int i = move.getCol() + 2; i < columnSize; i++) {
                    if (field[move.getRow()][i] == 0) break;
                    if (field[move.getRow()][i] == move.getValue()) return true;
                }
            }
            if (move.getCol() - 1 >= 0 && field[move.getRow()][move.getCol() - 1] != move.getValue() && field[move.getRow()][move.getCol()-1] != 0) {
                for (int i = move.getCol() - 2; i >= 0; i--) {
                    if (field[move.getRow()][i] == 0) break;
                    if (field[move.getRow()][i] == move.getValue()) return true;
                }
            }

            if (move.getCol() + 1 < columnSize && move.getRow() + 1 < rowSize && field[move.getRow() + 1][move.getCol() + 1] != move.getValue() && field[move.getRow() + 1][move.getCol() + 1] != 0) {
                for (int i = 2; i < Integer.min(rowSize - move.getRow(), columnSize - move.getCol()); i++) {
                    if (field[move.getRow() + i][move.getCol() + i] == 0) break;
                    if (field[move.getRow() + i][move.getCol() + i] == move.getValue()) return true;
                }
            }
            if (move.getCol() - 1 >= 0 && move.getRow() - 1 >= 0 && field[move.getRow() - 1][move.getCol() - 1] != move.getValue()  && field[move.getRow() - 1][move.getCol() - 1] != 0) {
                for (int i = 2; i < Integer.min(move.getRow(), move.getCol()); i++) {
                    if (field[move.getRow() - i][move.getCol() - i] == 0) break;
                    if (field[move.getRow() - i][move.getCol() - i] == move.getValue()) return true;
                }
            }
            if (move.getCol() - 1 >= 0 && move.getRow() + 1 < rowSize && field[move.getRow() + 1][move.getCol() - 1] != move.getValue()  && field[move.getRow() + 1][move.getCol()-1] != 0 ) {
                for (int i = 2; i < Integer.min(rowSize - move.getRow(), move.getCol()); i++) {
                    if (field[move.getRow() + i][move.getCol() - i] == 0) break;
                    if (field[move.getRow() + i][move.getCol() - i] == move.getValue()) return true;
                }
            }
            if (move.getRow() - 1 >= 0 && move.getCol() + 1 < columnSize && field[move.getRow() - 1][move.getCol() + 1] != move.getValue() && field[move.getRow() - 1][move.getCol() + 1] != 0) {
                for (int i = 2; i < Integer.min(columnSize - move.getCol(), move.getRow()); i++) {
                    if (field[move.getRow() - i][move.getCol() + i] == 0) break;
                    if (field[move.getRow() - i][move.getCol() + i] == move.getValue()) return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int c = 0; c < columnSize; c++) {
            sb.append(c + 1);
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < rowSize; r++) {
            sb.append(r + 1);
            for (int cell : field[r]) {
                sb.append(cell);
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
