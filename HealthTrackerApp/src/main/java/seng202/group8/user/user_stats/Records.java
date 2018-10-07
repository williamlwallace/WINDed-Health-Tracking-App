package seng202.group8.user.user_stats;

import java.time.LocalDateTime;

public class Records extends UserStats{

    /**
     * Initial Variables
     */
    public LocalDateTime date;


    /**
     * @return the date for when the record was entered
     */
    public LocalDateTime getDate() {
        return date;
    }


    /**
     * Sets the records date to a new date
     * @param date the local date time that you want to set the records date to
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    /**
     * Creates a new date and adds it to the record
     */
    public void createDate() {
        this.date = LocalDateTime.now();
    }

}
