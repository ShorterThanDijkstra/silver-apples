package com.github.maerd_zinbiel.backend.mwvb.mapper;

import com.github.maerd_zinbiel.backend.mwvb.domain.Sentence;
import com.github.maerd_zinbiel.backend.mwvb.domain.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SentenceMapper {
    void insertSentences(@Param("sentences") List<Sentence> sentences, @Param("wordId") Integer wordId);

    void insertSentencesOfSpecialWord(@Param("word") Word word);
}
