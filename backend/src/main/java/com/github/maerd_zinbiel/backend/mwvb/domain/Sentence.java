package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

@Repository
public class Sentence {
    private String text;
    private Word word;

    public String getText() {
        return text;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Sentence() {

    }

    public Sentence(String text) {
        this.text = text;
    }

    public void setText(String text) {
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
