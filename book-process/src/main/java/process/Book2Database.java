package process;


import entity.*;
import mapper.*;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class Book2Database implements BookWriter {
    private final SqlSession sqlSession;

    public Book2Database(SqlSession session) {
        sqlSession = session;
    }

    @Override
    public void writeIntro(TheIntro intro) throws IOException {
        TheIntroMapper theIntroMapper = sqlSession.getMapper(TheIntroMapper.class);
        theIntroMapper.insert(intro);
    }

    private void insertUnit(Unit unit) {
        UnitMapper unitMapper = sqlSession.getMapper(UnitMapper.class);
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

    @Override
    public void writeUnit(Unit unit) throws IOException {
        insertUnit(unit);
    }

    @Override
    public void writeUnitsDone() throws IOException {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
