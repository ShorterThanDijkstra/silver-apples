package backend.mwvb.mapper;

import backend.mwvb.entity.Root;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RootMapper {

    @Select(" SELECT id, name, description FROM root WHERE unit_id IN " +
            " (SELECT id FROM unit WHERE index=#{index}) ")
    @Results({
            @Result(property = "words",
                    column = "id",
                    many = @Many(select = "backend.mwvb.mapper.WordMapper.wordsInRoot") )
    })
    List<Root> rootsInUnit(@Param("index") Integer index);

}
