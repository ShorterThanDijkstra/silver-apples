package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
public class Unit {
    @JsonIgnore
    private final int index;

    @JsonIgnore
    private final Integer id;
}
