package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.List;

public class Quiz {
    private Unit unit;

    private List<QuizItem> quizA;
    private List<QuizItem> quizB;
    private String quizDescA;
    private String quizDescB;

    public Unit getUnit() {
        return unit;
    }

    public List<QuizItem> getQuizA() {
        return quizA;
    }

    public List<QuizItem> getQuizB() {
        return quizB;
    }

    public String getQuizDescA() {
        return quizDescA;
    }

    public String getQuizDescB() {
        return quizDescB;
    }
}
