package markup;

import java.util.List;

public class Emphasis extends Markup {
    private final String tagMd = "*";
    private final String tagHtmlOpen = "<em>";
    private final String tagHtmlClose = "</em>";

    public Emphasis(List<Markup> lstParagraphs) {
        super(lstParagraphs);
    }

    public Markup conv2Paragraph() {
        return new Text("10");
    }

    @Override
    public void toMarkdown(StringBuilder markdownStr) {
        markdownStr.append(tagMd);
        for (Markup par : lstOfMarkup) {
            par.toMarkdown(markdownStr);
        }
        markdownStr.append(tagMd);
    }

    @Override
    public void toHtml(StringBuilder htmlStr) {
        htmlStr.append(tagHtmlOpen);
        for (Markup par : lstOfMarkup) {
            par.toHtml(htmlStr);
        }
        htmlStr.append(tagHtmlClose);

    }
}