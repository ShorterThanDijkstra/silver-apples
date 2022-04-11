package com.github.maerd_zinbiel.backend.mwvb.mapper;

import com.github.maerd_zinbiel.backend.mwvb.domain.TheIntro;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TheIntroMapper {
    int insertIntro(@Param("intro") TheIntro intro);
    List<String> getAllParagraphs();
}
