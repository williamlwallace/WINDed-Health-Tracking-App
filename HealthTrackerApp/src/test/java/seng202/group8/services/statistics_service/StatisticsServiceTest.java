package seng202.group8.services.statistics_service;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.*;
import seng202.group8.parser.Parser;
import seng202.group8.services.health_service.HealthService;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StatisticsServiceTest {

    private StatisticsService statsService;
    private User user;
    private Data data;

    @Before
    public void start() throws Exception {
        this.user = new User("Joel", 19, 72.0, 175.0, Sex.MALE);
        this.statsService = new StatisticsService(user);
        Parser parserTest = new Parser("/home/cosc/student/jnr26/SENG202/SENG202Team8/seng202_2018_example_data_clean.csv", user);
        parserTest.parseFile();
        ArrayList<Data> dataList = parserTest.getDataList();
        this.user.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        for (int i = 0; i < dataList.size(); i++) {
            this.user.getUserActivities().insertActivityInGivenList(0, dataList.get(i));
        }
        this.data = dataList.get(4);
        user.updateWeight(10.0);
        user.updateWeight(80.0);
        user.updateWeight(70.0);
        statsService.updateHomeStats();
    }

    @Test
    public void testAverageHeatRate() {
        Integer heartRate = statsService.getAverageHeartRate();
        Integer expected = 144;
        assertEquals(expected, heartRate);
    }

    @Test
    public void testKmRunTotal() {
        Double kmRun = statsService.getKmRunTotal();
        Double expected = 11.028600927641806;
        assertEquals(expected, kmRun);
    }
    @Test
    public void testKmWalkTotal() {
        Double kmWalk = statsService.getkmWalkTotal();
        Double expected = 6.71793717146557;
        assertEquals(expected, kmWalk);
    }

    @Test
    public void testKmBikedTotal() {
        Double kmBike = statsService.getKmBikedTotal();
        Double expected = 1.1334396015012507;
        assertEquals(expected, kmBike);
    }

    @Test
    public void testKmTotal() {
        Double kmTotal = statsService.getKmTotal();
        Double expected = 24.593994676642048;
        assertEquals(expected, kmTotal);
    }

    @Test
    public void testWeightLostTotal() {
        Double lost = statsService.getWeightLossTotal();
        Double expected = 72.0;
        assertEquals(expected, lost);
    }

    @Test
    public void testCaloriesBurnedTotal() {
        Double lost = statsService.getCaloriesBurnedTotal();
        Double expected = 0.0;
        assertEquals(expected, lost);
    }


    @Test
    public void testDistanceGraph() {
        GraphXY graph = statsService.getDistanceOverTimeGraph(data);
        ArrayList<Double> correctY = new ArrayList<Double>(Arrays.asList(52.89999999999998, 52.89999999999998, 53.39999999999998));
        ArrayList<Double> correctX = new ArrayList<Double>(Arrays.asList(0.0, 1.0, 2.0));
        GraphXY graph2 = new GraphXY();
        graph2.setXAxis(correctX);
        graph2.setYAxis(correctY);
        assertTrue(EqualsBuilder.reflectionEquals(graph, graph2));
    }

    @Test
    public void testHeartRateGraph() {
        GraphXY graph = statsService.getHeartRateOverTimeGraph(data);
        ArrayList<Double> correctY = new ArrayList<Double>(Arrays.asList(139.0, 145.0, 149.0));
        ArrayList<Double> correctX = new ArrayList<Double>(Arrays.asList(0.0, 1.0, 2.0));
        GraphXY graph2 = new GraphXY();
        graph2.setXAxis(correctX);
        graph2.setYAxis(correctY);
        assertTrue(EqualsBuilder.reflectionEquals(graph, graph2));
    }

    @Test
    public void testCaloriesGraph() {
        GraphXY graph = statsService.getCaloriesBurnedOverTimeGraph(data);
        ArrayList<Double> correctY = new ArrayList<Double>(Arrays.asList(0.38149816762268957, 0.4016033301465902, 8.59199808795411));
        ArrayList<Double> correctX = new ArrayList<Double>(Arrays.asList(0.0, 1.0, 2.0));
        GraphXY graph2 = new GraphXY();
        graph2.setXAxis(correctX);
        graph2.setYAxis(correctY);
        assertTrue(EqualsBuilder.reflectionEquals(graph, graph2));
    }

    @Test
    public void testStressGraph() {
        GraphXY graph = statsService.getStressLevelOverTimeGraph(data);
        ArrayList<Double> correctY = new ArrayList<Double>(Arrays.asList(100.0, 50.0));
        ArrayList<Double> correctX = new ArrayList<Double>(Arrays.asList(0.0, 1.0));
        GraphXY graph2 = new GraphXY();
        graph2.setXAxis(correctX);
        graph2.setYAxis(correctY);
        assertTrue(EqualsBuilder.reflectionEquals(graph, graph2));
    }

    @Test
    public void testBMIGraph() {
        GraphXY graph = statsService.getGraphDataBMIType();
        ArrayList<Double> correctY = new ArrayList<Double>(Arrays.asList(23.510204081632654));
        ArrayList<Double> correctX = new ArrayList<Double>(Arrays.asList(0.0, 1.0));
        GraphXY graph2 = new GraphXY();
        graph2.setXAxis(correctX);
        graph2.setYAxis(correctY);
        //System.out.println(graph.getYAxis());
        assertTrue(EqualsBuilder.reflectionEquals(graph, graph2));
    }

    @Test
    public void testWeightGraph() {
        GraphXY graph = statsService.getGraphDataWeight();
        ArrayList<Double> correctY = new ArrayList<Double>(Arrays.asList(72.0, 10.0, 80.0, 70.0));
        ArrayList<Double> correctX = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        GraphXY graph2 = new GraphXY();
        graph2.setXAxis(correctX);
        graph2.setYAxis(correctY);
        assertTrue(EqualsBuilder.reflectionEquals(graph, graph2));
    }

    @Test
    public void testTimesAndGetDifferenceFunctions() {
        ArrayList<LocalDateTime> localTimes = new ArrayList<>();
        localTimes.add(LocalDateTime.now());
        localTimes.add(LocalDateTime.now().plusMinutes(1));
        localTimes.add(LocalDateTime.now().plusMinutes(2));
        ArrayList<Double> times = statsService.createTimes(localTimes);
        ArrayList<Double> expectedTimes = new ArrayList<Double>(Arrays.asList(0.0, 60.0, 120.0));
        assertEquals(expectedTimes, times);
    }



// [139, 145, 149, 147]
}