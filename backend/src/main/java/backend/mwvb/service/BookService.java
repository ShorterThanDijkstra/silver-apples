package backend.mwvb.service;

import backend.mwvb.entity.*;

import java.util.List;

public interface BookService {
    int UNIT_COUNT = 30;

    List<List<Root>> allRoots();

    List<Root> rootsInUnit(Integer index);

    List<Word> wordsInRoot(Integer rootId);

    List<Quiz> quizzesInUnit(Integer unitId);

    List<List<Quiz>> allQuizzes();

    Unit unit(Integer unitIndex);

    TheIntro intro();
}
