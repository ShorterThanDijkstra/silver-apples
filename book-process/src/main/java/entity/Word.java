package entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Word {
    private final String spell;
    private final String explain;
    private final String detail;
    private final List<Sentence> sentences;
    private  Root root;
    @JsonIgnore
    private Integer id;

    public Word(final String spell, final String explain, final String detail, final List<Sentence> sentences) {
        this.spell = spell;
        this.explain = explain;
        this.detail = detail;
        this.sentences = sentences;
        this.root = null;
        this.id = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public String getSpell() {
        return spell;
    }

    public String getExplain() {
        return explain;
    }

    public String getDetail() {
        return detail;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public Root getRoot() {
        return root;
    }
}
