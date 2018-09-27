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

public class FrequencyGoalTest {

    private FrequencyGoal frequencyGoal;
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

        frequencyGoal = new FrequencyGoal(user, "Gotta go running more!",
                        GoalType.TimePerformedGoal, DataType.HIKE, 2, LocalDateTime.now());
        frequencyGoal.setStartDate(new Date(0));
        hikeData1 = new HikeData("Hike", DataType.HIKE, localTimes, coordinateList, heartRates, user);
        hikeData2 = new HikeData("Hike", DataType.HIKE, localTimes, coordinateList, heartRates, user);
        user.getUserActivities().insertActivityInGivenList(0, hikeData1);
    }

    @After
    public void tearDown() throws Exception {
        frequencyGoal = null;
        user = null;
        hikeData1 = null;
        hikeData2 = null;
    }

    //This test should show the goal is not complete
    @Test
    public void checkIsCompleteTest1() {
        frequencyGoal.checkIsCompleted();
        assertFalse(frequencyGoal.getIsCompleted());
    }

    //This test shows the goal is not completed but also that frequencyGoal.timesCurrentlyPerformedActivity
    // has been updated to 1
    @Test
    public void checkIsCompleteTest2() {
        frequencyGoal.checkIsCompleted();
        assertEquals(frequencyGoal.getTimesCurrentlyPerformedActivity().intValue(), 1);
    }

    //This test should show the goal is complete
    @Test
    public void checkIsCompleteTest3() {
        user.getUserActivities().insertActivityInGivenList(0, hikeData2);
        frequencyGoal.checkIsCompleted();
        assertTrue(frequencyGoal.getIsCompleted());
    }

    //This test shows the goal is completed but also that frequencyGoal.timesCurrentlyPerformedActivity
    // has been updated to 1
    @Test
    public void checkIsCompleteTest4() {
        user.getUserActivities().insertActivityInGivenList(0, hikeData2);
        frequencyGoal.checkIsCompleted();
        assertEquals(frequencyGoal.getTimesCurrentlyPerformedActivity().intValue(), 2);
    }


}