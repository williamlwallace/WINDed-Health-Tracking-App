package seng202.group8.data_entries_tests;

import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.*;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

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

public class DataTests {

    private User testUser;
    //private ArrayList<LocalDateTime> localDateTimes;


    @Before
    public void setUp() throws Exception {
        testUser = new User("Clarke", 20, 72.0, 167.0, Sex.MALE);
        ArrayList<Date> testTimes;
        Date timeOne;

        String stringTimeOne = "00:00:00";
        DateFormat dateTimeFormatter = new SimpleDateFormat("hh:mm:ss");
        Date testTime = new Date(00-00-00);
        String testTitle = "Testing Title.";

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
        //coordinateList.add(new CoordinateData(30.26868423,-97.83252265,202));

        RunData testData = new RunData("testDistance", DataType.WALK, localTimes, coordinateList, heartRates, testUser);
        testUser.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        testUser.getUserActivities().insertActivityInGivenList(0, testData);


    }

    @Test
    public void testGetAllDateTimes() throws Exception {

//        Parser parserTest = new Parser("/home/cosc/student/cmc280/Desktop/SENG202_Assignment/SENG202Team8/seng202_2018_example_data_clean.csv", testUser);
//        ArrayList<Data> dataList = parserTest.getDataList();
//
//        for (int i = 0; i < dataList.size(); i++) {
//            testUser.getUserActivities().insertActivityInGivenList(0, dataList.get(i));
//            //System.out.println(dataList.get(i).getHeartRateList());
//            System.out.println(dataList.get(i).getAllDateTimes());
//        }

        ArrayList<LocalDateTime> localTimes = new ArrayList<LocalDateTime>();
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 10));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 15));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 25));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 23, 59, 59));
        localTimes.add(LocalDateTime.of(2018, 9, 11, 00, 00, 00));

        assertEquals((testUser.getUserActivities().getAllData().get(0).getAllDateTimes()), localTimes);


        //System.out.println(testTime);

        //Data tester = new Data(testTitle, dataType, ArrayList< LocalDateTime > newDateTimes, ArrayList< CoordinateData > newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser)
        //testTime.setTime("00:00:00");
//        {
//            try {
//                testTime = dateTimeFormatter.parse(stringTimeOne);
//                testTimes.add(testTime);
//                System.out.println(testTimes);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Test
    public void testWalkCalories() throws Exception {

        //System.out.println(testUser.getUserActivities().getAllData().get(0).getConsumedCalories());
        assertTrue((testUser.getUserActivities().getAllData().get(0).getConsumedCalories()) == 19403.08347952518);

    }

    @Test
    public void testDistanceDifference() throws Exception {

        CoordinateDataDifference distanceDifference = new CoordinateDataDifference(testUser.getUserActivities().getAllData().get(0).getCoordinatesArrayList().get(0), (testUser.getUserActivities().getAllData().get(0).getCoordinatesArrayList().get(1)));
        Double distanceDoodad = testUser.getUserActivities().getAllData().get(0).getDistanceCovered();
        System.out.println(distanceDoodad);
        System.out.println(distanceDifference.getDistanceDifference());
    }



    //timeOne.setTime("00:00:00");
    /* void setTime(Date date); */
}
