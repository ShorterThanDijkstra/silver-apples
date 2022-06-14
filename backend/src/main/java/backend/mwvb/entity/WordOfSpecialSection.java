package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class WordOfSpecialSection {
    @JsonIgnore
    public static final int WORDS_IN_SPECIAL_SECTION = 8;

    @JsonIgnore
    private final Integer id;

    private final String spell;
    private final String explain;
    private final String detail;

    private List<SentenceOfSpecialSectionWord> sentences;
}
