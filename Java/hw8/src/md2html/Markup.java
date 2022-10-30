package md2html;

import java.util.ArrayList;
import java.util.List;

public abstract class Markup {
    protected ArrayList<Markup> markups;
    protected final String modeMd = "md";
    protected final String modeHtml = "html";

    protected String tagMd;
    protected String tagHtmlOpen;
    protected final String tagHtmlClose;

    public Markup(List<Markup> markups, String tagMd, String tagHtmlOpen, String tagHtmlClose) {
        this.markups = new ArrayList<>(markups);
        this.tagMd = tagMd;
        this.tagHtmlOpen = tagHtmlOpen;
        this.tagHtmlClose = tagHtmlClose;
    }

    abstract void toMarkdown(StringBuilder str);
    abstract void toHtml(StringBuilder str);

    public void getMdStruct(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (tagMd.length() > 0 && str.charAt(i) == tagMd.charAt(0) && tagMd.charAt(0) != '#') {
                if (tagMd.length() == 2 && i + 1 < str.length() && str.charAt(i + 1) == tagMd.charAt(i + 1) || tagMd.length() == 1) {
                    return;
                }
            }

            boolean isStrong = false;
            switch(str.charAt(i)) {
                case('-'):
                    if (i + 1 < str.length() && str.charAt(i + 1) == '-') {
                        for (int j = i + 2; j < str.length() - 1; j++) {
                            if (str.charAt(j) == '-' && str.charAt(j + 1) == '-') {
                                markups.add(new Strikeout());
                                markups.get(markups.size() - 1).getMdStruct(str.substring(i + 1, j));
                                i = j + 1;
                                break;
                            }
                        }
                    }
                    break;

                case('*'):
                    if (str.charAt(i + 1) == '*') {
                        isStrong = true;
                    }

                    for (int j = i + 2; j < str.length() - 1; j++) {
                        if (str.charAt(j) == '*') {
                            if (isStrong && str.charAt(j + 1) == '*') {
                                markups.add(new Strong("**"));
                                markups.get(markups.size() - 1).getMdStruct(str.substring(i + 1, j));
                                i = j + 1;
                                break;
                            } 
                            if (!isStrong) {
                                markups.add(new Emphasis("*"));
                                markups.get(markups.size() - 1).getMdStruct(str.substring(i + 1, j));
                                i = j;
                                break;
                            }
                        }
                    }
                    break;

                case('_'):
                    if (str.charAt(i + 1) == '_') {
                        isStrong = true;
                    }

                    for (int j = i + 2; j < str.length() - 1; j++) {
                        if (str.charAt(j) == '_') {
                            if (isStrong && str.charAt(j + 1) == '_') {
                                markups.add(new Strong("__"));
                                markups.get(markups.size() - 1).getMdStruct(str.substring(i + 2, j));
                                i = j + 1;
                                break;
                            } 
                            if (!isStrong) {
                                markups.add(new Emphasis("_"));
                                markups.get(markups.size() - 1).getMdStruct(str.substring(i + 1, j));
                                i = j;
                                break;
                            }
                        }
                    }
                    break;

                case('`'):
                    for (int j = i + 1; j < str.length(); j++) {
                        if (str.charAt(j) == '`') {
                            markups.add(new Code());
                            markups.get(markups.size() - 1).getMdStruct(str.substring(i + 1, j));
                            i = j;
                            break;
                        }
                    }
                    break;

                default:
                    StringBuilder text = new StringBuilder();
                    while (i < str.length() && str.charAt(i) != '*' && str.charAt(i) != '_' && str.charAt(i) != '-') {
                        text.append(str.charAt(i));
                        i++;
                    }
                    i--;
                    markups.add(new Text(text.toString()));
                    break;
            }
        }

        return;
    }

    protected void addTags(StringBuilder str, String tagOpen, String tagClose, String mode) {
        if (!tagOpen.isEmpty()) {
            str.append(tagOpen);
        }

        if (mode.equals(modeMd)) {
            for (Markup markup : markups) {
                markup.toMarkdown(str);
            }
        }

        if (mode.equals(modeHtml)) {
            for (Markup markup : markups) {
                markup.toHtml(str);
            }
        }

        if (!tagOpen.isEmpty()) {
            str.append(tagClose);
        }
    }
}
