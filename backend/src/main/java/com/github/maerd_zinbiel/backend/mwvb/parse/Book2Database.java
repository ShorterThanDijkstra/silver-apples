package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.WebApplication;
import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import com.github.maerd_zinbiel.backend.mwvb.domain.Unit;
import com.github.maerd_zinbiel.backend.mwvb.mapper.TheIntroMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
public class Book2Database implements BookWriter {

    private TheIntroMapper theIntroMapper;

    public void setTheIntroMapper(TheIntroMapper theIntroMapper) {
        this.theIntroMapper = theIntroMapper;
    }

    public TheIntroMapper getTheIntroMapper() {
        return theIntroMapper;
    }

    @Override
    public void writeIntro(TheIntro intro) throws IOException {
        theIntroMapper.insert(intro);
    }

    @Override
    public void writeUnit(Unit unit) throws IOException {

    }

    @Override
    public void writeUnitsDone() throws IOException {

    }
}
