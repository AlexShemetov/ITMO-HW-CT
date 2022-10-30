package md2html;

import java.util.List;

public class Header extends Markup {
    public Header(List<Markup> markups, int level) {
        super(markups, "", "<h", "</h>");
        setTags(level);
    }

    public Header(Markup markup, int level) {
        super(List.of(markup), "", "<h", "</h>");
        setTags(level);
    }

    public Header(int level) {
        super(List.of(), "", "<h", "</h>");
        setTags(level);
    }

    private void setTags(int level) {
        tagHtmlOpen = tagHtmlOpen + Integer.toString(level) + ">";
        for (int i = 0; i < level; i++) {
            tagMd += "#";
        }
    }

    public void toMarkdown(StringBuilder str) {
        addTags(str, tagMd, "", modeMd);
    }

    public void toHtml(StringBuilder str) {
        addTags(str, tagHtmlOpen, tagHtmlClose, modeHtml);
    }
}
