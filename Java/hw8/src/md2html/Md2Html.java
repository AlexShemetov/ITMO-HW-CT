package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        String inFile = args[0];
        String outFile = args[1];
        
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(
                                        new File(inFile)), StandardCharsets.UTF_8));
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(
                                        new File(outFile)), StandardCharsets.UTF_8))
        ) {
            String line = in.readLine();
            while (line != null) {
                StringBuilder paragraph = new StringBuilder();

                while (line.isEmpty()) {
                    line = in.readLine();
                }
                while (line != null && !line.isEmpty()) {
                    if (paragraph.length() != 0) {
                        paragraph.append('\n');
                    }
                    paragraph.append(line);
                    line = in.readLine();
                }

                StringBuilder str = new StringBuilder();
                if (paragraph.charAt(0) == '#') {
                    int i = 0;
                    while (paragraph.charAt(i) == '#') {
                        i++;
                    }
                    if (paragraph.charAt(i) == ' ') {
                        Header hd = new Header(i);
                        hd.getMdStruct(paragraph.toString().substring(i + 1, paragraph.length()));
                        hd.toHtml(str);
                    } else {
                        Paragraph pr = new Paragraph();
                        pr.getMdStruct(paragraph.toString());
                        pr.toHtml(str);
                    }
                } else {
                    Paragraph pr = new Paragraph();
                    pr.getMdStruct(paragraph.toString());
                    pr.toHtml(str);
                }
                out.write(str.toString() + "\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}