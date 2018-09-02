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
    public void checkIsTachicardicHeavyWorkTrue() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(169);//Minimum for tachicardia (220 - 22) * 0.85 = 168.7
        heartRateList.add(21);
        runData = new RunData("Run forrest run!", DataType.RUN, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        assertTrue(healthService.isTachicardic());
    }

    @Test
    public void checkIsTachicardicHeavyWorkFalse() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(169);
        heartRateList.add(21);
        runData = new RunData("Run forrest run!", DataType.RUN, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        assertFalse(healthService.isTachicardic());
    }

    @Test
    public void checkIsTachicardicLightWorkTrue() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(101);
        heartRateList.add(21);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertTrue(healthService.isTachicardic());
    }

    @Test
    public void checkIsTachicardicMixedtWorkTrue() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(169);
        heartRateList.add(21);
        runData = new RunData("Run forrest run!", DataType.RUN, new ArrayList<Double>(), heartRateList);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, runData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertTrue(healthService.isTachicardic());
    }


    @Test
    public void checkIsTachicardicLightWorkFalse1() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(99);
        heartRateList.add(21);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertFalse(healthService.isTachicardic());
    }

    @Test
    public void checkIsTachicardicLightWorkFalse2() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(101);
        heartRateList.add(21);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertFalse(healthService.isTachicardic());
    }


    @Test
    public void checkIsBradicardicTrue() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(101);
        heartRateList.add(59);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertTrue(healthService.isBradicardic());
    }

    @Test
    public void checkIsBradicardicFalse() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(101);
        heartRateList.add(59);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);

        assertFalse(healthService.isBradicardic());
    }


    @Test
    public void checkIsAtCardiovascularRiskTrue() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(101);
        heartRateList.add(59);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertTrue(healthService.isAtCardiovasMortalityRisk());
    }

    @Test
    public void checkIsAtCardiovascularRiskFalse() {
        ArrayList<Integer> heartRateList = new ArrayList<Integer>();
        heartRateList.add(101);
        heartRateList.add(59);
        walkData = new WalkData("Walk forrest walk!", DataType.WALK, new ArrayList<Double>(), heartRateList);
        user.getUserActivities().insertActivityList(activityList);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        user.getUserActivities().insertActivityInGivenList(0, walkData);
        assertFalse(healthService.isAtCardiovasMortalityRisk());
    }

}