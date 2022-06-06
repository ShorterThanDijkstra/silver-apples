package backend.mwvb.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterInfo {
    @NotNull
    @NotBlank
    @Size(min = 5)
    private String username;

    @NotNull
    @NotBlank
    private String token;
    @NotNull
    @NotBlank
    @Size(min = 10)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 10)
    private String confirmedPassword;

    private String email;


}
