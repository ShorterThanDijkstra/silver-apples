package com.github.maerd_zinbiel.backend.mwvb.parse;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TheHtmlProcessorTest {

    @Test
    void getInstance() throws IOException {
        TheHtmlProcessor.getInstance().processIntro();
    }
}