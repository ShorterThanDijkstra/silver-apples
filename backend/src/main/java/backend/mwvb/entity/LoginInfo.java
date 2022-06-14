package backend.mwvb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private String email;
    @NotNull
    private String password;
}
