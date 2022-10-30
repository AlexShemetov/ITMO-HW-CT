import java.util.*;
import java.io.*;

public class WsppSortedPosition {
    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> counter = new TreeMap<>();
        try {
            fileReader(args[0], counter);
            try {
                fileWriter(args[1], counter);
            } catch (IOException ioe) {
                System.out.println("Output not found");
                System.out.println(ioe.getMessage());
            }
        } catch (IOException ioe) {
            System.out.println("File not found" + ioe.getMessage());
        }
    }

    private static void fileReader(String fileIn, Map<String, ArrayList<Integer>> counter)
        throws IOException {
//        Scanner scn = new Scanner(new FileInputStream(fileIn));
//        int position = 0;
//        int line = 0;
//        while (scn.hasNextWord()) {
//            String word = scn.nextWord().toLowerCase();
//            if (scn.getLastWordLine() > line) {
//                line++;
//                position = 0;
//            }
//            if (counter.get(word) != null) {
//                counter.get(word).set(0, counter.get(word).get(0) + 1);
//                counter.get(word).add(line + 1);
//                counter.get(word).add(++position);
//            } else {
//                ArrayList<Integer> adder = new ArrayList<>();
//                adder.add(1);
//                adder.add(line + 1);
//                adder.add(++position);
//                counter.put(word, new ArrayList<>(adder));
//            }
//        }
    }

    private static void fileWriter(String fileOut, Map<String, ArrayList<Integer>> counter)
           throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut), "utf-8"));
        try {
            for (String key : counter.keySet()) {
                writer.write(key + " " + counter.get(key).get(0));
                for (int i = 1; i < counter.get(key).size(); i+=2) {
                    writer.write(" " + counter.get(key).get(i) + ":" + counter.get(key).get(i + 1));
                }
                writer.write("\n");
            }
        } finally {
            writer.close();
        }
    }
}
