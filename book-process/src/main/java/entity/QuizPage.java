package entity;

import java.util.LinkedList;
import java.util.List;

public class QuizPage {
    private Quiz quiz;
    private final String description;
    private final List<QuizItem> quizItems;

    public QuizPage(final String description) {
        this.description = description;
        this.quizItems = new LinkedList<>();
        this.quiz = null;
    }

    public void appendQuizItem(QuizItem item) {
        quizItems.add(item);
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getDescription() {
        return description;
    }

    public List<QuizItem> getQuizItems() {
        return quizItems;
    }
}
