package backend.mwvb.entity;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

public class UserCustomWord {

    @Nullable
    private Integer id;
    @Nullable
    private Integer userId;
    @NotNull
    private String spell;

    @NotNull
    private String explanation;
    @Nullable
    private String sentence;
    @Nullable
    private String note;
    @NotNull
    private OffsetDateTime createTime;

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public @Nullable Integer getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Integer userId) {
        this.userId = userId;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Nullable
    public String getSentence() {
        return sentence;
    }

    public void setSentence(@Nullable String sentence) {
        this.sentence = sentence;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    public void setNote(@Nullable String note) {
        this.note = note;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCustomWord word = (UserCustomWord) o;

        if (!Objects.equals(userId, word.userId)) return false;
        if (!Objects.equals(spell, word.spell)) return false;
        if (!Objects.equals(explanation, word.explanation)) return false;
        if (!Objects.equals(sentence, word.sentence)) return false;
        if (!Objects.equals(note, word.note)) return false;
        return Objects.equals(createTime.toLocalDate(), word.createTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (spell != null ? spell.hashCode() : 0);
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
