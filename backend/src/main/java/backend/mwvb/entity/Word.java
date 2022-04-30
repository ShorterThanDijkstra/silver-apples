package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Word {
    private final String spell;
    private final String explain;
    private final String detail;
    private List<Sentence> sentences;
    @JsonIgnore
    private Integer id;
}
