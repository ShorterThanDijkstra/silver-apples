package com.github.maerd_zinbiel.backend.mwvb.domain;

public class Sentence {
    private final String text;
    private  Word word;

    public String getText() {
        return text;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Sentence(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
                "\"text\":\"" + text + '\"' +
                ",\"word\":" + word +
                '}';
    }
}
