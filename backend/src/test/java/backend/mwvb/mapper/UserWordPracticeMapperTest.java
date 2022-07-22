package backend.mwvb.mapper;

import backend.mwvb.entity.UserWordPractice;
import backend.mwvb.entity.Word;
import backend.mwvb.service.UserRegisterService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import java.time.OffsetDateTime;

import static backend.mwvb.test_util.BookTestUtilKt.randomSentence;
import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class UserWordPracticeMapperTest {
    @Autowired
    private UserWordPracticeMapper userWordPracticeMapper;

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private RootMapper rootMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WordMapper wordMapper;

    @Test
    void insertThenQuery() throws MessagingException {
        val randomUser = registerRandomUser(userRegisterService);
        val user = userMapper.queryUserByEmail(randomUser.getEmail());
        val word = selectWord();
        val sentence = randomSentence();
        val newPractice = new UserWordPractice(user, word, sentence, OffsetDateTime.now());
        userWordPracticeMapper.insert(newPractice);

        val practices = userWordPracticeMapper.queryByWordId(word.getId());
        val match = practices.stream().anyMatch(
                practice -> {
                    assert practice.getUser() != null;
                    return practice.getUser().equals(newPractice.getUser()) &&
                            practice.getWord().equals(newPractice.getWord()) &&
                            practice.getSentence().equals(newPractice.getSentence());
                }
        );

        assertThat(match, is(true));
    }

    Word selectWord() {
        return rootMapper
                .rootsInUnit(13)
                .get(6).getWords().get(3);
    }


}