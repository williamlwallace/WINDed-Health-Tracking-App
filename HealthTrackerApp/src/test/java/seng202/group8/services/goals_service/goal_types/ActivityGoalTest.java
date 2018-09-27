package seng202.group8.services.goals_service.goal_types;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.HikeData;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ActivityGoalTest {


    private ActivityGoal activityGoal;
    private User user;
    private HikeData hikeData1;
    private HikeData hikeData2;


    @Before
    public void setUp() throws Exception {
        user = new User("Lorenzo", 22, 83.0, 183.0, Sex.MALE);
        user.getUserActivities().insertActivityList(new ActivityList("Hikes"));

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


        activityGoal = new ActivityGoal(user, "Gotta go running more!",
                GoalType.ActivityGoal, DataType.HIKE, 250.0, LocalDateTime.now());
        activityGoal.setStartDate(new Date(0));
        hikeData1 = new HikeData("Hike", DataType.HIKE, localTimes, coordinateList, heartRates, user);
        hikeData1.setDistanceCovered(200.0);
        hikeData2 = new HikeData("Hike", DataType.HIKE, localTimes, coordinateList, heartRates, user);
        hikeData2.setDistanceCovered(100.0);
        user.getUserActivities().insertActivityInGivenList(0, hikeData1);
    }

    @After
    public void tearDown() throws Exception {
        activityGoal = null;
        user = null;
        hikeData1 = null;
        hikeData2 = null;
    }

    @Test
    public void checkIsCompleteTest1() {
        activityGoal.checkIsCompleted();
        assertFalse(activityGoal.getIsCompleted());
    }


    @Test
    public void checkIsCompleteTest2() {
        activityGoal.checkIsCompleted();
        assertEquals(activityGoal.getDistanceCurrentlyCovered(), 200.0, 10e-2);
    }

    @Test
    public void checkIsCompleteTest3() {
        user.getUserActivities().insertActivityInGivenList(0, hikeData2);
        activityGoal.checkIsCompleted();
        assertTrue(activityGoal.getIsCompleted());
    }


    @Test
    public void checkIsCompleteTest4() {
        user.getUserActivities().insertActivityInGivenList(0, hikeData2);
        activityGoal.checkIsCompleted();
        assertEquals(activityGoal.getDistanceCurrentlyCovered(), 300.0, 10e-2);
    }

}