import java.io.*;
import java.nio.charset.StandardCharsets;

public class Scanner {
    private final Reader reader;
    private StringBuilder word = new StringBuilder();
    private StringBuilder num = new StringBuilder();
    private StringBuilder line = new StringBuilder();
    private char[] buf = new char[1024];
    private int bufIndex = 0;
    private char lastElem = ' ';

    public Scanner(InputStream stream) {
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    public Scanner(String str) {
        reader = new StringReader(str);
    }

    public String nextLine() {
        String str = line.toString();
        line = new StringBuilder();
        return str;
    }

    public boolean hasNextLine() {
        if (line.length() != 0) {
            return true;
        }

        try {
            return readLine();
        } catch (IOException ioe) {
            System.err.println("Input not found");
            System.err.println(ioe.getMessage());
        }

        return false;
    }

    public int nextInt() {
        int number = Integer.parseInt(num.toString());
        num = new StringBuilder();
        return number;
    }

    public boolean hasNextInt() {
        if (num.length() != 0) {
            return true;
        }

        try {
            if (!skipWhitespace()) {
                return false;
            }
            return readInt();
        } catch (IOException ioe) {
            System.err.println("Input not found");
            System.err.println(ioe.getMessage());
        }
        return false;
    }

    public String nextWord() {
        String str = word.toString();
        word = new StringBuilder();
        return str;
    }

    public boolean hasNextWord() {
        if (word.length() != 0) {
            return true;
        }

        try {
            if (!skipWhitespace()) {
                return false;
            }
            return readWord();
        } catch (IOException ioe) {
            System.err.println("Input not found");
            System.err.println(ioe.getMessage());
        }
        return false;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException ioe) {
            System.err.println("File already closed or not found");
            System.err.println(ioe.getMessage());
        }
    }

    private boolean readLine() throws IOException {
        while (reader.read(buf) != -1) {
            for (int i = 0; i < buf.length; i++) {
                char c = buf[i];
                if (c == '\n') {
                    break;
                }
                line.append(c);
            }
        }
        return line.length() > 0;
    }

//    private boolean readLine() throws IOException {
//        for (int i = reader.read(); i != -1; i = reader.read()) {
//            if ((char) i != '\n') {
//                line.append((char) i);
//            } else {
//                return true;
//            }
//        }
//
//        return line.length() > 0;
//    }

    private boolean readInt() throws IOException {
        if (Character.isDigit(lastElem) || lastElem == '-') {
            num.append(lastElem);
        }

        for (int i = reader.read(); i != -1; i = reader.read()) {
            if (Character.isDigit((char) i)) {
                num.append((char) i);
            } else {
                return num.length() > 0;
            }
        }

        return num.length() > 0;
    }

    private boolean readWord() throws IOException {
        word.append(lastElem);
        for (int i = reader.read(); i != -1; i = reader.read()) {
            if (isWord((char) i)) {
                word.append((char) i);
            } else {
                return word.length() > 0;
            }
        }

        return word.length() > 0;
    }

//    private boolean skipWhitespace() throws IOException {
//        while ()
//    }

    private boolean skipWhitespace() throws IOException {
        for (int i = reader.read(); i != -1; i = reader.read()) {
            if (!Character.isWhitespace((char) i)) {
                lastElem = (char) i;
                return true;
            }
        }

        return false;
    }

    private static boolean isWord(char elem) {
        return (Character.getType(elem) == Character.DASH_PUNCTUATION || Character.isLetter(elem) || elem == '\'');
    }
}
