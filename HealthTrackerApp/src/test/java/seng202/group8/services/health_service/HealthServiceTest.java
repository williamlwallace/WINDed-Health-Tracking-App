package seng202.group8.services.health_service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.RunData;
import seng202.group8.data_entries.WalkData;
import seng202.group8.user.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HealthServiceTest {

    private RunData runData;
    private WalkData walkData;
    private HealthService healthService;
    private ActivityList activityList;
    private User user;


    @Before
    public void setUp() throws Exception {
        user = new User("Lorenzo", 22, 83.0, 183.0);
        healthService = new HealthService(user);
//        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
//        heartRateList.add(130);
//        heartRateList.add(21);
//        runData = new RunData("Run forrest run!", DataType.RUN, new ArrayList<Double>(), heartRateList);
        activityList = new ActivityList("Bad forrest gump imitation");
    }

    @After
    public void tearDown() throws Exception {
        runData = null;
        walkData = null;
        healthService = null;
        user = null;
        activityList = null;
    }

    @Test
    public void checkIsTachicardicTrue() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(330);
        heartRateList.add(21);
        runData = new RunData("Run forrest run!", DataType.RUN, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        assertTrue(healthService.isTachicardic());
    }

    @Test
    public void checkIsBradicardic() {
    }

    @Test
    public void checkIsAtCardiovascularRisk() {
    }
}