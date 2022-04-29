package backend.mwvb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
@Repository
public class Quiz {
    private Unit unit;
    private final List<SimpleQuizPage> simpleQuizPages;

    @JsonIgnore
    private Integer id;

    public Quiz() {
        simpleQuizPages = new LinkedList<>();
        unit = null;
        id = null;
    }

    public void appendSimpleQuizPage(SimpleQuizPage quizPage) {
        simpleQuizPages.add(quizPage);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SimpleQuizPage> getSimpleQuizPages() {
        return simpleQuizPages;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
