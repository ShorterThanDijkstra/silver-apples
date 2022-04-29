package entity;

public class QuizItem {
    private QuizPage quizPage;
    private final String description;
    private final String answer;

    public QuizItem(final String description, final String  answer) {
        this.description = description;
        this.answer = answer;
        this.quizPage = null;
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

    public QuizPage getQuizPage() {
        return quizPage;
    }

}
