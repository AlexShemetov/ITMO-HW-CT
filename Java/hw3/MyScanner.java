import java.io.*;
import java.util.ArrayList;

class MyScanner {
   private String text;
   private ArrayList<String> lines;
   private ArrayList<String> words;
   private ArrayList<String> numbers;
   private Integer indexLine = -1;
   private Integer indexWord = -1;
   private Integer indexNumber = -1;
   private Reader reader;

   public MyScanner(InputStream in) throws IOException {
      this.reader = new InputStreamReader(in);
      readInput();
   }

   public MyScanner(InputStream in, String code) throws IOException {
      this.reader = new InputStreamReader(in, code);
      readInput();
   }

   public MyScanner(File file) throws IOException {
      this.reader = new InputStreamReader(
                     new FileInputStream(file.getName())
                    );
      readInput();
   }

   public MyScanner(File file, String code) throws IOException {
      this.reader = new InputStreamReader(
                     new FileInputStream(file.getName()), code
                    );
      readInput();
   }

   public MyScanner(String str) throws IOException {
      this.reader = new StringReader(str);
      readInput();
   }

   private void readInput() {
      StringBuffer buf = new StringBuffer();
      try {
         for(int i = reader.read(); i != -1; i = reader.read()) {
            buf.append((char) i);
         }
      } catch (IOException e) {
         System.out.println("Input not found");
         System.out.println(e.getMessage());
      }
      this.text = buf.toString() + "\n";
      
      buildLines();
      buildNumbers();
      buildWords();
   }

   private void buildLines() {
      ArrayList<String> lines = new ArrayList<>();
      int leftLine = 0;
      for (int i = 0; i < text.length(); i++) {
         char symbol = text.charAt(i);
         if (Character.getType(symbol) == 15) {
            if (leftLine != i) {
               String line = text.substring(leftLine, i);
               lines.add(line);
            } else if (symbol == '\r') {
               lines.add("");
            }
            leftLine = i + 1;
         }
      }
      this.lines = lines;
   }

   private void buildWords() {
      ArrayList<String> words = new ArrayList<>();
      int leftWord = 0;
      for (int i = 0; i < text.length(); i++) {
         char symbol = text.charAt(i);
         if (Character.isWhitespace(symbol)) {
            if (leftWord != i) {
               String word = text.substring(leftWord, i);
               words.add(word);
            }
            leftWord = i + 1;
         }
      }
      this.words = words;
   }

   private void buildNumbers() {
      ArrayList<String> numbers = new ArrayList<>();
      int leftNumber = 0;
      for (int i = 0; i < text.length(); i++) {
         char symbol = text.charAt(i);
         if (Character.getType(symbol) != 9 && symbol != '-') {
            if (leftNumber != i) {
               String number = text.substring(leftNumber, i);
               numbers.add(number);
            }
            leftNumber = i + 1;
         }
      }
      this.numbers = numbers;
   }

   public boolean hasNextInt() {
      if (this.indexNumber < this.numbers.size() - 1) {
         return true;
      } else {
         return false;
      }
   }

   public boolean hasNextWord() {
      if (this.indexWord < this.words.size() - 1) {
         return true;
      } else {
         return false;
      }
   }

   public boolean hasNextLine() {
      if (this.indexLine < this.lines.size() - 1) {
         return true;
      } else {
         return false;
      }
   }

   public String nextLine() {
      this.indexLine += 1;
      String line = lines.get(indexLine);
      return line;
   }

   public String nextWord() {
      this.indexWord += 1;
      String word = words.get(indexWord);
      return word;
   }

   public Integer nextInt() {
      this.indexNumber += 1;
      Integer number = Integer.parseInt(numbers.get(indexNumber));
      return number;
   }

}
