import java.io.IOException;
import java.util.Scanner;

public class ReverseTranspose {
  public static void main(String[] args) {
    try {
      Scanner fromIn = new Scanner(System.in);
      int[][] arr = new int[1_000_000][];
      int[] elem = new int[1_000_000];
      int sizeOfArrays = 0;
      int maxSizeOfElems = 0;

      while (fromIn.hasNextLine()) {
        try {
          Scanner fromStr = new Scanner(fromIn.nextLine());
          fromStr.setRadix(16);
          int sizeOfElem = 0;

          while (fromStr.hasNextInt()) {
            elem[sizeOfElem] = fromStr.nextInt();
            sizeOfElem++;
          }

          if (maxSizeOfElems < sizeOfElem) {
            maxSizeOfElems = sizeOfElem;
          }

          arr[sizeOfArrays] = new int[sizeOfElem];
          for (int i = 0; i < sizeOfElem; i++) {
            arr[sizeOfArrays][i] = elem[i];
          }
          sizeOfArrays++;
        } catch (IOException ioe) {
          System.out.println("loer");
        }
      }

      for (int j = 0; j < maxSizeOfElems; j++) {
        for (int i = 0; i < sizeOfArrays; i++) {
          if (j < arr[i].length) {
            System.out.print(arr[i][j]);
            System.out.print(" ");
          }
        }
        System.out.println();
      }
    } catch (IOException ioe) {
      System.out.println("input not found");
    }
  }
}