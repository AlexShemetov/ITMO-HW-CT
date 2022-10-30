package markup;

import java.util.List;

public class Strong extends Markup { 
    public Strong(List<Markup> lstParagraphs) {
        super(lstParagraphs);
    }

    @Override
    public void toMarkdown(StringBuilder markdownStr) {
        markdownStr.append("__");
        for (Markup par : lstOfMarkup) {
            par.toMarkdown(markdownStr);
        }
        markdownStr.append("__");
    }

    @Override
    public void toHtml(StringBuilder htmlStr) {
        htmlStr.append("<strong>");
        for (Markup par : lstOfMarkup) {
            par.toHtml(htmlStr);
        }
        htmlStr.append("</strong>");

    }
}