package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TheHtmlParser {
    private static final String WMVB_FILE = "com/github/maerd_zinbiel/backend/wmvb/parse/mwvb.html";
    private static final int UNIT_COUNT = 30;
    private static final int ROOTS_IN_UNIT_COUNT = 8;
    private static final int WORDS_IN_ROOT_COUNT = 4;
    private static final int WORDS_IN_MYTHOLOGY_AND_HISTORY = 8;

    private final TheIntro theIntro;
    private List<Unit> units;
    private static TheHtmlParser instance;

    private TheHtmlParser() {
        Elements pages = pages();
        theIntro = new TheIntro(pages.first().html());

        Elements rest = pages.next();
        units = new ArrayList<>();
        rest = parseUnits(rest);
        parseAnswers(rest);
    }

    private Elements parseRoot(Elements elements, Unit unit, int descIndex) {
        Element page = elements.first();

        String rootDesc = page.child(descIndex).text();
        String rootName = rootDesc.split(" ")[0];
        Root root = new Root(rootName, rootDesc);
        unit.appendRoot(root);

        elements = elements.next();
        for (int i = 0; i < WORDS_IN_ROOT_COUNT; i++) {
            elements = parseWord(elements, root);
        }
        return elements;
    }

    private Elements parseWord(Elements elements, Root root) {
        Element page = elements.first();

        assert page.children().size() >= 4;

        String[] info = page.child(1).text().split(" ", 2);
        String spell = info[0];
        String explain = info[1];

        Sentence sentence = new Sentence(page.child(2).text());

        String detail = page.child(3).text();

        Word word = new Word(spell, explain, sentence, detail);
        root.appendWord(word);

        return elements.next();
    }

    private Elements parseUnit(Elements elements) {
        final Unit unit = new Unit();
        for (int i = 0; i < ROOTS_IN_UNIT_COUNT / 2; i++) {
            if (i == 0) {
                elements = parseRoot(elements, unit, 3);
            } else {
                elements = parseRoot(elements, unit, 1);
            }
            elements = parseRoot(elements, unit, 1);
            elements = parseQuiz(elements, unit);
        }

        elements = parseMythologyAndHistory(elements, unit);
        elements = parseQuiz(elements, unit);

        units.add(unit);
        return elements;
    }

    private Elements parseMythologyAndHistory(Elements elements, Unit unit) {
        for (int i = 0; i < WORDS_IN_MYTHOLOGY_AND_HISTORY; i++) {
            elements = elements.next();
        }
        return elements;
    }

    private boolean isQuizPage(Element page) {
        String info = page.child(1).text();
        if (info.startsWith("Quiz ") || info.startsWith("Review Quizzes ")) {
            return true;
        }
        for (int i = 0; i < 26; i++) {
            String start = (char) ('A' + i) + ". ";
            if (info.startsWith(start)) {
                return true;
            }
        }
        return false;
    }

    private Elements parseQuiz(Elements elements, Unit unit) {
        Element page = elements.first();
        while (isQuizPage(page)) {
            elements = elements.next();
            page = elements.first();
        }
        return elements;
    }

    private Elements parseUnits(Elements elements) {
        while (elements.first().child(1).text().startsWith("Unit ")) {
            elements = parseUnit(elements);
        }
        System.out.println(elements.first().text());
        System.out.println(units.size());
        assert units.size() == UNIT_COUNT;

        return elements;
    }

    private void parseAnswers(Elements elements) {
    }


    public static TheHtmlParser getInstance() {
        if (instance == null) {
            instance = new TheHtmlParser();
        }
        return instance;
    }

    public TheIntro getTheIntro() {
        return theIntro;
    }

    public List<Unit> getUnits() {
        return units;
    }

    private Elements pages() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new ClassPathResource(WMVB_FILE).getFile(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc != null) {
            return doc.body().select(".page");
        }
        throw new RuntimeException();
    }

}
