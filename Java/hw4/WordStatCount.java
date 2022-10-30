import java.io.*;
import java.util.ArrayList;

public class WordStatCount {
  public static void sortBubble(ArrayList<String> words, ArrayList<Integer> counts) {
    boolean isSorted = false;
    int bufCount;
    String bufWords1, bufWords2;

    while (!isSorted) {
      isSorted = true;
      for(int i = 0; i < words.size() - 1; i++) {
        if (counts.get(i) > counts.get(i + 1)) {
          isSorted = false;

          bufCount = counts.get(i);
          counts.set(i, counts.get(i + 1));
          counts.set(i + 1, bufCount);

          bufWords1 = words.get(i);
          bufWords2 = words.get(i + 1);
          words.set(i, bufWords2);
          words.set(i + 1, bufWords1);
        }
      }
    }
  }

  private static void bufferedReader(
    String fileIn, 
    ArrayList<String> words, 
    ArrayList<Integer> counts) throws IOException {
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(
        new FileInputStream(fileIn), "utf8"
      )
    );
    try {
      readAndCount(reader, words, counts);
    } finally {
      reader.close();
    }
  }

  private static void readAndCount(
    BufferedReader reader, 
    ArrayList<String> words,
    ArrayList<Integer> counts) throws IOException {
    while (true) {
      String line = reader.readLine();
      if (line == null) {
        return;
      }
      line = line.toLowerCase() + " ";

      String word = "";
      int left = -1, right = -1;

      for (int i = 0; i < line.length(); i++) {
        if (Character.getType(line.charAt(i)) == Character.DASH_PUNCTUATION 
            || Character.isLetter(line.charAt(i))
            || line.charAt(i) == '\'') {
          if (right == -1) {
            left = i;
            right++;
          }
        } else {
          if (left == -1) {
            continue;
          }
          right = i;
          word = line.substring(left, right);
          left = -1;
          right = -1;
          boolean put = true;
          for (int j = 0; j < words.size(); j++) {
            if (word.compareTo(words.get(j)) == 0) {
              put = false;
              counts.set(j, counts.get(j) + 1);
              break;
            }
          }
          if (put) {
            words.add(word);
            counts.add(1);
          }
        }
      }
    }
  }

  private static void fileWriter(
    String fileOut, 
    ArrayList<String> words, 
    ArrayList<Integer> counts) throws IOException {
    BufferedWriter writer = new BufferedWriter(
      new OutputStreamWriter(
        new FileOutputStream(fileOut), "utf8"
      )
    );
    try {
      sortBubble(words, counts);
      for (int i = 0; i < words.size(); i++) {
        writer.write(words.get(i) + " " + counts.get(i) + "\n");
      }
    } finally {
      writer.close();
    }   
  }

  public static void main(String[] args) {
    ArrayList<String> words = new ArrayList<String>();
    ArrayList<Integer> counts = new ArrayList<Integer>();
    
    try {
      bufferedReader(args[0], words, counts);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }    

    try {
      fileWriter(args[1], words, counts);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }
}