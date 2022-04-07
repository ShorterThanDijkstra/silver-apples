package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.LinkedList;
import java.util.List;

public class Quiz {
    private Unit unit;
    private List<QuizPage> quizPages;
    private List<SimpleQuizPage> simpleQuizPages;

    public Quiz() {
        quizPages = new LinkedList<>();
        simpleQuizPages = new LinkedList<>();
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
