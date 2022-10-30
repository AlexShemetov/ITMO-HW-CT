package md2html;

import java.util.List;

public class Paragraph extends Markup {
    public Paragraph(List<Markup> markups) {
        super(markups, "", "<p>", "</p>");
    }

    public Paragraph(Markup markup) {
        super(List.of(markup), "", "<p>", "</p>");
    }

    public Paragraph() {
        super(List.of(), "", "<p>", "</p>");
    }

    public void toMarkdown(StringBuilder str) {
        addTags(str, "", "", modeMd);
    }

    public void toHtml(StringBuilder str) {
        addTags(str, tagHtmlOpen, tagHtmlClose, modeHtml);
    }
}
