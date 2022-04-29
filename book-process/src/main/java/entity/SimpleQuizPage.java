package entity;


public class SimpleQuizPage {
    private final String content;
    private final String[] answers;

    private Integer id;
    public SimpleQuizPage(String content, String[] answers) {
        this.content = content;
        this.answers = answers;
        this.id = null;
    }

    public String content() {
        return content;
    }

    public String[] answers() {
        return answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
