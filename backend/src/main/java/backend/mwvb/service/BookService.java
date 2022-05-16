package backend.mwvb.service;

import backend.mwvb.entity.*;

import java.util.List;

public interface BookService {

    List<List<Root>> allRoots();

    List<Root> rootsInUnit(Integer unitIndex);

    List<Word> wordsInRoot(Integer rootId);

    List<Quiz> quizzesInUnit(Integer unitId);

    List<List<Quiz>> allQuizzes();

    Unit unit(Integer unitIndex);

    TheIntro intro();
}
