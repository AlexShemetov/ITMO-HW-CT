package markup;

import java.util.List;

public class Strikeout extends Markup {
    public Strikeout(List<Markup> lstParagraphs) {
        super(lstParagraphs);
    }

    @Override
    public void toMarkdown(StringBuilder markdownStr) {
        markdownStr.append("~");
        for (Markup par : lstOfMarkup) {
            par.toMarkdown(markdownStr);
        }
        markdownStr.append("~");
    }

    @Override
    public void toHtml(StringBuilder htmlStr) {
        htmlStr.append("<s>");
        for (Markup par : lstOfMarkup) {
            par.toHtml(htmlStr);
        }
        htmlStr.append("</s>");

    }
}