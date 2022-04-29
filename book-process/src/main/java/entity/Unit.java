package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

public class Unit {
    // TODO: 2022/4/6 List -> Array
    private final List<Root> roots; // size: 8
    private final List<Word> specialSection; // size: 8
    private final List<Quiz> quizzes; // size:6
    @JsonIgnore
    private final int index;

    public Unit(int index) {
        roots = new LinkedList<>();
        specialSection = new LinkedList<>();
        quizzes = new LinkedList<>();
        this.index = index;
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

    public int getIndex() {
        return index;
    }
}
