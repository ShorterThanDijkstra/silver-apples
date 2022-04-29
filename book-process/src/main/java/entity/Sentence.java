package entity;


public class Sentence {
    private final String text;
    private Word word;

    public Sentence(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

}
