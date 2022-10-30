package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        int row = -1, col = -1;

        while (true) {
            try {
                row = in.nextInt() - 1;
                col = in.nextInt() - 1;
            } catch (InputMismatchException ime) {
                System.out.println("Enter a number!");
                this.in = new Scanner(System.in);
                continue;
            }
            break;
        }

        return new Move(row, col, position.getTurn());
    }
}
