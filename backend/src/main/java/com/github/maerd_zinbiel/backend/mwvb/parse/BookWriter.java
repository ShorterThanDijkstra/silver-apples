package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import com.github.maerd_zinbiel.backend.mwvb.domain.Unit;

import java.io.IOException;

public interface BookWriter {
    void writeIntro(TheIntro intro) throws IOException;
    void writeUnit(Unit unit) throws IOException;
    void writeUnitsDone() throws IOException;
}
