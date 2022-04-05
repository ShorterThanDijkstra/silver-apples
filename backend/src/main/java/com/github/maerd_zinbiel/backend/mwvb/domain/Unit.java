package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.LinkedList;
import java.util.List;

public class Unit {
    private final List<Root> roots = new LinkedList<>(); // size: 8
    private final List<Word> specialSection = new LinkedList<>(); // size: 8
    private final List<Quiz> quizzes = new LinkedList<>(); // size:6

    public void appendRoot(Root root) {
        roots.add(root);
    }

    public void specialSectionAppendWord(Word word) {
        specialSection.add(word);
    }

    public void appendQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public List<Root> getRoots() {
        return roots;
    }

    public List<Word> getSpecialSection() {
        return specialSection;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    @Override
    public String toString() {
        return "{" +
                "\"roots\":" + roots +
                ",\"specialSection\":" + specialSection +
                ",\"quizzes\":" + quizzes +
                '}';
    }
}
