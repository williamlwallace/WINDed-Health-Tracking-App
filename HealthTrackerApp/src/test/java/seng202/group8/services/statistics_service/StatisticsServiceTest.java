package seng202.group8.services.statistics_service;

import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.RunData;
import seng202.group8.data_entries.WalkData;
import seng202.group8.services.health_service.HealthService;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class StatisticsServiceTest {

    private RunData runData;
    private StatisticsService statsService;
    private ActivityList activityList;
    private User user;

    @Before
    public void start() throws Exception {
        user = new User("Joel", 19, 72.0, 175.0);
        statsService = new StatisticsService(user);
        activityList = new ActivityList("Stats Test");
        ArrayList<Integer> heartRateList = new ArrayList();
        heartRateList.add(100);
        heartRateList.add(50);
        runData = new RunData("Running", DataType.RUN, new ArrayList<LocalDateTime>(), new ArrayList<CoordinateData>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, runData);
    }

    @Test
    public void testAverageHeatRate() {
        Integer heartRate = statsService.getAverageHeartRate();
        Integer expected = 75;
        assertEquals(expected, heartRate);
    }


}