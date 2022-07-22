package backend.mwvb.mapper;

import backend.mwvb.entity.UserWordPractice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface UserWordPracticeMapper {
    @Insert(" INSERT INTO user_word_practice " +
            " (user_id, word_id, sentence, create_time) " +
            " VALUES (#{practice.user.id}, #{practice.word.id}, #{practice.sentence}, #{practice.createTime})"
    )
    void insert(@Param("practice") UserWordPractice userWordPractice);

    @Select(" select id, user_id, word_id, sentence, create_time from user_word_practice where word_id=#{wordId} ")
    @Results(
            id = "UserWordPracticeMap",
            value = {
                    @Result(column = "create_time", property = "createTime", javaType = OffsetDateTime.class, jdbcType = JdbcType.TIMESTAMP_WITH_TIMEZONE),

                    @Result(property = "user",
                            column = "user_id",
                            many = @Many(select = "backend.mwvb.mapper.UserMapper.queryUserById")),
                    @Result(property = "word",
                            column = "word_id",
                            many = @Many(select = "backend.mwvb.mapper.WordMapper.queryWordById"))
            })
    List<UserWordPractice> queryByWordId(@Param("wordId") Integer wordId);
}
