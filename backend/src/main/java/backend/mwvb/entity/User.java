package backend.mwvb.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class User {
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 5)
    private String name;

    @NotNull
    @Size(min = 10)
    @NotBlank
    private String password;

    @NotNull
    @Email
    @NotBlank
    private String email;

    private Boolean isActive;

    @NotNull
    @NotBlank
    private String nickName;

    @NotNull
    @NotBlank
    private String confirmPassword;
}
