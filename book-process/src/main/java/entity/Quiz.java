package entity;


import java.util.LinkedList;
import java.util.List;

public class Quiz {
    private Unit unit;
    private final List<QuizPage> quizPages;
    private final List<SimpleQuizPage> simpleQuizPages;

    public Quiz() {
        quizPages = new LinkedList<>();
        simpleQuizPages = new LinkedList<>();
        unit = null;
    }

    public void appendSimpleQuizPage(SimpleQuizPage quizPage) {
        simpleQuizPages.add(quizPage);
    }

    public void appendQuizPage(QuizPage page) {
        quizPages.add(page);
    }

    public List<SimpleQuizPage> getSimpleQuizPages() {
        return simpleQuizPages;
    }

    public List<QuizPage> getQuizPages() {
        return quizPages;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
