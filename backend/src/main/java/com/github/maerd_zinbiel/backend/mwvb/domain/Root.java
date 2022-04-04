package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Root {
    private Unit unit;
    private final String name;
    private final String description;
    private final List<Word> words; // size: 4

    public Root(String name, String description) {
        this.name = name;
        this.description = description;
        this.words = new LinkedList<>();
    }

    public void appendWord(Word word) {
//        word.setRoot(this);
//        words.add(word);
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Word> getWords() {
        return words;
    }

}
