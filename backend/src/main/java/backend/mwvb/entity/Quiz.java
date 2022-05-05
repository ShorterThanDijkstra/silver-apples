package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Quiz {
    @JsonIgnore
    private final Integer id;

    @JsonIgnore
    private final Integer unitId;

    private List<SimpleQuizPage> quizPages;
}
