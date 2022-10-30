import java.io.IOException;

public class Revers {
  public static void main(String[] args) {
    try {
      MyScanner from_in = new MyScanner(System.in);
      int[][] arr = new int[1_000_000][];
      int[] elem = new int[1_000_000];
      int sizeOfArrays = 0;

      while (from_in.hasNextLine()) {
        try {
          MyScanner from_str = new MyScanner(from_in.nextLine());
          int sizeOfElem = 0;

          while (from_str.hasNextInt()) {
            elem[sizeOfElem] = from_str.nextInt();
            sizeOfElem++;
          }

          arr[sizeOfArrays] = new int[sizeOfElem];
          for (int i = 0; i < sizeOfElem; i++) {
            arr[sizeOfArrays][i] = elem[i];
          }
          sizeOfArrays++;
        } catch (IOException ioe) {
          System.out.println("Input not found");
        }
      }

      for (int i = sizeOfArrays - 1; i >= 0; i--) {
        for (int j = arr[i].length - 1; j >= 0; j--) {
          System.out.print(arr[i][j]);
          System.out.print(" ");
        }
        System.out.println();
      }
    } catch (IOException ioe) {
      System.out.println("Input not found");
    }
  }
}