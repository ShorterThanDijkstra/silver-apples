package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Root {
    private final Integer id;
    private final String name;
    private final String description;

    private List<Word> words;
}
