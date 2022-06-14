package backend.mwvb.mapper;

import backend.mwvb.entity.Word;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
class WordMapperTest {
    @Autowired
    private WordMapper wordMapper;

    @Test
    void wordsInRoot() {
        List<Word> words = wordMapper.wordsInRoot(258);
        assertThat(words.size(), equalTo(Word.WORDS_IN_ROOT_COUNT));
    }
}