package backend.mwvb.mapper;

import backend.mwvb.entity.Root;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RootMapper {
    @Select("select name, description from root where unit_id in (select id from unit where index=#{index})")
    List<Root> rootsInUnit(@Param("index") Integer index);
}
