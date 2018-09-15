package seng202.group8.Parser_Tests;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.parser.*;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private Parser parserTest1;
    private Parser parserTest2;
    private Parser parserTest3;
    private User userTest;

    @Before
    public void setUp() throws Exception {
        userTest = new User("Sam", 20, 72.0, 167.0, Sex.MALE);
        parserTest1 =  new Parser("/home/cosc/student/sgv15/Documents/Seng/Winded_team8/SENG202Team8_2/seng202_2018_example_data_clean.csv", userTest);
        //parserTest2 =  new Parser("/home/cosc/student/sgv15/Documents/Seng/Winded_team8/SENG202Team8_2/seng202_2018_example_data.csv", userTest);
    }

    @After
    public void tearDown() {

        parserTest1 = null;
        parserTest2 = null;
    }

    @Test
    public void noErrors() {
        List<String> testListTitle = Stream.of("walk in the woods", "run around the block", "walk in the woods", "longer run", "walk with dog", "hike in the mountains", "run in the city", "walk in the mountains", "walk in the city", "hiking with friends", "some outdoor exercise running", "exercising with friends bike").collect(Collectors.toList());
        List<DataType> testListType = Stream.of(DataType.WALK, DataType.RUN, DataType.WALK, DataType.RUN, DataType.WALK, DataType.HIKE, DataType.RUN, DataType.WALK, DataType.WALK, DataType.HIKE, DataType.RUN, DataType.BIKE).collect(Collectors.toList());
        List<Integer> testListNum = Stream.of(33, 7, 33, 294, 4, 33, 5, 8, 8, 318, 291, 101).collect(Collectors.toList());
        ArrayList<Data> dataTest = new ArrayList<>(parserTest1.getDataList());
        for (int i = 0; i < dataTest.size(); i++) {
            //System.out.println(dataTest.get(i).getCoordinatesArrayList().size());
            //System.out.println(dataTest.get(i).getTitle());
            assertEquals(dataTest.get(i).getTitle(), testListTitle.get(i));
            assertEquals(dataTest.get(i).getDataType(), testListType.get(i));
            //System.out.println(dataTest.get(i).getCoordinatesArrayList().size());
            //System.out.println(testListNum.get(i));
            assertEquals(dataTest.get(i).getCoordinatesArrayList().size(), (int) testListNum.get(i));
            assertEquals(dataTest.get(i).getAllDateTimes().size(), (int) testListNum.get(i));
            assertEquals(dataTest.get(i).getHeartRateList().size(), (int) testListNum.get(i));
        }
    }

    @Test
    public void notCSV() throws Exception {
        int finished = 0;
        try {
            Parser parserTest = new Parser("seng202_2018_example_data_clean.txt", userTest);
        } catch (NotCSVError e) {
            finished = 1;
        }
        assertEquals(finished, 1);
    }

    @Test
    public void wrongFileName() throws Exception {
        int finished = 0;
        try {
            Parser parserTest = new Parser("Fake.csv", userTest);
        } catch (FileNotFoundError e) {
            finished = 1;
        }
        assertEquals(finished, 1);
    }

    @Test
    public void noType() throws Exception {
        int finished = 0;
        try {
            parserTest2 =  new Parser("/home/cosc/student/sgv15/Documents/Seng/Winded_team8/SENG202Team8_2/seng202_2018_example_data.csv", userTest);;
        } catch (noTypeError e) {
            finished = 1;
        }
        assertEquals(finished, 1);
    }

}
