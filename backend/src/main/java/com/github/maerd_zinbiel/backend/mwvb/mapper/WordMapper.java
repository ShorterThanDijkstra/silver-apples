package com.github.maerd_zinbiel.backend.mwvb.mapper;

import com.github.maerd_zinbiel.backend.mwvb.domain.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WordMapper {
    void insert(@Param("word") Word word, @Param("rootId") Integer rootId);

    void insertWordInSpecialSection(@Param("word") Word word, @Param("unitId") Integer unitId);

}
