package com.github.maerd_zinbiel.backend.mwvb.parse;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TheHtmlProcessorTest {

    @Test
    void processIntro() throws IOException {
        TheHtmlProcessor.getInstance().processIntro();
    }

    @Test
    void processUnits() throws IOException {
        TheHtmlProcessor.getInstance().processUnits();
    }

    @Test
    void getAnswerPages() {
        Elements answerPages = TheHtmlProcessor.getInstance().getAnswerPages();
        System.out.println(answerPages.first());
    }

}