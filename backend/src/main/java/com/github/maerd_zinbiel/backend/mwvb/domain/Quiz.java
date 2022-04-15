package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class Quiz {
    private Unit unit;
    private List<QuizPage> quizPages;
    private List<SimpleQuizPage> simpleQuizPages;
    private Integer id;

    public Quiz() {
        quizPages = new LinkedList<>();
        simpleQuizPages = new LinkedList<>();
        id = null;
        unit = null;
    }

    public void appendSimpleQuizPage(SimpleQuizPage quizPage) {
        simpleQuizPages.add(quizPage);
    }

    public List<SimpleQuizPage> getSimpleQuizPages() {
        return simpleQuizPages;
    }

    public void setSimpleQuizPages(List<SimpleQuizPage> simpleQuizPages) {
        this.simpleQuizPages = simpleQuizPages;
    }

    public void appendQuizPage(QuizPage page) {
        quizPages.add(page);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<QuizPage> getQuizPages() {
        return quizPages;
    }

    public void setQuizPages(List<QuizPage> quizPages) {
        this.quizPages = quizPages;
    }
}
