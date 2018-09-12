package seng202.group8.activity_collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.data_entries.*;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ActivityListTest {

    private ActivityList activityList;
    private RunData jog;
    private WalkData fastWalk;

    @Before
    public void setUp() throws Exception {
        User user = new User("A", 12, 34.9, 56.9);
        activityList = new ActivityList("Jog with friends");
        activityList.setTitle("Intense runs");
        jog = new RunData("Morning jog", DataType.NOT_ASSISTED_SPORTS_DATA, new ArrayList<LocalDateTime>(), new ArrayList<CoordinateData>(), new ArrayList<Integer>(), user);
        fastWalk = new WalkData("Fast Walk", DataType.NOT_ASSISTED_SPORTS_DATA, new ArrayList<LocalDateTime>(), new ArrayList<CoordinateData>(), new ArrayList<Integer>(), user);
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

        Data posOneActivity = activityList.getActivity(0);
        assertEquals(posOneActivity.getTitle(), "Fast Walk");
    }

    @Test
    public void insertActivityCheckSorting() {

        fastWalk.setCreationDate(new Date(1234));
        activityList.insertActivity(jog);
        activityList.insertActivity(fastWalk);
        Data activityAtZero = activityList.getActivity(0);

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