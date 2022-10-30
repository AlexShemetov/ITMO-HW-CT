package md2html;

import java.util.List;

public class Emphasis extends Markup {
    public Emphasis(List<Markup> markups, String tagMd) {
        super(markups, tagMd, "<em>", "</em>");
    }

    public Emphasis(Markup markup, String tagMd) {
        super(List.of(markup), tagMd, "<em>", "</em>");
    }

    public Emphasis(String tagMd) {
        super(List.of(), tagMd, "<em>", "</em>");
    }

    public void toMarkdown(StringBuilder str) {
        addTags(str, tagMd, tagMd, modeMd);
    }

    public void toHtml(StringBuilder str) {
        addTags(str, tagHtmlOpen, tagHtmlClose, modeHtml);
    }
}