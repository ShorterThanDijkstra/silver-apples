package backend.mwvb.service;

import backend.mwvb.entity.Quiz;
import backend.mwvb.entity.Root;
import backend.mwvb.entity.Unit;
import backend.mwvb.entity.Word;

import java.util.List;

public interface BookService {
    int UNIT_COUNT = 30;

    List<List<Root>> allRoots();

    List<Root> rootsInUnit(Integer index);

    List<Word> wordsInRoot(Integer rootId);

    List<Quiz> quizzesInUnit(Integer unitId);

    List<List<Quiz>> allQuizzes();

    Unit unit(Integer unitIndex);
}
