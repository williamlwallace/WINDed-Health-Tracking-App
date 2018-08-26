package seng202.group8.activity_collection;

import java.util.Date;

public class Activity {
    int mockVal;
    Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;

    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    String title;

    public Activity(String title) {
        this.title = title;
        this.creationDate = new Date();
    }

    public int getMockVal() {
        return mockVal;
    }
}
