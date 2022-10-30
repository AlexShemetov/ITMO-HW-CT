package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int m = Integer.parseInt(args[1]);
        final int k = Integer.parseInt(args[2]);
        final int players = Integer.parseInt(args[3]);
        final int result = new TwoPlayerGame(
                new TicTacToeBoard(n,m,k,players),
                players,
                new Scanner(System.in)
        ).play(true);
        switch (result) {
            case 1:
                System.out.println("First player won");
                break;
            case 2:
                System.out.println("Second player won");
                break;
            case 3:
                System.out.println("Third player won");
                break;
            case 4:
                System.out.println("Fourth player won");
                break;
            case 0:
                System.out.println("Draw");
                break;
            default:
                throw new AssertionError("Unknown result " + result);
        }
    }
}
