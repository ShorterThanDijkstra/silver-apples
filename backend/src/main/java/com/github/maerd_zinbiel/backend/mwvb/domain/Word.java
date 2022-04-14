package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

@Repository
public class Word {
    private String spell;
    private String explain;
    private Sentence sentence;
    private String detail;
    private Root root;

    public Word() {

    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Word(String spell, String explain, Sentence sentence, String detail) {
        this.spell = spell;
        this.explain = explain;
        this.sentence = sentence;
        this.detail = detail;
    }

    public String getSpell() {
        return spell;
    }

    public String getExplain() {
        return explain;
    }

    public Sentence getSentence() {
        return sentence;
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
        return "{" +
                "\"spell\":\"" + spell + '\"' +
                ",\"explain\":\"" + explain + '\"' +
                ",\"sentence\":" + sentence +
                ",\"detail\":\"" + detail + '\"' +
                ",\"root\":" + root +
                '}';
    }
}
