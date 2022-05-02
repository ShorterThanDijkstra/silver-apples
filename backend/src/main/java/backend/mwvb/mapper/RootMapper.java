package backend.mwvb.mapper;

import backend.mwvb.entity.Root;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RootMapper {

    @Select(" SELECT id, name, description FROM root WHERE unit_id IN " +
            " (SELECT id FROM unit WHERE index=#{index}) ")
    List<Root> rootsInUnit(@Param("index") Integer index);
}
