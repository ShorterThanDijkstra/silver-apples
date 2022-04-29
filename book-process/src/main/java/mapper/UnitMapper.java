package mapper;

import entity.Unit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UnitMapper {
    void insert(@Param("unit") Unit unit);
}
