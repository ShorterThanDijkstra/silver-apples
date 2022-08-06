package backend.mwvb.mapper;

import backend.mwvb.entity.Unit;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Lazy;

@Mapper
public interface UnitMapper {
    @Select(" SELECT id, index FROM unit WHERE index = #{unitIndex}")
    @Results({
            @Result(
                    property = "roots",
                    column = "index",
                    many = @Many(select = "backend.mwvb.mapper.RootMapper.rootsInUnit")
            ),
            @Result(
                    property = "quizzes",
                    column = "index",
                    many = @Many(select = "backend.mwvb.mapper.QuizMapper.quizzesInUnit")
            ),
            @Result(
                    property = "specialSectionWords",
                    column = "index",
                    many = @Many(select = "backend.mwvb.mapper.SpecialSectionMapper.specialWordsOfUnit")
            )
    }
    )
    Unit unit(@Param("unitIndex") Integer unitIndex);
}
