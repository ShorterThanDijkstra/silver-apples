package backend.mwvb.mapper;

import backend.mwvb.entity.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WordMapper {
    @Select("SELECT id, spell, explain, detail FROM word WHERE root_id=#{rootId}")
    @Results({
            @Result(property = "sentences",
                    column = "id",
                    many = @Many(select = "backend.mwvb.mapper.SentenceMapper.sentencesOfWord") )
    })
    List<Word> wordsInRoot(@Param("rootId") Integer rootId);
}
