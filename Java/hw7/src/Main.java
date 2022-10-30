import markup.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Paragraph paragraph = new Paragraph(List.of(
                new Strong(List.of(
                        new Text("1"),
                        new Strikeout(List.of(
                                new Text("2"),
                                new Emphasis(List.of(
                                        new Text("3"),
                                        new Text("4"),
                                        new Text("78")
                                )),
                                new Text("5")
                        )),
                        new Text("6")
                ))
        ));
        
        StringBuilder str = new StringBuilder();
        paragraph.toMarkdown(str);
        System.out.println(str);
        str = new StringBuilder();
        paragraph.toHtml(str);
        System.out.println(str);
    }
}
