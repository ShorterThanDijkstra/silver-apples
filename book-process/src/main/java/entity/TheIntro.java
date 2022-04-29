package entity;

import java.util.LinkedList;
import java.util.List;

public class TheIntro {
    private static final String TITLE = "INTRODUCTION to the Second Edition";
    private final List<String> paragraphs;

    public TheIntro() {
        paragraphs = new LinkedList<>();
    }

    public void appendParagraph(String paragraph) {
        paragraphs.add(paragraph);
    }

    public String getTitle() {
        return TITLE;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }
}
