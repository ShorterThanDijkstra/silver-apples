package com.github.maerd_zinbiel.backend.mwvb.mapper;

import com.github.maerd_zinbiel.backend.mwvb.domain.SimpleQuizPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SimpleQuizPageMapper {
    void insertContent(@Param("page") SimpleQuizPage quizPage, @Param("quizId") Integer quizId);
    void insertAnswers(@Param("page") SimpleQuizPage quizPage);
}
