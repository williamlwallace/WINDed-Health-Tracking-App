package seng202.group8.services.goals_service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.RunData;
import seng202.group8.data_entries.WalkData;
import seng202.group8.services.goals_service.goal_types.ActivityGoal;
import seng202.group8.services.goals_service.goal_types.FrequencyGoal;
import seng202.group8.services.goals_service.goal_types.GoalType;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class GoalsServiceTest {


    private User user;
    private GoalsService goalsService;
    private ActivityGoal activityGoal;
    private FrequencyGoal frequencyGoal;
    private ActivityList activityList;
    private ActivityList activityList1;
    private WalkData walkData;
    private RunData runData;


    @Before
    public void setUp() throws Exception {
        activityList = new ActivityList("Walks in park");
        activityList.setCreationDate(new Date(0));
        activityList1 = new ActivityList("Runs in bush");
        activityList1.setCreationDate(new Date(0));

        walkData = new WalkData("walk ciao", DataType.WALK,new ArrayList<LocalDateTime>(), new ArrayList<CoordinateData>(), new ArrayList<Integer>(), user);
        runData = new RunData("Run ciao", DataType.RUN, new ArrayList<LocalDateTime>(), new ArrayList<CoordinateData>(), new ArrayList<Integer>(), user);
        user = new User("Lorenzo", 22, 82.0, 183.0, Sex.MALE);

        goalsService = new GoalsService(user);

        activityGoal = new ActivityGoal(user, "", GoalType.ActivityGoal, DataType.WALK, 200.0);
        activityGoal.setStartDate(new Date(0));
        frequencyGoal = new FrequencyGoal(user, "", GoalType.TimePerformedGoal, DataType.RUN, 3);
        frequencyGoal.setStartDate(new Date(0));

        goalsService.getCurrentActivityGoals().add(activityGoal);
        goalsService.getCurrentTimesPerformedGoals().add(frequencyGoal);

        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityList(activityList1);
    }

    @After
    public void tearDown() throws Exception {
        user = null;
        goalsService = null;
        activityGoal = null;
        frequencyGoal = null;
        activityList = null;
        activityList1 = null;
        walkData = null;
        runData = null;
    }

    @Test
    public void updateCorrect() {
        walkData.setDistanceCovered(200.1);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertEquals(goalsService.getCurrentActivityGoals().size(), 0);
    }

    @Test
    public void updateCorrect1() {
        walkData.setDistanceCovered(200.0);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(1, runData);
        user.getUserActivities().insertActivityInGivenList(1, runData);
        user.getUserActivities().insertActivityInGivenList(1, runData);
        assertEquals(goalsService.getCurrentTimesPerformedGoals().size(), 0);
    }

    @Test
    public void updateCorrect3() {
        walkData.setDistanceCovered(200.0);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(1, runData);
        user.getUserActivities().insertActivityInGivenList(1, runData);
        user.getUserActivities().insertActivityInGivenList(1, runData);
        assertEquals(goalsService.getCurrentActivityGoals().size(), 0);
    }
}