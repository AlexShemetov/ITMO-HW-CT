package md2html;

import java.util.List;

public class Strikeout extends Markup { 
    public Strikeout(List<Markup> markups) {
        super(markups, "--", "<s>", "</s>");
    }

    public Strikeout(Markup markup) {
        super(List.of(markup), "--", "<s>", "</s>");
    }

    public Strikeout() {
        super(List.of(), "--", "<s>", "</s>");
    }

    public void toMarkdown(StringBuilder str) {
        addTags(str, tagMd, tagMd, modeMd);
    }

    public void toHtml(StringBuilder str) {
        addTags(str, tagHtmlOpen, tagHtmlClose, modeHtml);
    }
}
