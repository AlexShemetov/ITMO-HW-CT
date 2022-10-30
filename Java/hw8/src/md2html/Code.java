package md2html;

import java.util.List;

public class Code extends Markup {
    public Code(List<Markup> markups) {
        super(markups, "`", "<code>", "</code>");
    }

    public Code(Markup markup) {
        super(List.of(markup), "`", "<code>", "</code>");
    }

    public Code() {
        super(List.of(), "`", "<code>", "</code>");
    }

    public void toMarkdown(StringBuilder str) {
        addTags(str, tagMd, tagMd, modeMd);
    }

    public void toHtml(StringBuilder str) {
        addTags(str, tagHtmlOpen, tagHtmlClose, modeHtml);
    }
}
