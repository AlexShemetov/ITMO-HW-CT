package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwoPlayerGame {
    private final Board board;
    private final List<Player> players;
    private final int totalPlayers;

    public TwoPlayerGame(Board board, int totalPlayers, Scanner in) {
        this.board = board;
        this.totalPlayers = totalPlayers;
        players = new ArrayList<>();
        System.out.print("\nWhat types of player do you want to add? (B - bot, H - human) Enter all ");
        String playerType = new String(in.nextLine());
        for (int i = 0; i < totalPlayers; i++) {
            if (playerType.charAt(i) == 'B') {
                players.add(new RandomPlayer());
            } else if (playerType.charAt(i) == 'H') {
                players.add(new HumanPlayer(new Scanner(System.in)));
            }
        }
    }

    public int play(boolean log) {
        int[] results = new int[totalPlayers];
        while (true) {
            for (int i = 0; i < totalPlayers; i++) {
                results[i] = makeMove(players.get(i), i + 1, log);
                if (results[i] != -1)  {
                    return results[i];
                }
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            if (player.getClass() == HumanPlayer.class && result == GameResult.LOOSE) {
                System.out.println("Result: " + GameResult.UNKNOWN);
                System.out.print("\nRepeat your move ");
                makeMove(player, no, log);
                return -1;
            } else {
                System.out.println("Result: " + result);
            }
        }
        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return 3 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
