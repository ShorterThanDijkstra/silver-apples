package mapper;

import entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WordMapper {
    void insert(@Param("word") Word word, @Param("rootId") Integer rootId);

    void insertWordInSpecialSection(@Param("word") Word word, @Param("unitId") Integer unitId);

}
