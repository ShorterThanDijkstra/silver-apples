package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Root {
    @JsonIgnore
    public static int ROOTS_IN_UNIT_COUNT = 8;
    private final Integer id;
    private final String name;
    private final String description;

    private List<Word> words;
}
