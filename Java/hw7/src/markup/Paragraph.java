package markup;

import java.util.List;

public class Paragraph extends Markup {
    public Paragraph(List<Markup> lstParagraphs) {
        super(lstParagraphs);
    }

    public Markup conv() {
        return new Paragraph(List.of(new Text("10")));
    }

    @Override
    public void toMarkdown(StringBuilder markdownStr) {
        for (Markup par : lstOfMarkup) {
            par.toMarkdown(markdownStr);
        }
    }

    @Override
    public void toHtml(StringBuilder htmlStr) {
        for (Markup par : lstOfMarkup) {
            par.toHtml(htmlStr);
        }
    }
}