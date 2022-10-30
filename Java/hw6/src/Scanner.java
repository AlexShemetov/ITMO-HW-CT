import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Scanner {
    private final FileReader reader;
    private char[] buf = new char[1024];
    private boolean fileReaded = false;
    private int indexBuf = 0;
    private StringBuilder word = new StringBuilder();
    private int lastWordLine = 0;

    public Scanner(String file) throws IOException {
        reader = new FileReader(file, StandardCharsets.UTF_8);
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException ioe) {
            System.err.println("input not found" + ioe.getMessage());
        }
    }

    public int getLastWordLine() {
        return lastWordLine;
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

        if (fileReaded) {
            return false;
        }

        try {
            if (!skipWhitespace()) {
                return false;
            }
            return readWord();
        } catch (IOException ioe) {
            System.err.println("Input not found");
            System.out.println(ioe.getMessage());
        }
        return false;
    }

    private boolean readWord() throws IOException {
        int c = buf.length;
        do {
            for (; indexBuf < buf.length; indexBuf++) {
                if (checkElem(buf[indexBuf])) {
                    word.append(buf[indexBuf]);
                } else {
                    if (buf[indexBuf] == '\n') {
                        lastWordLine++;
                    }
                    if (c != buf.length) {
                        Arrays.fill(buf, ' ');
                    }
                    return word.length() > 0;
                }
            }
            indexBuf = 0;
        } while ((c = reader.read(buf)) != -1 && checkElem(buf[indexBuf]));

        fileReaded = true;
        return word.length() > 0;
    }

    private boolean skipWhitespace() throws IOException {
        do {
            for (; indexBuf < buf.length; indexBuf++) {
                if (checkElem(buf[indexBuf])) {
                    return true;
                }
            }
            indexBuf = 0;
        } while (reader.read(buf) != -1);

        fileReaded = true;
        return false;
    }

    private static boolean checkElem(char elem) {
        return (Character.getType(elem) == Character.DASH_PUNCTUATION || Character.isLetter(elem) || elem == '\'');
    }
}
