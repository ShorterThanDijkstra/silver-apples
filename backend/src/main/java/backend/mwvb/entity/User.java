package backend.mwvb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;

    private String name;

    private String password;

    private String email;

    private OffsetDateTime createTime;

    public static User fromRegisterInfo(RegisterInfo info) {
        User user = new User();
        user.setName(info.getUsername());
        user.setPassword(info.getPassword());
        user.setEmail(info.getEmail());
        user.createTime = OffsetDateTime.now();
        return user;
    }
}
