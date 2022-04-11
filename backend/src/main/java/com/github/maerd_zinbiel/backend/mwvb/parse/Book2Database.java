package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import com.github.maerd_zinbiel.backend.mwvb.domain.Unit;
import com.github.maerd_zinbiel.backend.mwvb.mapper.TheIntroMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Book2Database implements BookWriter{


    @Override
    public void writeIntro(TheIntro intro) throws IOException {

    }

    @Override
    public void writeUnit(Unit unit) throws IOException {

    }

    @Override
    public void writeUnitsDone() throws IOException {

    }
}
