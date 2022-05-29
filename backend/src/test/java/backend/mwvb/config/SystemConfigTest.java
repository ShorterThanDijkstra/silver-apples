package backend.mwvb.config;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class SystemConfigTest {
    private Pattern emailValidator;

    @Autowired
    public void setEmailValidator(Pattern emailValidator) {
        this.emailValidator = emailValidator;
    }

    @ParameterizedTest
    @ValueSource(strings = {"kiskanadya@x4u.me", "romanic@gwenbd94.com"})
    void emailValidator(String email) {
        assertThat(emailValidator.matcher(email).matches(), is(true));

    }
}