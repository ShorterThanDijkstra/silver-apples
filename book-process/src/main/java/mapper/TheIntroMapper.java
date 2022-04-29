package mapper;

import entity.TheIntro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TheIntroMapper {
    int insert(@Param("intro") TheIntro intro);
}
