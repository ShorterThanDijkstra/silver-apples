package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class SimpleQuizPage {
    @JsonIgnore
    private final Integer id;

    private final String content;

    private List<String> answers;

}
