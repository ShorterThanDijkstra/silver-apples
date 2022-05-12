package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Unit {
    @JsonIgnore
    private final Integer id;

    private final int index;

    private List<Root> roots;

    private List<Quiz> quizzes;

    private List<Word> specialSectionWords;
}
