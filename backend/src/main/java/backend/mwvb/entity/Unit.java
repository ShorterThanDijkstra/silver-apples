package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Unit {
    @JsonIgnore
    public static final int UNIT_COUNT = 30;

    @JsonIgnore
    private final Integer id;

    @Size(min = 1, max = 30)
    private final int index;

    private List<Root> roots;

    private List<Quiz> quizzes;

    private List<Word> specialSectionWords;
}
