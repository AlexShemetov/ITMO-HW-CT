import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class Scanner {
    // объявление сканера
    // формирование listLines из массива символов
    // формирование listInLine из строки
    // ArrayList <String> listLines - содержит линии
    // Integer indexLine - индекс линии
    // ArrayList <String> listInLine - содержит слова из линии
    // Integer indexInLine - индекс слова в линии
    // hasNextLine()
    // Если listLine.size() > indexLine return True else return False
    // hasNext()
    // Если listInLine.size() > listInLine return True else return False
    // next()
    // return indexInLine[indexInLine++]
    // nextLine()
    // return listLines[indexLine++] + обновление listInLine
    String text;
    ArrayList<String> lines;
    ArrayList<String> words;
    ArrayList<String> numbers;
    Integer indexLine = -1;
    Integer indexWord = -1;
    Integer indexNumber = -1;

    public Scanner(InputStream source) {
        StringBuffer buf = new StringBuffer();
        try {
            for (int i = System.in.read(); i != -1; i = System.in.read()) {
                buf.append((char) i);
            }
        } catch (IOException e) {
            System.out.println("Can't read");
            System.out.println(e.getMessage());
        }
        text = buf.toString() + "\n";
        this.text = text;
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        int leftWord = 0;
        int leftLine = 0;
        int leftNumber = 0;
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            // По словам
            if (Character.isWhitespace(symbol)) {
                if (leftWord != i) {
                    String word = text.substring(leftWord, i);
                    words.add(word);
                }
                leftWord = i + 1;
            }
            // По линиям
            if (Character.getType(symbol) == 15) {
                if (leftLine != i) {
                    String line = text.substring(leftLine, i);
                    lines.add(line);
                } else if (symbol == '\r') {
                    lines.add("");
                }
                leftLine = i + 1;
            }
            // по числам
            if (Character.getType(symbol) != 9 && symbol != '-') {
                if (leftNumber != i) {
                    String number = text.substring(leftNumber, i);
                    numbers.add(number);
                }
                leftNumber = i + 1;
            }
        }
        this.lines = lines;
        this.words = words;
        this.numbers = numbers;
    }

    public Scanner(String text) {
        text = text + "\n";
        this.text = text;
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        int leftWord = 0;
        int leftLine = 0;
        int leftNumber = 0;
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            // По словам
            if (Character.isWhitespace(symbol)) {
                if (leftWord != i) {
                    String word = text.substring(leftWord, i);
                    words.add(word);
                }
                leftWord = i + 1;
            }
            // По линиям
            if (Character.getType(symbol) == 15) {
                if (leftLine != i) {
                    String line = text.substring(leftLine, i);
                    lines.add(line);
                } else if (symbol == '\r') {
                    lines.add("");
                }
                leftLine = i + 1;
            }
            // по числам
            if (Character.getType(symbol) != 9 && symbol != '-') {
                if (leftNumber != i) {
                    String number = text.substring(leftNumber, i);
                    numbers.add(number);
                }
                leftNumber = i + 1;
            }
        }
        this.lines = lines;
        this.words = words;
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
