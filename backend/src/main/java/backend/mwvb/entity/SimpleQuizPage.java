package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SimpleQuizPage {
    private final String content;
    private String[] answers;
    @JsonIgnore
    private Integer id;
}
