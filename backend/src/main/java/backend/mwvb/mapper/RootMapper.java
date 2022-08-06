package backend.mwvb.mapper;

import backend.mwvb.entity.Root;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
public interface RootMapper {

    @Select(" SELECT id, name, description FROM root WHERE unit_id IN " +
            " (SELECT id FROM unit WHERE index=#{unitIndex}) ")
    @Results({
            @Result(property = "words",
                    column = "id",
                    many = @Many(select = "backend.mwvb.mapper.WordMapper.wordsInRoot") )
    })
    List<Root> rootsInUnit(@Param("unitIndex") Integer unitIndex);

}
