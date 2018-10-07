package seng202.group8.data_entries_tests;

import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.*;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;
import seng202.group8.data_entries.Data;

import java.text.DateFormat;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;

import static org.junit.Assert.*;


/**
 * This is a test class for some of data's basic functionality. Most of the methods in data are
 * tested by their use in other classes (such as statistics service), or other methods rather
 * than unittests.
 */
public class DataTests {

    private User testUser;
    //private ArrayList<LocalDateTime> localDateTimes;


    @Before
    public void setUp() throws Exception {
        testUser = new User("Clarke", 20, 72.0, 167.0, Sex.MALE);
        ArrayList<Date> testTimes;
        Date timeOne;

        //String stringTimeOne = "00:00:00";
        //DateFormat dateTimeFormatter = new SimpleDateFormat("hh:mm:ss");
        //Date testTime = new Date(00-00-00);
        //String testTitle = "Testing Title.";

        ArrayList<Integer> heartRates = new ArrayList<Integer>();
        heartRates.add(100);
        heartRates.add(115);
        heartRates.add(120);
        heartRates.add(125);
        heartRates.add(130);

        ArrayList<LocalDateTime> localTimes = new ArrayList<LocalDateTime>();
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 10));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 15));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 25));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 23, 59, 59));
        localTimes.add(LocalDateTime.of(2018, 9, 11, 00, 00, 00));

        ArrayList<CoordinateData> coordinateList = new ArrayList<CoordinateData>();
        coordinateList.add(new CoordinateData(30.2553368,-97.83891084,239.5));
        coordinateList.add(new CoordinateData(30.25499189,-97.83913958,239));
        coordinateList.add(new CoordinateData(30.25469617,-97.83931962,239));
        coordinateList.add(new CoordinateData(30.2541537,-97.83977501,239.5));
        coordinateList.add(new CoordinateData(30.26868423,-97.83252265,202));

        RunData testData = new RunData("testDistance", DataType.RUN, localTimes, coordinateList, heartRates, testUser);
        testUser.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        testUser.getUserActivities().insertActivityInGivenList(0, testData);


    }

    @Test
    public void testGetAllDateTimes() throws Exception {

        ArrayList<LocalDateTime> localTimes = new ArrayList<LocalDateTime>();
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 10));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 15));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 25));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 23, 59, 59));
        localTimes.add(LocalDateTime.of(2018, 9, 11, 00, 00, 00));

        assertEquals((testUser.getUserActivities().getAllData().get(0).getAllDateTimes()), localTimes);

    }

    @Test
    public void testGetConsumedCalories() throws Exception {

        //System.out.println(testUser.getUserActivities().getAllData().get(0).getConsumedCalories());
        assertTrue((testUser.getUserActivities().getAllData().get(0).getConsumedCalories()) == 19403.08347952518);

    }

    @Test
    public void testGetConsumedCaloriesBetweenPoints() throws Exception {

        //System.out.println(testUser.getUserActivities().getAllData().get(0).getConsumedCaloriesBetweenPoints());

        ArrayList<Double> testCalories = new ArrayList<Double>();
        testCalories.add(1.2408779477374121);
        testCalories.add(2.9341220522625875);
        testCalories.add(19398.57737014022);
        testCalories.add(0.3311093849585724);

        assertEquals(testUser.getUserActivities().getAllData().get(0).getConsumedCaloriesBetweenPoints(), testCalories);
    }

    @Test
    public void testDistanceDifference() throws Exception {

        CoordinateDataDifference distanceDifference = new CoordinateDataDifference(testUser.getUserActivities().getAllData().get(0).getCoordinatesArrayList().get(0), (testUser.getUserActivities().getAllData().get(0).getCoordinatesArrayList().get(1)));
        Double distanceTester = testUser.getUserActivities().getAllData().get(0).getDistanceCovered();
        //System.out.println(distanceTester);
        //System.out.println(distanceDifference.getDistanceDifference());
    }

    @Test
    public void equalsNewData() {
    }

    @Test
    public void getIsGraphable() {
        Boolean graphableTester = testUser.getUserActivities().getAllData().get(0).getIsGraphable();
        assertTrue(graphableTester);
    }

    @Test
    public void getTitle() {

        //System.out.println("testDistance" + testUser.getUserActivities().getAllData().get(0).getTitle());
        assertEquals("testDistance", testUser.getUserActivities().getAllData().get(0).getTitle());
    }

}
