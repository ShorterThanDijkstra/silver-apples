package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    void register() {
        User user = User.builder()
                .name("test")
                .password("password")
                .email("email@gmail.com")
                .isActive(false)
                .nickName("nickName")
                .build();
        userMapper.insert(user);
    }
}