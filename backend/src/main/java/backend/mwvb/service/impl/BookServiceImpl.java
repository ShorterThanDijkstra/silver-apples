package backend.mwvb.service.impl;

import backend.mwvb.entity.*;
import backend.mwvb.mapper.*;
import backend.mwvb.service.BookService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Data
public class BookServiceImpl implements BookService {
    private final RootMapper rootMapper;
    private final WordMapper wordMapper;
    private final QuizMapper quizMapper;
    private final UnitMapper unitMapper;

    private final TheIntroMapper theIntroMapper;

    @Override
    public List<List<Root>> allRoots() {
        List<List<Root>> allRoots = new LinkedList<>();
        for (int unit = 1; unit <= Unit.UNIT_COUNT; unit++) {
            allRoots.add(rootsInUnit(unit));
        }
        return allRoots;
    }

    @Override
    public List<Root> rootsInUnit(Integer unitIndex) {
        return rootMapper.rootsInUnit(unitIndex);
    }

    @Override
    public List<Word> wordsInRoot(Integer rootId) {
        return wordMapper.wordsInRoot(rootId);
    }

    @Override
    public List<Quiz> quizzesInUnit(Integer unitIndex) {
        return quizMapper.quizzesInUnit(unitIndex);
    }

    @Override
    public List<List<Quiz>> allQuizzes() {
        List<List<Quiz>> allQuizzes = new LinkedList<>();
        for (int unit = 1; unit <= Unit.UNIT_COUNT; unit++) {
            allQuizzes.add(quizzesInUnit(unit));
        }
        return allQuizzes;
    }

    @Override
    public Unit unit(Integer unitIndex) {
        return unitMapper.unit(unitIndex);
    }

    @Override
    public TheIntro intro() {
        return new TheIntro(theIntroMapper.paragraphs());
    }
}
