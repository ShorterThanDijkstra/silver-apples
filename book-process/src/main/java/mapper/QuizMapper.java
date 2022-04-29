package mapper;

import entity.Quiz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuizMapper {
    void insert(@Param("quiz") Quiz quiz, @Param("unitId") Integer unitId);
}
