package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.Root;
import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import com.github.maerd_zinbiel.backend.mwvb.domain.Unit;
import com.github.maerd_zinbiel.backend.mwvb.domain.Word;
import com.github.maerd_zinbiel.backend.mwvb.mapper.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public class Book2Database implements BookWriter {
    private int unitIndex = 0;
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    @Transactional
    @Override
    public void writeIntro(TheIntro intro) throws IOException {
        TheIntroMapper theIntroMapper = sqlSession.getMapper(TheIntroMapper.class);
        theIntroMapper.insert(intro);
    }

    private void insertUnit(Unit unit) {
        UnitMapper unitMapper = sqlSession.getMapper(UnitMapper.class);
        unit.setIndex(unitIndex);
        unitMapper.insert(unit);
        insertRoots(unit);
    }

    private void insertSentence(Word word) {
        SentenceMapper sentenceMapper = sqlSession.getMapper(SentenceMapper.class);
        Integer wordId = word.getId();
        sentenceMapper.insertSentences(word.getSentences(), wordId);
    }

    private void insertWords(Root root) {
        WordMapper wordMapper = sqlSession.getMapper(WordMapper.class);
        int rootId = root.getId();
        root.getWords().forEach(
                word -> {
                    wordMapper.insert(word, rootId);
                    insertSentence(word);
                }
        );
    }

    private void insertRoots(Unit unit) {
        int unitId = unit.getId();
        RootMapper rootMapper = sqlSession.getMapper(RootMapper.class);
        unit.getRoots().forEach(
                root -> {
                    rootMapper.insert(root, unitId);
                    insertWords(root);
                }
        );
    }

    @Transactional
    @Override
    public void writeUnit(Unit unit) throws IOException {
        unitIndex++;
        insertUnit(unit);
        System.exit(0);
    }

    @Override
    public void writeUnitsDone() throws IOException {

    }
}
