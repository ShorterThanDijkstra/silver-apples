package com.github.maerd_zinbiel.backend.mwvb.parse;

import com.github.maerd_zinbiel.backend.mwvb.domain.*;
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
        insertQuiz(unit);
        insertSpecialSection(unit);
    }

    private void insertSpecialSection(Unit unit) {
        WordMapper wordMapper = sqlSession.getMapper(WordMapper.class);
        SentenceMapper sentenceMapper = sqlSession.getMapper(SentenceMapper.class);
        Integer unitId = unit.getId();
        unit.getSpecialSection().forEach(
                word -> {
                    wordMapper.insertWordInSpecialSection(word, unitId);
                    sentenceMapper.insertSentencesOfSpecialWord(word);
                }
        );
    }


    private void insertQuiz(Unit unit) {
        QuizMapper quizMapper = sqlSession.getMapper(QuizMapper.class);
        Integer unitId = unit.getId();
        unit.getQuizzes().forEach(
                quiz -> {
                    quizMapper.insert(quiz, unitId);
                    insertQuizPage(quiz);
                }
        );
    }

    private void insertQuizPage(Quiz quiz) {
        SimpleQuizPageMapper pageMapper = sqlSession.getMapper(SimpleQuizPageMapper.class);
        Integer quizId = quiz.getId();
        quiz.getSimpleQuizPages().forEach(
                page -> {
                    pageMapper.insertContent(page, quizId);
                    pageMapper.insertAnswers(page);
                }
        );
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
