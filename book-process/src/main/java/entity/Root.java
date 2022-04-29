package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

public class Root {
    private Unit unit;
    private final String name;
    private final String description;
    private final List<Word> words; // size: 4
    @JsonIgnore
    private Integer id;
    public Root(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.words = new LinkedList<>();
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
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
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
