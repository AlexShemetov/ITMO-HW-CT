package md2html;

import java.util.List;

public class Text extends Markup {
    private StringBuilder str;

    public Text(String str) {
        super(List.of(), "", "", "");
        this.str = new StringBuilder(str);
    }

    public void toMarkdown(StringBuilder str) {
        str.append(this.str);
    }

    public void toHtml(StringBuilder str) {
        str.append(this.str);
    }
}
