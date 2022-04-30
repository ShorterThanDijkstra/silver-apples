package backend.mwvb.mapper;

import backend.mwvb.entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface WordMapper {
    @Select("SELECT id, spell, explain, detail FROM word WHERE root_id=#{rootId}")
    List<Word> wordsInRoot(@Param("rootId") Integer rootId);
}
