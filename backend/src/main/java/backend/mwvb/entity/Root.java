package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Root {
    private final Integer id;
    private final String name;
    private final String description;
}
