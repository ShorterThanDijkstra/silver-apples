package backend.mwvb.service;

import backend.mwvb.entity.Quiz;
import backend.mwvb.entity.Root;
import backend.mwvb.entity.Word;

import java.util.List;

public interface BookService {
    List<Root> rootsInUnit(Integer index);
    List<Word> wordsInRoot(Integer rootId);

    List<Quiz> quizzesInUnit(Integer unitId);
}
