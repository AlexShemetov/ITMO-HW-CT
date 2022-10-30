import java.util.ArrayList;

public class ReverseTranspose {
    public static void main(String[] args) {
        Scanner fromIn = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        //int[][] arr = new int[1_000_000][];
        //int[] elem = new int[1_000_000];
        ArrayList<Integer> elem = new ArrayList<>();
        //int sizeOfArrays = 0;
        int maxSizeOfElems = 0;

        while (fromIn.hasNextLine()) {
            Scanner fromStr = new Scanner(fromIn.nextLine());
            int sizeOfElem = 0;

            while (fromStr.hasNextInt()) {
//                elem[sizeOfElem] = fromStr.nextInt();
//                sizeOfElem++;
                elem.add(fromStr.nextInt());
            }

            if (maxSizeOfElems < elem.size()) {
                maxSizeOfElems = elem.size();
            }

            arr.add(new ArrayList<Integer>(elem));
            elem = new ArrayList<>();
//
//            arr[sizeOfArrays] = new int[sizeOfElem];
//            System.arraycopy(elem, 0, arr[sizeOfArrays], 0, sizeOfElem);
//            sizeOfArrays++;
        }

//        for (int i = sizeOfArrays - 1; i > -1; i--) {
//            for (int j = maxSizeOfElems - 1; j > -1; j--) {
//                if (j < arr[i].length) {
//                    System.out.print(arr[i][j]);
//                    System.out.print(" ");
//                }
//            }
//            System.out.println();
//        }

        for (int j = 0; j < maxSizeOfElems; j++) {
            for (int i = 0; i < arr.size(); i++) {
                if (j < arr.get(i).size()) {
                    System.out.print(arr.get(i).get(j));
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
