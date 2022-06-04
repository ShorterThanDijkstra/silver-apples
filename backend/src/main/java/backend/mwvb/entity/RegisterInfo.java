package backend.mwvb.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RegisterInfo(@NotNull @NotBlank @Size(min = 5) String username,
                           @NotNull @NotBlank @Email String email,
                           @NotNull @NotBlank @Size(min = 10) String password,
                           @NotNull @NotBlank @Size(min = 10) String confirmedPassword) {
}
