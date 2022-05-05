package backend.mwvb.mapper;

import backend.mwvb.entity.SimpleQuizPage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SimpleQuizPageMapper {
    @Select("SELECT id, content, quiz_id FROM simple_quiz_page WHERE quiz_id = #{quizId}")
    @Result(
            property = "answers",
            column = "id",
            many = @Many(select = "backend.mwvb.mapper.SimpleQuizPageMapper.answersInQuizPage")
    )
    List<SimpleQuizPage> quizPagesInQuiz(@Param("quizId") Integer quizId);

    @Select("SELECT answer FROM simple_quiz_page_answer WHERE page_id= #{quizPageId} ")
    List<String> answersInQuizPage(@Param("quizPageId") Integer quizPageId);
}
