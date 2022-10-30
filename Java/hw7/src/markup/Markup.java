package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class Markup {
    protected List<Markup> lstOfMarkup;

    abstract Markup conv();

    protected Markup(List<Markup> markupLst) {
        lstOfMarkup = new ArrayList<>();
        for (int i = 0; i < markupLst.size(); i++) {
            lstOfMarkup.add(markupLst.get(i));
        }
    }

    public void toMarkdown(StringBuilder markdownStr) {
        for (Markup mrk : lstOfMarkup) {
            mrk.toMarkdown(markdownStr);
        }
    }

    public void toHtml(StringBuilder htmlStr) {
        for (Markup mrk :lstOfMarkup) {
            mrk.toHtml(htmlStr);
        }
    }
}
