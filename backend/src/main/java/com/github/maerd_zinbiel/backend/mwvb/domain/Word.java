package com.github.maerd_zinbiel.backend.mwvb.domain;

public class Word {
    private final String spell;
    private final String explain;
    private final Sentence sentence;
    private final String detail;
    private Root root;

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
