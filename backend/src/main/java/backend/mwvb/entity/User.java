package backend.mwvb.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class User {
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private Boolean isActive;

    @NotNull
    private String nickName;
}
