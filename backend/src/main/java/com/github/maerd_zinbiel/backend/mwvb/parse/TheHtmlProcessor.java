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

public class TheHtmlProcessor {
    private static final String WMVB_FILE = "com/github/maerd_zinbiel/backend/wmvb/parse/mwvb.html";
    private static final String WMVB_FILE_INTRO = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-intro.json";
    private static final String WMVB_FILE_UNITS = "src/main/resources/com/github/maerd_zinbiel/backend/wmvb/parse/mwvb-units.json";
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

    private SequenceWriter processUnitsSetup() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter fileWriter = new FileWriter(WMVB_FILE_UNITS, true);
        return mapper.writer().writeValuesAsArray(fileWriter);
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
            unitsPages = processUnit(unitsPages, unit, unitCount);
            firstPage = unitsPages.first();
//            System.out.println(unit);
//            seqWriter.write(unit);
        }

        processAnswers(unitsPages);
        seqWriter.close();

        assert unitCount == UNIT_COUNT;
    }

    private void processAnswers(Elements answerPages) {
        // TODO: 2022/4/5
    }

    private Elements processUnit(Elements unitsPages, Unit unit, int unitCount) {
        for (int i = 0; i < ROOTS_IN_UNIT_COUNT / 2; i++) {
            if (i == 0) {
                unitsPages = processRoot(unitsPages, unit, 3);
            } else {
                unitsPages = processRoot(unitsPages, unit, 1);
            }
            unitsPages = processRoot(unitsPages, unit, 1);
            unitsPages = processQuiz(unitsPages, unit);
        }

        unitsPages = processSpecialSection(unitsPages, unit, unitCount);
        unitsPages = processQuiz(unitsPages, unit);
        return unitsPages;
    }

    private Elements processSpecialSection(Elements unitsPages, Unit unit, int unitCount) {
        // TODO: 2022/4/5 processTwoRoots要改
        if ((unitCount <= 20 && unitCount >= 14) || unitCount == 26) {
            return processTwoRoots(unitsPages, unit, 2);
        }
        if (unitCount == 27 || unitCount == 28) {
            return processTwoRoots(unitsPages, unit, 1);
        }
        unitsPages = processSpecialSectionWord(unitsPages, unit, 2, 3, 4);
        for (int i = 1; i < WORDS_LAST; i++) {
            unitsPages = processSpecialSectionWord(unitsPages, unit, 1, 2, 3);
        }
        return unitsPages;
    }

    private Word processWord(Elements unitsPages, int infoIndex, int sentenceIndex, int detailIndex) {
        Element wordPage = unitsPages.first();

        assert wordPage != null;
        String[] info = wordPage.child(infoIndex).text().split(" ", 2);
        String spell = info[0];
        String explain = info[1];

        Sentence sentence = new Sentence(wordPage.child(sentenceIndex).text());

        String detail = wordPage.child(detailIndex).text();

        return new Word(spell, explain, sentence, detail);
    }

    private Elements processWordInRoot(Elements unitsPages, Root root) {

        Word word = processWord(unitsPages, 1, 2, 3);
        root.appendWord(word);
        return unitsPages.next();
    }

    private Elements processSpecialSectionWord(Elements unitsPages, Unit unit, int infoIndex, int sentenceIndex, int detailIndex) {
        Word word = processWord(unitsPages, infoIndex, sentenceIndex, detailIndex);
        unit.specialSectionAppendWord(word);
        return unitsPages.next();
    }

    private Elements processTwoRoots(Elements unitsPages, Unit unit, int firstRootIndex) {
        unitsPages = processRoot(unitsPages, unit, firstRootIndex);
        unitsPages = processRoot(unitsPages, unit, 1);
        unitsPages = processQuiz(unitsPages, unit);
        return unitsPages;
    }

    private Elements processQuiz(Elements unitsPages, Unit unit) {
        // TODO: 2022/4/5  
        Element page = unitsPages.first();
        while (isQuizPage(page)) {
            unitsPages = unitsPages.next();
            page = unitsPages.first();
        }
        return unitsPages;
    }

    private boolean isQuizPage(Element page) {
        if (page == null) {
            return false;
        }
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

    private Elements processRoot(Elements unitsPages, Unit unit, int descIndex) {
        Element rootPage = unitsPages.first();

        assert rootPage != null;
        String rootDesc = rootPage.child(descIndex).text();
        String rootName = rootDesc.split(" ")[0];

        Root root = new Root(rootName, rootDesc);
        unit.appendRoot(root);

        unitsPages = unitsPages.next();
        for (int i = 0; i < WORDS_IN_ROOT_COUNT; i++) {
            unitsPages = processWordInRoot(unitsPages, root);
        }
        return unitsPages;
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
