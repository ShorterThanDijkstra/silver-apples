package backend.mwvb.mapper;

import backend.mwvb.entity.UserCustomWord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Lazy;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface UserCustomWordMapper {

    @Insert(" INSERT INTO user_custom_word " +
            " (user_id, spell, explanation, sentence, note, create_time) VALUES " +
            " (#{customWord.userId}, #{customWord.spell}, #{customWord.explanation}, #{customWord.sentence}, #{customWord.note}, #{customWord.createTime}) ")
    void insert(@Param("customWord") UserCustomWord customWord);

    @Select(" SELECT id, user_id, spell, sentence, note, create_time, explanation " +
            " FROM user_custom_word " +
            " WHERE user_id = #{userId}" +
            " ORDER BY create_time DESC ")
    @Results(id = "userCustomWordMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "spell", property = "spell", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "sentence", property = "sentence", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "note", property = "note", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", javaType = OffsetDateTime.class, jdbcType = JdbcType.TIMESTAMP_WITH_TIMEZONE),
            @Result(column = "explanation", property = "explanation", javaType = String.class, jdbcType = JdbcType.VARCHAR),

    })
    List<UserCustomWord> queryByUseId(@Param("userId") Integer  userId);

    @Delete(" DELETE FROM user_custom_word WHERE id=#{wordId} ")
    void delete(@Param("wordId") Integer wordId);
}
