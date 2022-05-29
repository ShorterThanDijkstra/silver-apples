package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Word {
    @JsonIgnore
    public static final int WORDS_IN_ROOT_COUNT = 4;
    private final Integer id;
    private final String spell;
    private final String explain;
    private final String detail;
    private List<Sentence> sentences;
}
