package com.github.maerd_zinbiel.backend.mwvb.domain;

import java.util.Arrays;

public class SimpleQuizPage {
    private final String content;
    private final String[] answers;

    public SimpleQuizPage(String content, String[] answers) {
        this.content = content;
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
