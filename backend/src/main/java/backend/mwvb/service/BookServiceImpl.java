package backend.mwvb.service;

import backend.mwvb.entity.Quiz;
import backend.mwvb.entity.Root;
import backend.mwvb.entity.Word;
import backend.mwvb.mapper.QuizMapper;
import backend.mwvb.mapper.RootMapper;
import backend.mwvb.mapper.WordMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Data
public class BookServiceImpl implements BookService {
    private final RootMapper rootMapper;
    private final WordMapper wordMapper;
    private final QuizMapper quizMapper;

    @Override
    public List<List<Root>> allRoots() {
        List<List<Root>> allRoots = new LinkedList<>();
        for (int unit = 1; unit <= UNIT_COUNT; unit++) {
            allRoots.add(rootsInUnit(unit));
        }
        return allRoots;
    }

    @Override
    public List<Root> rootsInUnit(Integer index) {
        return rootMapper.rootsInUnit(index);
    }

    @Override
    public List<Word> wordsInRoot(Integer rootId) {
        return wordMapper.wordsInRoot(rootId);
    }

    @Override
    public List<Quiz> quizzesInUnit(Integer unitId) {
        return quizMapper.quizzesInUnit(unitId);
    }
}
