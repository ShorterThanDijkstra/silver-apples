package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class TheHtmlParser {
    private static final String WMVB_FILE = "com/github/maerd_zinbiel/backend/wmvb/parse/mwvb.html";
    private static final int UNIT_COUNT = 30;
    private static final int ROOTS_IN_UNIT_COUNT = 8;
    private static final int WORDS_IN_ROOT_COUNT = 4;
    private static final int WORDS_IN_MYTHOLOGY_AND_HISTORY = 8;

    private final TheIntro theIntro;
    private final List<Unit> units;
    private static TheHtmlParser instance;

    private TheHtmlParser() {
        Elements pages = pages();

        theIntro = new TheIntro(pages.first().html());

        Elements rest = pages.next();
        units = new LinkedList<>();
        rest = parseUnits(rest);
        parseAnswers(rest);
    }

    private Elements parseRoot(Elements elements, Unit unit, int descIndex) {
        Element page = elements.first();

        String rootDesc = page.child(descIndex).text();
        String rootName = rootDesc.split(" ")[0];
//        String rootName = "for test";
//        String rootDesc = "for test";
        Root root = new Root(rootName, rootDesc);
        unit.appendRoot(root);

        elements = elements.next();
        for (int i = 0; i < WORDS_IN_ROOT_COUNT; i++) {
            elements = parseWord(elements, root, 1, 2, 3);
        }
        return elements;
    }

    private Elements parseWord(Elements elements, @Nullable Root root, int infoIndex, int sentenceIndex, int detailIndex) {
        Element page = elements.first();

        String[] info = page.child(infoIndex).text().split(" ", 2);
//        String[] info = {"for test", "for test"};
        String spell = info[0];
        String explain = info[1];

        Sentence sentence = new Sentence(page.child(sentenceIndex).text());

        String detail = page.child(detailIndex).text();

        Word word = new Word(spell, explain, sentence, detail);
        if (root != null) {
            root.appendWord(word);
        }

        return elements.next();
    }

    private Elements parseUnit(Elements elements, int unitCount) {
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

        elements = parseLastEightWords(elements, unit, unitCount);
        elements = parseQuiz(elements, unit);  // review quiz

        units.add(unit);
        return elements;
    }

    private Elements parseTwoRoots(Elements elements, Unit unit, int firstRootIndex) {
        elements = parseRoot(elements, unit, firstRootIndex);
        elements = parseRoot(elements, unit, 1);
        elements = parseQuiz(elements, unit);
        return elements;
    }

    private Elements parseLastEightWords(Elements elements, Unit unit, int unitCount) {
        if ((unitCount <= 20 && unitCount >= 14) || unitCount == 26) {
            return parseTwoRoots(elements, unit, 2);
        }
        if (unitCount == 27 || unitCount == 28) {
            return parseTwoRoots(elements, unit, 1);
        }
        elements = parseWord(elements, null, 2, 3, 4);
        for (int i = 1; i < WORDS_IN_MYTHOLOGY_AND_HISTORY; i++) {
            elements = parseWord(elements, null, 1, 2, 3);
        }
        return elements;
    }

    private boolean isQuizPage(Element page) {
        String info = page.child(1).text();
        if (info.startsWith("Quiz ") || info.startsWith("Review Quizzes ")) {
            return true;
        }
        for (int i = 0; i < 7; i++) {
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
        int unitCount = 0;
        while (elements.first().child(1).text().startsWith("Unit ")) {
            unitCount += 1;
            System.out.println("parsing unit: " + unitCount);
            elements = parseUnit(elements, unitCount);
        }
        assert unitCount == UNIT_COUNT;

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
            return doc.body().children();
        }
        throw new RuntimeException();
    }

}
