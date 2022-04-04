package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Unit {
    private final List<Root> roots = new LinkedList<>(); // size: 8
    private final List<Word> mythologyAndHistory = new LinkedList<>(); // size: 8
    private final List<Quiz> quizzes = new LinkedList<>(); // size:6

    public void appendRoot(Root root) {
//        root.setUnit(this);
//        roots.add(root);
    }

    public void appendMythologyAndHistory(Word word) {
        mythologyAndHistory.add(word);
    }

    public void appendQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public List<Root> getRoots() {
        return roots;
    }

    public List<Word> getMythologyAndHistory() {
        return mythologyAndHistory;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}
