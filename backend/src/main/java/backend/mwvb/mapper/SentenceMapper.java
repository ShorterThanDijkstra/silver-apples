package backend.mwvb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SentenceMapper {
    @Select("SELECT text FROM sentence WHERE word_id=#{wordId}")
    List<String> sentencesOfWord(@Param("wordId") Integer wordId);
}
