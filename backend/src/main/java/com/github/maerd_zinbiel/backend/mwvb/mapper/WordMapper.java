package com.github.maerd_zinbiel.backend.mwvb.mapper;

import com.github.maerd_zinbiel.backend.mwvb.domain.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WordMapper {
    void insert(@Param("word") Word word, @Param("rootId") Integer rootId);
}
