package seng202.group8.activity_collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ActivityListTest {

    private ActivityList activityList;
    private Activity jog;
    private Activity fastWalk;

    @Before
    public void setUp() throws Exception {
        activityList = new ActivityList("Jog with friends");
        activityList.setTitle("Intense runs");
        jog = new Activity("Morning jog");
        fastWalk = new Activity("Fast Walk");
    }

    @After
    public void tearDown() throws Exception {
        activityList = null;
        jog = null;
        fastWalk = null;
    }


    @Test
    public void insertActivityOneVal() {
        activityList.insertActivity(fastWalk);

        Activity posOneActivity = activityList.getActivity(0);
        assertEquals(posOneActivity.getTitle(), "Fast Walk");
    }

    @Test
    public void insertActivityCheckSorting() {

        fastWalk.setCreationDate(new Date(1234));
        activityList.insertActivity(jog);
        activityList.insertActivity(fastWalk);
        Activity activityAtZero = activityList.getActivity(0);

        assertEquals(activityAtZero.getTitle(), "Fast Walk");
    }

    @Test
    public void setTitleAcceptedBehaviour() {
        assertEquals(activityList.getTitle(), "Intense runs");
    }

    @Test
    public void setTitleRejectedBehaviour() {
        activityList.setTitle("Lazy");
        assertEquals(activityList.getTitle(), "Intense runs");
    }


    @Test
    public void setCreationDateAcceptedBehaviour() {
        Date veryOldDate = new Date(1234);
        activityList.setCreationDate(veryOldDate);
        assertEquals(activityList.getCreationDate().getTime(), 1234);
    }

    @Test
    public void setCreationDateRejectedBehaviour() {
        // Create date in far future
        Date currentDate = new Date();
        Date dateInFarFuture = new Date(2 * currentDate.getTime());

        activityList.setCreationDate(new Date(1234));
        activityList.setCreationDate(dateInFarFuture);

        assertEquals(activityList.getCreationDate().getTime(), 1234);
    }
}