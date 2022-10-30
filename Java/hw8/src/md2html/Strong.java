package md2html;

import java.util.List;

public class Strong extends Markup {
    public Strong(List<Markup> markups, String tagMd) {
        super(markups, tagMd, "<strong>", "</strong>");
    }

    public Strong(Markup markup, String typeTagMd) {
        super(List.of(markup), typeTagMd, "<strong>", "</strong>");
    }

    public Strong(String typeTagMd) {
        super(List.of(), typeTagMd, "<strong>", "</strong>");
    }

    public void toMarkdown(StringBuilder str) {
        addTags(str, tagMd, tagMd, modeMd);
    }

    public void toHtml(StringBuilder str) {
        addTags(str, tagHtmlOpen, tagHtmlClose, modeHtml);
    }
}