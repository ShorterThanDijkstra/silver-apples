package mapper;

import entity.Root;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RootMapper {
    void insert(@Param("root") Root root, @Param("unitId") Integer unitId);
}
