package mapper;

import entity.Sentence;
import entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SentenceMapper {
    void insertSentences(@Param("sentences") List<Sentence> sentences, @Param("wordId") Integer wordId);

    void insertSentencesOfSpecialWord(@Param("word") Word word);
}
