import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReverseHex2 {
  public static void main(String[] args) {
      Scanner fromIn = new Scanner(System.in);
      int[][] arr = new int[1_000_000][];
      int[] elem = new int[1_000_000];
      int sizeOfArrays = 0;
      int maxSizeOfElems = 0;

      while (fromIn.hasNextLine()) {
          Scanner fromStr = new Scanner(fromIn.nextLine());
          int sizeOfElem = 0;

          while (fromStr.hasNextInt()) {
            elem[sizeOfElem] = fromStr.nextInt();
            sizeOfElem++;
          }

          if (maxSizeOfElems < sizeOfElem) {
            maxSizeOfElems = sizeOfElem;
          }

          arr[sizeOfArrays] = new int[sizeOfElem];
          System.arraycopy(elem, 0, arr[sizeOfArrays], 0, sizeOfElem);
          sizeOfArrays++;
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
  }
}