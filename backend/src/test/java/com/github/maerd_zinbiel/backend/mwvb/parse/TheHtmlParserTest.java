package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.Unit;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TheHtmlParserTest {

    @Test
    void getInstance() {
        TheHtmlParser parser = TheHtmlParser.getInstance();
        List<Unit> units = parser.getUnits();
    }
}