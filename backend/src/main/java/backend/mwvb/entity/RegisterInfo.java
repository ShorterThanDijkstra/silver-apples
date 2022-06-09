package backend.mwvb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Size(min = 10)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 10)
    private String confirmedPassword;

    @NotNull
    @NotBlank
    private String token;


}
