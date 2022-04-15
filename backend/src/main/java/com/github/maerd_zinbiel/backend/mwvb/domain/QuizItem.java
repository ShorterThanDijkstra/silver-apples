package com.github.maerd_zinbiel.backend.mwvb.domain;

public class QuizItem {
    private QuizPage quizPage;
    private final String description;
    private final String answer;

    public QuizItem(String description, String  answer) {
        this.description = description;
        this.answer = answer;
        this.quizPage = null;
    }

    public QuizPage getQuizPage() {
        return quizPage;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuizPage(QuizPage quizPage) {
        this.quizPage = quizPage;
    }
}
