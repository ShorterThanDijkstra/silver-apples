package backend.mwvb.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TheIntroMapper {
    List<String> getAllParagraphs();
}
