package backend.mwvb.entity;

import java.sql.Date;

public class UserWordActivity {
    private Date date;
    private Integer count;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserActive{" +
                "date=" + date +
                ", count=" + count +
                '}';
    }
}
