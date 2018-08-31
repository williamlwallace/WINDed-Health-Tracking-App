package seng202.group8.user.user_stats;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Records extends UserStats{
    /**
     * Initial Variables
     */
    public String date;

    /**
     * @return the date for when the record was entered
     */
    public String getDate() {
        return date;
    }

    /**
     * Creates a new date and adds it to the record
     */
    public void createDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss ");
        this.date = format.format( new Date());
    }
}
