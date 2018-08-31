package seng202.group8.services.goals_service.goal_types;

import org.junit.After;
import org.junit.Before;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.HikeData;
import seng202.group8.user.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FrequencyGoalTest {

    private FrequencyGoal frequencyGoal;
    private User user;
    private ActivityList activityList;
    private HikeData hikeData1;
    private HikeData hikeData2;


    @Before
    public void setUp() throws Exception {
        user = new User("Lorenzo", 22, 83.0, 183.0);
        user.getUserActivities().insertActivityList(new ActivityList("Hikes"));
        frequencyGoal = new FrequencyGoal(user, "Gotta go running more!",
                        GoalType.TimePerformedGoal, DataType.HIKE, 2);
        hikeData1 = new HikeData("Hike", DataType.HIKE, new ArrayList<Double>(), new ArrayList<Integer>());
        hikeData2 = new HikeData("Hike", DataType.HIKE, new ArrayList<Double>(), new ArrayList<Integer>());
        user.getUserActivities().insertActivityInGivenList(0, hikeData1);
        user.getUserActivities().insertActivityInGivenList(0, hikeData2);

    }

    @After
    public void tearDown() throws Exception {
//        frequencyGoal
    }
}