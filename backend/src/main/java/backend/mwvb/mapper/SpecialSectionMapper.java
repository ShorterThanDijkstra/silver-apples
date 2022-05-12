package backend.mwvb.mapper;

import backend.mwvb.entity.SentenceOfSpecialSectionWord;
import backend.mwvb.entity.WordOfSpecialSection;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface SpecialSectionMapper {
    @Select(" SELECT id, spell, explain, detail FROM special_section_word WHERE unit_id IN "+
    " (SELECT id FROM unit WHERE index=#{unitIndex}) ")
    @Results(
            @Result(
                    property = "sentences",
                    column = "id",
                    many = @Many(select = "backend.mwvb.mapper.SpecialSectionMapper.sentencesOfSpecialSectionWord")
            )
    )
    List<WordOfSpecialSection> specialWordsOfUnit(@Param("unitIndex") Integer unitIndex);

    @Select(" SELECT text FROM special_section_sentence where SPECIAL_SECTION_WORD_ID = #{wordId}")
    List<SentenceOfSpecialSectionWord> sentencesOfSpecialSectionWord(@Param("wordId") Integer wordId);
}
