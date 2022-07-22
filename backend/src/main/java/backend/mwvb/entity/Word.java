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

    public Integer getId() {
        return id;
    }

    public String getSpell() {
        return spell;
    }

    public String getExplain() {
        return explain;
    }

    public String getDetail() {
        return detail;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
}
