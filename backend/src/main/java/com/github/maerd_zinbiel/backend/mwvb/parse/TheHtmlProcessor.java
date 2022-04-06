package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.github.maerd_zinbiel.backend.mwvb.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TheHtmlProcessor {
    private static final String WMVB_FILE = "com/github/maerd_zinbiel/backend/wmvb/parse/mwvb.html";
    private static final String WMVB_FILE_INTRO = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-intro.json";
    private static final String WMVB_FILE_UNITS = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-units.json";
    private static final String WMVB_FILE_QUIZZES = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-quizzes.json";

    private static final int UNIT_COUNT = 30;
    private static final int ROOTS_IN_UNIT_COUNT = 8;
    private static final int WORDS_IN_ROOT_COUNT = 4;
    private static final int WORDS_IN_SPECIAL_SECTION = 8;
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

    private SequenceWriter processUnitsSetup() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter fileWriter = new FileWriter(WMVB_FILE_UNITS, false);
        return mapper.writer().writeValuesAsArray(fileWriter);
    }

    private boolean validateUnit(Unit unit, String[] rootsNames) {
        List<Root> roots = unit.getRoots();
        if (roots.size() != ROOTS_IN_UNIT_COUNT) {
            return false;
        }
        for (int i = 0; i < roots.size(); i++) {
            Root root = roots.get(i);
            if (root.getWords().size() != WORDS_IN_ROOT_COUNT) {
                return false;
            }
            if (!root.getName().equals(rootsNames[i])) {
                System.out.println(root.getName());
                System.out.println(rootsNames[i]);
                return false;
            }
        }
        return unit.getSpecialSection().size() == WORDS_IN_SPECIAL_SECTION;
    }

    public void processUnits() throws IOException {
        SequenceWriter seqWriter = processUnitsSetup();

        Elements unitsPages = pages.next();

        int unitCount = 0;

        Element firstPage = unitsPages.first();

        while (firstPage != null && firstPage.child(1).text().startsWith("Unit ")) {
            Unit unit = new Unit();
            unitCount += 1;

            System.out.println("parsing unit: " + unitCount);

            String[] rootsNames = firstPage.child(2).text().split(" ", ROOTS_IN_UNIT_COUNT + 1);

            unitsPages = processUnit(unitsPages, unit, unitCount);

            firstPage = unitsPages.first();

            assert validateUnit(unit, rootsNames);

            seqWriter.write(unit);
        }
        processAnswers(unitsPages);
        System.out.println(unitsPages.first());
        seqWriter.close();

        assert unitCount == UNIT_COUNT;
    }

    private Elements processUnit(Elements unitsPages, Unit unit, int unitCount) {
        for (int i = 0; i < ROOTS_IN_UNIT_COUNT / 2; i++) {
            if (i == 0) {
                unitsPages = processRootInUnit(unitsPages, unit, 3);
            } else {
                unitsPages = processRootInUnit(unitsPages, unit, 1);
            }
            unitsPages = processRootInUnit(unitsPages, unit, 1);
            unitsPages = processQuiz(unitsPages, unit, false);
        }

        unitsPages = processSpecialSection(unitsPages, unit, unitCount);
        unitsPages = processQuiz(unitsPages, unit, false);
        unitsPages = processQuiz(unitsPages, unit, true); // review quizzes
        return unitsPages;
    }

    private Elements processSpecialSection(Elements unitsPages, Unit unit, int unitCount) {
        if (unitCount == 14
                || unitCount == 15
                || unitCount == 17
                || unitCount == 18
                || unitCount == 19
                || unitCount == 20
                || unitCount == 26) {
            return processRootsInSpecialSection(unitsPages, unit, 2);
        }
        if (unitCount == 27 || unitCount == 28) {
            return processRootsInSpecialSection(unitsPages, unit, 1);
        }
        if (unitCount == 16) {
            unitsPages = unitsPages.next();
            for (int i = 0; i < WORDS_IN_SPECIAL_SECTION; i++) {
                unitsPages = processWordInSpecialSection(unitsPages, unit, 1, 2, 3);
            }
            return unitsPages;
        }
        unitsPages = processWordInSpecialSection(unitsPages, unit, 2, 3, 4);
        for (int i = 1; i < WORDS_IN_SPECIAL_SECTION; i++) {
            unitsPages = processWordInSpecialSection(unitsPages, unit, 1, 2, 3);
        }
        return unitsPages;
    }

    private Word extractWord(Element wordPage, int infoIndex, int sentenceIndex, int detailIndex) {
        assert wordPage != null;
        String[] info = wordPage.child(infoIndex).text().split(" ", 2);
        String spell = info[0];
        String explain = info[1];

        Sentence sentence = new Sentence(wordPage.child(sentenceIndex).text().substring(2));

        String detail = wordPage.child(detailIndex).text();

        return new Word(spell, explain, sentence, detail);
    }

    private Elements processWordInRoot(Elements unitsPages, Root root) {
        Element wordPage = unitsPages.first();
        assert wordPage != null;
        Word word = extractWord(wordPage, 1, 2, 3);
        root.appendWord(word);
        return unitsPages.next();
    }

    private Elements processWordInSpecialSection(Elements unitsPages, Unit unit, int infoIndex, int sentenceIndex, int detailIndex) {
        Element wordPage = unitsPages.first();
        assert wordPage != null;
        Word word = extractWord(wordPage, infoIndex, sentenceIndex, detailIndex);
        unit.specialSectionAppendWord(word);
        return unitsPages.next();
    }

    private Elements processQuiz(Elements unitsPages, Unit unit, boolean isReviewQuizzes) {
        // TODO: 2022/4/5  
        Element page = unitsPages.first();
        assert page != null;
        assert isFirstQuizPage(page, isReviewQuizzes);

        unitsPages = unitsPages.next();
        page = unitsPages.first();
        while (isRestQuizPage(page)) {
            unitsPages = unitsPages.next();
            page = unitsPages.first();
        }
        return unitsPages;
    }

    private boolean isFirstQuizPage(Element page, boolean isReviewQuizzes) {
        String info = page.child(1).text();
        if (isReviewQuizzes) {
            return info.startsWith("Review Quizzes ");
        }
        return info.startsWith("Quiz ");
    }

    private boolean isRestQuizPage(Element page) {
        if (page == null) {
            return false;
        }
        String info = page.child(1).text();
        for (int i = 0; i < 7; i++) {
            String start = (char) ('A' + i) + ". ";
            if (info.startsWith(start)) {
                return true;
            }
        }
        return false;
    }

    private Elements processRootsInSpecialSection(Elements unitsPages, Unit unit, int rootDescIndex) {
        unitsPages = processRootInSpecialSection(unitsPages, unit, rootDescIndex);
        unitsPages = processRootInSpecialSection(unitsPages, unit, 1);
        return unitsPages;
    }

    private Elements processRootInSpecialSection(Elements unitsPages, Unit unit, int descIndex) {
        Element rootPage = unitsPages.first();
        assert rootPage != null;
        Root root = extractRoot(rootPage, descIndex);

        unitsPages = unitsPages.next();
        for (int i = 0; i < WORDS_IN_ROOT_COUNT; i++) {
            Element wordPage = unitsPages.first();
            assert wordPage != null;
            Word word = extractWord(wordPage, 1, 2, 3);
            word.setRoot(root);
            unit.specialSectionAppendWord(word);
            unitsPages = unitsPages.next();

        }
        return unitsPages;
    }

    private Root extractRoot(Element rootPage, int descIndex) {
        String rootDesc = rootPage.child(descIndex).text();
        String rootName = rootDesc.split(" ")[0];
        return new Root(rootName, rootDesc);
    }

    private Elements processRootInUnit(Elements unitsPages, Unit unit, int descIndex) {
        Element rootPage = unitsPages.first();

        assert rootPage != null;
        Root root = extractRoot(rootPage, descIndex);
        unit.appendRoot(root);

        unitsPages = unitsPages.next();
        for (int i = 0; i < WORDS_IN_ROOT_COUNT; i++) {
            unitsPages = processWordInRoot(unitsPages, root);
        }

        return unitsPages;
    }

    private void processAnswers(Elements answerPages) {
        // TODO: 2022/4/5
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
