package seng202.group8.activity_collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.data_entries.*;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

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
        User user = new User("A", 12, 34.9, 56.9, Sex.MALE);
        activityList = new ActivityList("Jog with friends");
        activityList.setTitle("Intense runs");

        ArrayList<LocalDateTime> localTimes = new ArrayList<LocalDateTime>();
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 10));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 15));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 25));

        ArrayList<Integer> heartRates = new ArrayList<Integer>();
        heartRates.add(100);
        heartRates.add(115);
        heartRates.add(120);

        ArrayList<CoordinateData> coordinateList = new ArrayList<CoordinateData>();
        coordinateList.add(new CoordinateData(30.26881985,-97.83246599,204.4));
        coordinateList.add(new CoordinateData(30.26868423,-97.83252265,202));
        coordinateList.add(new CoordinateData(30.26863712,-97.83267747,201.5));

        jog = new RunData("Morning jog", DataType.NOT_ASSISTED_SPORTS_DATA, localTimes, coordinateList, heartRates, user);
        fastWalk = new WalkData("Fast Walk", DataType.NOT_ASSISTED_SPORTS_DATA, localTimes, coordinateList, heartRates, user);
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