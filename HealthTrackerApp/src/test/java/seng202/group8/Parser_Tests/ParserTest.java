package seng202.group8.Parser_Tests;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import seng202.group8.data_entries.Data;
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
        List<String> testList = Stream.of("walk in the woods", "run around the block", "walk in the woods", "longer run", "walk with dog", "hike in the mountains", "run in the city", "walk in the mountains", "walk in the city", "hiking with friends", "some outdoor exercise running", "exercising with friends bike").collect(Collectors.toList());
        ArrayList<Data> dataTest = parserTest1.getDataList();
        for (int i = 0; i < dataTest.size(); i++) {
            //System.out.println(dataTest.get(i).getTitle());
            assertEquals(dataTest.get(i).getTitle(), testList.get(i));
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
