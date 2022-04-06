package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.Root;
import com.github.maerd_zinbiel.backend.mwvb.domain.Unit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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