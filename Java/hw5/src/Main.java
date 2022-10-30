import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scn = new Scanner(new FileInputStream("input.txt"));
            while (scn.hasNextLine()) {
                System.out.println(scn.nextLine());
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found");
            System.err.println(fnfe.getMessage());
        }
    }
}