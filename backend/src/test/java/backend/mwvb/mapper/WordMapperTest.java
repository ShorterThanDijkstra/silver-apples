package backend.mwvb.mapper;

import backend.mwvb.entity.Root;
import backend.mwvb.entity.Unit;
import backend.mwvb.entity.Word;
import lombok.val;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class WordMapperTest {
    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private RootMapper rootMapper;

    @Test
    void wordsInRoot() {
        val unitIndex = RandomUtils.nextInt(1, Unit.UNIT_COUNT + 1);
        val roots = rootMapper.rootsInUnit(unitIndex);
        val rootIndex = RandomUtils.nextInt(0, Root.ROOTS_IN_UNIT_COUNT);
        List<Word> words = wordMapper.wordsInRoot(roots.get(rootIndex).getId());
        assertThat(words.size(), equalTo(Word.WORDS_IN_ROOT_COUNT));
    }

    @Test
    void queryWord() {
        val wordBySpell = wordMapper.queryWordBySpell("ambient");
        val wordById = wordMapper.queryWordById(wordBySpell.getId());
        assertThat(wordById, equalTo(wordBySpell));
    }
}