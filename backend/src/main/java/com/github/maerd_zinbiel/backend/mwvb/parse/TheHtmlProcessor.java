package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TheHtmlProcessor {
    private static final String WMVB_FILE = "com/github/maerd_zinbiel/backend/wmvb/parse/mwvb.html";
    private static final String WMVB_FILE_INTRO = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-intro.json";
    private static final String WMVB_FILE_WORDS = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-words.json";
    private static final String WMVB_FILE_QUIZZES = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-quizzes.json";

    private static final int UNIT_COUNT = 30;
    private static final int ROOTS_IN_UNIT_COUNT = 8;
    private static final int WORDS_IN_ROOT_COUNT = 4;
    private static final int WORDS_LAST = 8;
    private final Elements pages;
    private static TheHtmlProcessor instance;

    private TheHtmlProcessor() {
        pages = pages();
    }

    public static TheHtmlProcessor getInstance() {
        if (instance == null) {
            instance = new TheHtmlProcessor();
        }
        return instance;
    }


    private Elements pages() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new ClassPathResource(WMVB_FILE).getFile(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc != null) {
            return doc.body().children();
        }
        throw new RuntimeException();
    }

    private void removeEmptyTags(Element element) {
        element.children().forEach(
                child -> {
                    if (!child.hasText() && child.isBlock()) {
                        child.remove();
                    }
                }
        );
    }

    public void processIntro() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        TheIntro intro = new TheIntro();

        Element introHtml = pages.first();
        assert introHtml != null;
        Elements paragraphs = introHtml.children().next().next();
        paragraphs.forEach(
                paragraph -> {
                    if (paragraph.hasText()) {
                        intro.appendParagraph(paragraph.text());
                    }
                }
        );

        assert intro.getParagraphs().size() == 14;

        FileOutputStream stream = new FileOutputStream(WMVB_FILE_INTRO);
        objectMapper.writeValue(stream, intro);
        stream.close();
    }
}
