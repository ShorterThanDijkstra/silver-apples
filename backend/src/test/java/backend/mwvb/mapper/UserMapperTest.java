package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    void register() {
        User user = User.builder().name("test").password("password").email("email@gmail.com").isActive(false).nickName("nickName").build();
        userMapper.insert(user);
    }

    @Test
    @Disabled
    void usernameExist() {
        boolean result = userMapper.usernameExist("test");
        System.out.println(result);
        assertThat(result, is(true));
    }

    @Test
    @Disabled
    void emailExist() {
        boolean result = userMapper.emailExist("dhlidj@qq.com");
        System.out.println(result);
        assertThat(result, is(true));
    }

    @Test
    @Disabled
    void queryUser() {
        User user = userMapper.queryUserByName("dewdew");
        assertThat(user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), notNullValue());
        assertThat(user.getEmail(), notNullValue());
        assertThat(user.getIsActive(), notNullValue());
        assertThat(user.getNickName(), notNullValue());
        assertThat(user.getConfirmPassword(), nullValue());
    }

    @Test
    @Disabled
    void queryUserByEmail() {
        User user = userMapper.queryUserByEmail("ssssss@qq.com");
        System.out.println(user);
        assertThat(user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), notNullValue());
        assertThat(user.getEmail(), notNullValue());
        assertThat(user.getIsActive(), notNullValue());
        assertThat(user.getNickName(), notNullValue());
        assertThat(user.getConfirmPassword(), nullValue());
    }
}