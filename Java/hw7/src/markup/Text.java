package markup;

import java.util.List;

public class Text extends Markup {
    private String str;

    public Text(String str) {
        super(List.of());
        this.str = str;
    }


    @Override
    public void toMarkdown(StringBuilder markdownStr) {
        markdownStr.append(str);
    }

    @Override
    public void toHtml(StringBuilder htmlStr) {
        htmlStr.append(str);
    }
}