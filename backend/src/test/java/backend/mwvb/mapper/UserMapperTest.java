package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    private static User user;

    @BeforeAll
    public static void init() {
        user = randomUser();
    }

    private static User randomUser() {
        String name = RandomStringUtils.randomAlphanumeric(5, 10);
        String password = RandomStringUtils.randomAlphanumeric(10, 16);
        String emailPrefix = RandomStringUtils.randomAlphanumeric(5, 10);
        String emailSuffix = RandomStringUtils.randomAlphabetic(2, 5) +
                "." + RandomStringUtils.randomAlphabetic(2, 3);
        String email = emailPrefix + "@" + emailSuffix;
        OffsetDateTime now = OffsetDateTime.now();
        return User.builder()
                .username(name)
                .password(password)
                .email(email)
                .createTime(now)
                .build();
    }

    @Test
    @Order(1)
    public void register() {
        userMapper.insert(user);
    }

    @Test
    @Order(2)
    public void usernameExist() {
        boolean result = userMapper.usernameExist(user.getUsername());
        System.out.println(result);
        assertThat(result, is(true));
    }

    @Test
    @Order(3)
    public void emailExist() {
        boolean result = userMapper.emailExist(user.getEmail());
        System.out.println(result);
        assertThat(result, is(true));
    }


    @Test
    @Order(4)
    public void queryUserByName() {
        User user = userMapper.queryUserByName(UserMapperTest.user.getUsername());
        assertThat(user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getUsername(), notNullValue());
        assertThat(user.getEmail(), notNullValue());
        assertThat(user.getCreateTime(), notNullValue());
    }

    @Test
    @Order(5)
    public void queryUserByEmail() {
        User user = userMapper.queryUserByEmail(UserMapperTest.user.getEmail());
        assertThat(user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getUsername(), notNullValue());
        assertThat(user.getEmail(), notNullValue());
        assertThat(user.getCreateTime(), notNullValue());
    }

    @Test
    @Order(6)
    public void updatePasswordEmail() {
        String newPassword = RandomStringUtils.randomAlphanumeric(10, 16);
        userMapper.updatePasswordByEmail(user.getEmail(), newPassword);
        User updatedUser = userMapper.queryUserByEmail(user.getEmail());
        assertThat(updatedUser.getPassword(), equalTo(newPassword));
        assertThat(updatedUser.getPassword(), not(equalTo(user.getPassword())));
    }

    @Test
    @Order(7)
    public void queryUserById() {
        val userByName = userMapper.queryUserByName(UserMapperTest.user.getUsername());
        val userById = userMapper.queryUserById(userByName.getId());
        assertThat(userById, equalTo(userByName));
    }
}