package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class Unit {
    // TODO: 2022/4/6 List -> Array
    private final List<Root> roots; // size: 8
    private final List<Word> specialSection; // size: 8
    private final List<Quiz> quizzes; // size:6
    private Integer index;
    private Integer id;

    public Unit() {
        roots = new LinkedList<>();
        specialSection = new LinkedList<>();
        quizzes = new LinkedList<>();
        id = null;
        index = null;
    }

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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
