package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository

public class Root {
    private Unit unit;
    private String name;
    private String description;
    private List<Word> words; // size: 4
    private Integer id;
    public Root() {
        unit = null;
        name = null;
        description = null;
        words = null;
        id = null;
    }

    public Root(String name, String description) {
        this.name = name;
        this.description = description;
        this.words = new LinkedList<>();
        this.id = null;
        this.unit = null;
    }

    public void appendWord(Word word) {
        words.add(word);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWords(List<Word> words) {
        this.words = words;
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

    @Override
    public String toString() {
        return "{" +
                "\"unit\":" + unit +
                ",\"name\":\"" + name + '\"' +
                ",\"description\":\"" + description + "\"" +
                ",\"words\":" + words +
                '}';
    }
}
