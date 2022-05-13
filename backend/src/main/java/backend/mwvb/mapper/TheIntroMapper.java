package backend.mwvb.mapper;

import backend.mwvb.entity.TheIntro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TheIntroMapper {
    @Select(" SELECT paragraph FROM the_intro ")
    List<String> paragraphs();
}
