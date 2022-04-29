package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

public class Quiz {
    private Unit unit;
    private final List<QuizPage> quizPages;
    private final List<SimpleQuizPage> simpleQuizPages;

    @JsonIgnore
    private Integer id;

    public Quiz() {
        quizPages = new LinkedList<>();
        simpleQuizPages = new LinkedList<>();
        unit = null;
        id = null;
    }

    public void appendSimpleQuizPage(SimpleQuizPage quizPage) {
        simpleQuizPages.add(quizPage);
    }

    public void appendQuizPage(QuizPage page) {
        quizPages.add(page);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
