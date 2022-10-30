import java.util.Scanner;

import game.ReversiBoard;
import game.NPlayerGame;

public class Main {
    public static void main(String[] args) {
        final int n = 8;
        final int players = Integer.parseInt(args[0]);
        final int result = new NPlayerGame(
                new ReversiBoard(n, n, players),
                players,
                new Scanner(System.in)
        ).play(true);
        if (result == 0) {
            System.out.println("Draw");
        } else if (result > 0) {
            System.out.println("Winner " + result);
        } else {
            System.out.println("Unknow");
        }
    }
}
