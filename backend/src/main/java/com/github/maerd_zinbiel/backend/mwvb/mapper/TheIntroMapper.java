package com.github.maerd_zinbiel.backend.mwvb.mapper;

import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TheIntroMapper {
    int insert(@Param("intro") TheIntro intro);

    List<String> getAllParagraphs();
}
