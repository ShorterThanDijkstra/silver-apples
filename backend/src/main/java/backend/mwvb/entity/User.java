package backend.mwvb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 126772185781331467L;

    private Integer id;

    private String username;

    private String password;

    private String email;

    private OffsetDateTime createTime;

    public static User build(RegisterInfo info, String email) {
        User user = new User();
        user.setUsername(info.getUsername());
        user.setPassword(info.getPassword());
        user.setEmail(email);
        user.createTime = OffsetDateTime.now();
        return user;
    }
}
