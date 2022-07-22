package backend.mwvb.entity;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class UserWordPractice {
    private Integer id;
    @Nullable
    private User user;
    @NotNull
    private Word word;
    @NotNull
    private String sentence;
    @Nullable
    private OffsetDateTime createTime;

    public UserWordPractice() {

    }

    public UserWordPractice(@Nullable User user, Word word, String sentence, @Nullable OffsetDateTime createTime) {
        this.user = user;
        this.word = word;
        this.sentence = sentence;
        this.createTime = createTime;
    }

    public UserWordPractice(Word word, String sentence) {
        this.word = word;
        this.sentence = sentence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public User getUser() {
        return user;
    }

    public void setUser(@Nullable User user) {
        this.user = user;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Nullable
    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(@Nullable OffsetDateTime createTime) {
        this.createTime = createTime;
    }
}
