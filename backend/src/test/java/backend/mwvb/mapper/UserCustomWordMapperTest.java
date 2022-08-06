package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import backend.mwvb.entity.UserCustomWord;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class UserCustomWordMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCustomWordMapper userCustomWordMapper;

    private UserCustomWord customWord;

    private User user;

    @BeforeEach
    public void randomWord() {
        val randomUser = UserMapperTest.randomUser();
        userMapper.insert(randomUser);
        user = userMapper.queryUserByName(randomUser.getUsername());

        customWord = new UserCustomWord();
        customWord.setUserId(user.getId());
        customWord.setSpell(randomAlphanumeric(3, 6));
        customWord.setExplanation(randomAlphanumeric(7, 10));
        customWord.setSentence(randomAlphanumeric(8, 15));
        customWord.setNote(randomAlphanumeric(10, 25));
        customWord.setCreateTime(OffsetDateTime.now());

    }

    @Test
    void insertThenQuery() {
        userCustomWordMapper.insert(customWord);

        val words = userCustomWordMapper.queryByUseId(user.getId());
        val match = match(words, customWord);
        assertThat(match, is(true));
    }

    @Test
    void insertWithNullNoteThenQuery() {
        customWord.setNote(null);

        userCustomWordMapper.insert(customWord);

        val words = userCustomWordMapper.queryByUseId(user.getId());
        val match = match(words, customWord);
        assertThat(match, is(true));
    }

    @Test
    void insertWithNullSentenceThenQuery() {
        customWord.setSentence(null);

        userCustomWordMapper.insert(customWord);

        val words = userCustomWordMapper.queryByUseId(user.getId());
        val match = match(words, customWord);
        assertThat(match, is(true));
    }

    private boolean match(List<UserCustomWord> userCustomWords, UserCustomWord theWord) {
        val match = find(userCustomWords, theWord);
        return match != null;
    }

    private UserCustomWord find(List<UserCustomWord> words, UserCustomWord theWord) {
        return words.stream().filter(it -> it.equals(theWord)).findFirst().orElse(null);
    }

    @Test
    void delete() {
        userCustomWordMapper.insert(customWord);
        val wordsAfterInsert = userCustomWordMapper.queryByUseId(user.getId());
        val word = find(wordsAfterInsert, customWord);
        assertThat(word, is(notNullValue()));
        val wordId = word.getId();

        userCustomWordMapper.delete(wordId);
        val wordsAfterDelete = userCustomWordMapper.queryByUseId(user.getId());
        assertThat(wordsAfterDelete, hasSize(0));
    }
}