package process;

import entity.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TheBookProcessor {
    public static final String WMVB_FILE = "src/main/resources/book/mwvb.html";
    public static final String WMVB_FILE_INTRO = "src/main/resources/book/mwvb-intro.json";
    public static final String WMVB_FILE_UNITS = "src/main/resources/book/mwvb-units.json";

    private static final int UNIT_COUNT = 30;
    private static final int ROOTS_IN_UNIT_COUNT = 8;
    private static final int WORDS_IN_ROOT_COUNT = 4;
    private static final int WORDS_IN_SPECIAL_SECTION = 8;
    private final Elements pages;
    private Elements answerPages;
    private static TheBookProcessor instance;
    private final BookWriter writer;

    private TheBookProcessor(BookWriter writer) {
        this.pages = pages();
        this.writer = writer;
    }

    public static TheBookProcessor getInstance(BookWriter writer) {
        if (instance == null) {
            instance = new TheBookProcessor(writer);
        }
        return instance;
    }

    private Elements pages() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new File(WMVB_FILE), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc != null) {
            return doc.body().children();
        }
        throw new RuntimeException();
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
                return false;
            }
        }
        return unit.getSpecialSection().size() == WORDS_IN_SPECIAL_SECTION;
    }

    public void processUnits() throws IOException {
        Elements unitsPages = pages.next();
        this.answerPages = getAnswerPages();

        int unitCount = 0;

        Element firstPage = unitsPages.first();

        while (firstPage != null && firstPage.child(1).text().startsWith("Unit ")) {
            unitCount += 1;

            Unit unit = new Unit(unitCount);

            System.out.println("parsing unit: " + unitCount);

            String[] rootsNames = firstPage.child(2).text().split(" ", ROOTS_IN_UNIT_COUNT + 1);

            unitsPages = processUnit(unitsPages, unit, unitCount);

            firstPage = unitsPages.first();

            assert validateUnit(unit, rootsNames);

            this.writer.writeUnit(unit);
        }
        this.writer.writeUnitsDone();

        Element answerPageStart = unitsPages.first();
        assert answerPageStart != null;
        assert answerPageStart.children().size() == 3;
        assert answerPageStart.child(1).text().equals("Answers");
        assert unitCount == UNIT_COUNT;
        assert answerPages.size() == 0;
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
        if (unitCount == 14 || unitCount == 15 || unitCount == 17 || unitCount == 18 || unitCount == 19 || unitCount == 20 || unitCount == 26) {
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
        String info = wordPage.child(infoIndex).text();
        int end = 1;
        while (end < info.length()) {
            char c = info.charAt(end);
            if (c == '(') {
                assert info.charAt(end + 1) == '1';
                assert info.charAt(end + 2) == ')';
                break;
            }
            if (Character.isUpperCase(c)) {
                break;
            }
            end++;
        }
        String spell = info.substring(0, end).trim();
        String explain = info.substring(end);

        Sentence sentence = new Sentence(wordPage.child(sentenceIndex).text().substring(2));
        List<Sentence> sentences = new ArrayList<>();
        sentences.add(sentence);

        String detail = wordPage.child(detailIndex).text();

        return new Word(spell, explain, detail, sentences);
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

    private boolean validateAnswers(String[] answers, String[] rawAnswers) {
        String last = rawAnswers[rawAnswers.length - 1];
        int expectLen = Integer.parseInt(last.split("\\.")[0]);
        return answers.length == expectLen;
    }

    private String[] extractAnswers(String text) {
        String[] rawAnswers = text.split(" ");

        int offset;
        if (text.startsWith("1.")) {
            offset = 0;
        } else {
            offset = 1;
        }

        String[] answers = new String[rawAnswers.length - offset];

        for (int i = offset; i < rawAnswers.length; i++) {
            String rawAnswer = rawAnswers[i];
            String answer = rawAnswer.split("\\.")[1];

            answers[i - offset] = answer;
        }
        assert validateAnswers(answers, rawAnswers);
        return answers;
    }

    private Deque<String[]> processAnswerPage() {

        Element page = answerPages.first();
        Deque<String[]> result = new LinkedList<>();
        assert page != null;
        for (int i = 0; i < page.children().size(); i++) {
            Element answerBlock = page.child(i);
            if (answerBlock.hasText()) {
                String text = answerBlock.text();
                if (text.startsWith("Unit ") || text.startsWith("Quiz ") || text.startsWith("Review Quizzes ")) {
                    continue;
                }
                result.addLast(extractAnswers(text));
            }
        }

        answerPages = answerPages.next();
        return result;
    }

    private void extractSimpleQuizPage(Element page, String[] answers, Quiz quiz) {
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 0; i < page.children().size(); i++) {
            Element child = page.child(i);
            if (child.hasText()) {
                String text = child.text();
                if (text.startsWith("Answers") || text.startsWith("Quiz ") || text.startsWith("Review Quizzes ") || text.startsWith("Unit ")) {
                    continue;
                }
                contentBuilder.append(text);
                contentBuilder.append(" ");
            }
        }

        SimpleQuizPage quizPage = new SimpleQuizPage(contentBuilder.toString(), answers);

        assert validateSimpleQuizPage(quizPage);

        quiz.appendSimpleQuizPage(quizPage);
    }

    private boolean validateSimpleQuizPage(SimpleQuizPage quizPage) {
        String content = quizPage.content();
        String[] answers = quizPage.answers();

        Matcher matcher = Pattern.compile("\\d+\\. ").matcher(content);
        String lastNum = "";
        while (matcher.find()) {
            lastNum = matcher.group();
        }
        lastNum = lastNum.substring(0, lastNum.length() - 2);
        assert !lastNum.equals("");

        return Integer.parseInt(lastNum) == answers.length;
    }

    private void showQuizAnswers(Deque<String[]> quizAnswers) {
        quizAnswers.forEach(answer -> {
            Arrays.stream(answer).forEach(s -> System.out.print(s + " "));
            System.out.println();
        });
        System.out.println();
    }

    private Elements processQuiz(Elements unitsPages, Unit unit, boolean isReviewQuizzes) {
        // TODO: 2022/4/5
        Deque<String[]> quizAnswers = processAnswerPage();

        Quiz quiz = new Quiz();

        Element page = unitsPages.first();
        assert page != null;
        assert isFirstQuizPage(page, isReviewQuizzes);
        extractSimpleQuizPage(page, quizAnswers.removeFirst(), quiz);

        unitsPages = unitsPages.next();
        page = unitsPages.first();
        while (isRestQuizPage(page)) {
            extractSimpleQuizPage(page, quizAnswers.removeFirst(), quiz);
            unitsPages = unitsPages.next();
            page = unitsPages.first();
        }

        unit.appendQuiz(quiz);
        assert quizAnswers.isEmpty();
        return unitsPages;
    }

    private Elements getAnswerPages() {
        Elements answerPages = this.pages;
        Element first = answerPages.first();
        while (first != null && !"answers".equals(first.attr("id"))) {
            answerPages = answerPages.next();
            first = answerPages.first();
        }
        return answerPages.next();
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

    public void processIntro() throws IOException {

        TheIntro intro = new TheIntro();

        Element introHtml = pages.first();
        assert introHtml != null;
        Elements paragraphs = introHtml.children().next().next();
        paragraphs.forEach(paragraph -> {
            if (paragraph.hasText()) {
                intro.appendParagraph(paragraph.text());
            }
        });

        assert intro.getParagraphs().size() == 14;

        writer.writeIntro(intro);
    }
}
