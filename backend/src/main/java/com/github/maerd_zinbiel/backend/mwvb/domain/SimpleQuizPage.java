package com.github.maerd_zinbiel.backend.mwvb.domain;

import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class SimpleQuizPage {
    private String content;
    private String[] answers;
    private Integer id;
    public SimpleQuizPage() {
        content = null;
        answers = null;
        id = null;
    }

    public SimpleQuizPage(String content, String[] answers) {
        this.content = content;
        this.answers = answers;
        this.id = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getContent() {
        return content;
    }

    public String[] getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "SimpleQuizPage{" +
                "content='" + content + '\'' +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }
}
