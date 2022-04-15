package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Word {
    private String spell;
    private String explain;
    private String detail;
    private Root root;
    private Integer id;
    private List<Sentence> sentences;

    public Word() {
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Word(String spell, String explain, String detail, List<Sentence> sentences) {
        this.spell = spell;
        this.explain = explain;
        this.detail = detail;
        this.sentences = sentences;
        this.id = null;
        this.root = null;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "Word{" +
                "spell='" + spell + '\'' +
                ", explain='" + explain + '\'' +
                ", detail='" + detail + '\'' +
                ", root=" + root +
                ", id=" + id +
                ", sentences=" + sentences +
                '}';
    }
}
