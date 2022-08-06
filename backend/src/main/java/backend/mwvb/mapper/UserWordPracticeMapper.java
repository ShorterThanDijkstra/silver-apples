package backend.mwvb.mapper;

import backend.mwvb.entity.UserWordActivity;
import backend.mwvb.entity.UserWordPractice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Lazy;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface UserWordPracticeMapper {
    @Insert(" INSERT INTO user_word_practice " +
            " (user_id, word_id, sentence, create_time) " +
            " VALUES (#{practice.user.id}, #{practice.word.id}, #{practice.sentence}, #{practice.createTime})"
    )
    void insert(@Param("practice") UserWordPractice userWordPractice);

    @Select(" SELECT id, user_id, word_id, sentence, create_time " +
            " FROM user_word_practice " +
            " WHERE word_id=#{wordId} " +
            " ORDER BY create_time ")
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

    @Select(" SELECT date_trunc('day', create_time) AS date, count(*) as count " +
            " FROM user_word_practice WHERE user_id=#{userId} " +
            " GROUP BY date ")
    List<UserWordActivity> queryActivitiesByUserId(@Param("userId") Integer userId);

    @Select(" SELECT date_trunc('day', create_time) AS date, count(*) as count " +
            " FROM user_word_practice WHERE user_id IN " +
            " (SELECT id FROM sys_user WHERE username = #{username}) " +
            " GROUP BY date ")
    List<UserWordActivity> queryActivitiesByUsername(@Param("username") String username);

    @Select(" SELECT id, word_id, sentence, create_time " +
            " FROM user_word_practice WHERE user_id IN " +
            " (SELECT id FROM sys_user WHERE username = #{username}) " +
            " ORDER BY create_time")
    @Results(
            value = {
                    @Result(column = "create_time", property = "createTime", javaType = OffsetDateTime.class, jdbcType = JdbcType.TIMESTAMP_WITH_TIMEZONE),
                    @Result(property = "word",
                            column = "word_id",
                            many = @Many(select = "backend.mwvb.mapper.WordMapper.queryWordById"))
            })
    List<UserWordPractice> wordsPracticesOfUser(@Param("username") String username);
}
