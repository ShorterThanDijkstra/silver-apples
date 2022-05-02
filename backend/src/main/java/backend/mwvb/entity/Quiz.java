package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Quiz {
    @JsonIgnore
    private final Integer id;
}
