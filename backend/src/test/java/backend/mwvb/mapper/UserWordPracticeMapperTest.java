package backend.mwvb.mapper;

import backend.mwvb.entity.User;
import backend.mwvb.entity.UserWordActivity;
import backend.mwvb.entity.UserWordPractice;
import backend.mwvb.entity.Word;
import backend.mwvb.service.UserRegisterService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static backend.mwvb.test_util.BookTestUtilKt.randomSentence;
import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
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
    private UserWordPractice newPractice;
    private User user;

    @BeforeEach
    public void insert() throws MessagingException {
        val randomUser = registerRandomUser(userRegisterService);
        user = userMapper.queryUserByEmail(randomUser.getEmail());
        val word = selectWord();
        val sentence = randomSentence();
        val insert = new UserWordPractice(user, word, sentence, OffsetDateTime.now());
        userWordPracticeMapper.insert(insert);
        newPractice = insert;
    }

    @Test
    void queryByWordId() {
        val wordId = newPractice.getWord().getId();
        val practices = userWordPracticeMapper.queryByWordId(wordId);
        val match = match(practices);

        assertThat(match, is(true));
    }

    Word selectWord() {
        return rootMapper.rootsInUnit(13).get(6).getWords().get(3);
    }

    @Test
    void getActivitiesByUserId() {
        val activities = userWordPracticeMapper.queryActivitiesByUserId(user.getId());
        val activeCount = equalsToday(activities).getCount();
        assertThat(activeCount, is(greaterThan(0)));
    }

    @Test
    void getActivitiesByUsername() {
        val activities = userWordPracticeMapper.queryActivitiesByUsername(user.getUsername());
        val activeCount = equalsToday(activities).getCount();
        assertThat(activeCount, is(greaterThan(0)));
    }

    @Test
    void wordsPracticesOfUser() {
        val practices = userWordPracticeMapper.wordsPracticesOfUser(user.getUsername());
        val match = practices.stream()
                .anyMatch(practice -> practice.getWord().equals(newPractice.getWord()) &&
                        practice.getSentence().equals(newPractice.getSentence()));
        assertThat(match, is(true));
    }

    private UserWordActivity equalsToday(List<UserWordActivity> activities) {
        val today = Date.valueOf(LocalDate.now());
        return activities.stream()
                .filter((activity) -> activity.getDate().equals(today))
                .findFirst()
                .orElseGet(UserWordActivity::new);
    }

    private boolean match(List<UserWordPractice> practices) {
        return practices.stream()
                .anyMatch(practice -> {
                    assert practice.getUser() != null;
                    return practice.getUser().equals(newPractice.getUser()) &&
                            practice.getWord().equals(newPractice.getWord()) &&
                            practice.getSentence().equals(newPractice.getSentence());
                });
    }
}