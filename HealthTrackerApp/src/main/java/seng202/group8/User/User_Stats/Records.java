package seng202.group8.User.User_Stats;

import java.util.Date;

public class Records extends UserStats{
    /**
     * Initial Variables
     */
    public Date date;

    /**
     * @return the date for when the record was entered
     */
    public Date getDate() {
        return date;
    }

    /**
     * Creates a new date and adds it to the record
     */
    public void createDate() {
        this.date = new Date();
    }
}
