package backend.mwvb.entity;

import lombok.Data;

import java.util.List;

@Data
public class Word {
    private final Integer id;
    private final String spell;
    private final String explain;
    private final String detail;
    private List<Sentence> sentences;
}
