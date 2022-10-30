import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;

public class Wspp {
    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> counter = new LinkedHashMap<>();
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
        Scanner scn = new Scanner(fileIn);
        int position = 0;
        while (scn.hasNextWord()) {
            String word = scn.nextWord().toLowerCase();
            if (counter.get(word) != null) {
                counter.get(word).set(0, counter.get(word).get(0) + 1);
                counter.get(word).add(++position);
            } else {
                ArrayList<Integer> adder = new ArrayList<>();
                adder.add(1);
                adder.add(++position);
                counter.put(word, new ArrayList<>(adder));
            }
        }
        scn.close();
    }

    private static void fileWriter(String fileOut, Map<String, ArrayList<Integer>> counter)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut), StandardCharsets.UTF_8))) {
            for (String key : counter.keySet()) {
                writer.write(key + " " + counter.get(key).get(0));
                for (int i = 1; i < counter.get(key).size(); i++) {
                    writer.write(" " + counter.get(key).get(i));
                }
                writer.write("\n");
            }
        }
    }
}