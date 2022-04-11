package com.github.maerd_zinbiel.backend.mwvb.parse;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class TheBookProcessorTest {

    @Test
    void book2Json() throws IOException {
        TheBookProcessor processor = TheBookProcessor.getInstance(new Book2Json());
        processor.processIntro();
        processor.processUnits();
    }

}