package backend.mwvb.mapper;

import backend.mwvb.entity.Quiz;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
public interface QuizMapper {
    @Select("SELECT id, unit_id FROM quiz WHERE unit_id IN " +
            " ( SELECT id FROM unit WHERE index=#{unitIndex} ) ")
    @Result(
            property = "quizPages",
            column = "id",
            many = @Many(select = "backend.mwvb.mapper.SimpleQuizPageMapper.quizPagesInQuiz")
    )
    List<Quiz> quizzesInUnit(@Param("unitIndex") Integer unitIndex);
}
