package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.LinkedList;
import java.util.List;

public class QuizPage {
    private Quiz quiz;
    private final String description;
    private List<QuizItem> quizItems;

    public QuizPage(String description) {
        this.description = description;
        this.quizItems = new LinkedList<>();
    }

    public void appendQuizItem(QuizItem item) {
        quizItems.add(item);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setQuizItems(List<QuizItem> quizItems) {
        this.quizItems = quizItems;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getDescription() {
        return description;
    }

    public List<QuizItem> getQuizItems() {
        return quizItems;
    }
}
