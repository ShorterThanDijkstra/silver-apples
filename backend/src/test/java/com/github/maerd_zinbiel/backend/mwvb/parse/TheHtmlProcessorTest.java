package com.github.maerd_zinbiel.backend.mwvb.parse;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TheHtmlProcessorTest {

    @Test
    void processIntro() throws IOException {
        TheHtmlProcessor.getInstance().processIntro();
    }

    @Test
    void processUnits() throws IOException {
        TheHtmlProcessor.getInstance().processUnits();
    }

}