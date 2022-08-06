package backend.mwvb.mapper;

import backend.mwvb.entity.Word;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
public interface WordMapper {
    @Select("SELECT id, spell, explain, detail FROM word WHERE root_id=#{rootId}")
    @Results(id = "wordMap",
            value = {
                    @Result(property = "sentences",
                            column = "id",
                            many = @Many(select = "backend.mwvb.mapper.SentenceMapper.sentencesOfWord"))
            })
    List<Word> wordsInRoot(@Param("rootId") Integer rootId);

    @Select("SELECT id, spell, explain, detail FROM word WHERE id=#{wordId}")
    @ResultMap({"wordMap"})
    Word queryWordById(@Param("wordId") Integer wordId);

    // TODO: 2022/7/22 test
    @Select("SELECT EXISTS(SELECT id FROM word WHERE id=#{wordId})")
    boolean idExist(@Param("wordId") Integer wordId);

    @Select("SELECT id, spell, explain, detail FROM word WHERE spell=#{spell}")
    @ResultMap({"wordMap"})
    Word queryWordBySpell(@Param("spell") String spell);
}
