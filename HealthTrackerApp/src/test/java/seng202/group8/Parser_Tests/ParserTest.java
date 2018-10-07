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
import static org.junit.Assert.assertTrue;

public class ParserTest {
    private Parser parserTest1;
    private Parser parserTest2;
    private Parser parserTest3;
    private User userTest;

    @Before
    public void setUp() throws Exception {
        userTest = new User("Sam", 20, 72.0, 167.0, Sex.MALE);
        parserTest1 = new Parser("src/main/resources/resources/views/test_resources/seng202_2018_example_data_clean.csv", userTest);
        parserTest1.parseFile();
        parserTest2 = new Parser("src/main/resources/resources/views/test_resources/seng202_2018_example_data.csv", userTest);
        parserTest3 = new Parser("src/main/resources/resources/views/test_resources/seng202_2018_example_data_errors.csv", userTest);
    }

    @After
    public void tearDown() {

        parserTest1 = null;
        parserTest2 = null;
    }

    @Test
    public void getFilename() {
        assertEquals(parserTest1.getFilename(), "src/main/resources/resources/views/test_resources/seng202_2018_example_data_clean.csv");
    }

    @Test
    public void getUser() {
        assertEquals(parserTest1.getUser(), userTest);
    }

    /**
     * Tests to make sure a csv file with no errors compiles correctly. Tests that the titles and types stored are correct, as well as the right amount of lines are correct.
     */
    @Test
    public void noErrors() {
        List<String> testListTitle = Stream.of("walk in the woods", "run around the block", "walk in the woods", "longer run", "walk with dog", "hike in the mountains", "run in the city", "walk in the mountains", "walk in the city", "hiking with friends", "some outdoor exercise running", "exercising with friends bike").collect(Collectors.toList());
        List<DataType> testListType = Stream.of(DataType.WALK, DataType.RUN, DataType.WALK, DataType.RUN, DataType.WALK, DataType.HIKE, DataType.RUN, DataType.WALK, DataType.WALK, DataType.HIKE, DataType.RUN, DataType.BIKE).collect(Collectors.toList());
        List<Integer> testListNum = Stream.of(33, 7, 33, 294, 4, 33, 5, 8, 8, 318, 291, 101).collect(Collectors.toList());
        ArrayList<Data> dataTest = new ArrayList<>(parserTest1.getDataList());
        for (int i = 0; i < dataTest.size(); i++) {
            assertEquals(dataTest.get(i).getTitle(), testListTitle.get(i));
            assertEquals(dataTest.get(i).getDataType(), testListType.get(i));
            assertEquals(dataTest.get(i).getCoordinatesArrayList().size(), (int) testListNum.get(i));
            assertEquals(dataTest.get(i).getAllDateTimes().size(), (int) testListNum.get(i));
            assertEquals(dataTest.get(i).getHeartRateList().size(), (int) testListNum.get(i));
        }
//        assertTrue(true);
    }

    @Test
    public void multiErrors() throws Exception {
        try {
            parserTest3.parseFile();
        } catch(DataMissingError e) {
            //System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "Activity 'walk in the woods' is corrupt on line/lines: (Heart-rate or Coordinates) 2, (Incorrect number of lines) 3, (date) 8, (date) 15");
        }
    }

    /**
     * Checks that an error is thrown when a non CSV file is sent in.
     * @throws Exception
     */
    @Test
    public void notCSV() throws Exception {
        int finished = 0;
        try {
            Parser parserTest = new Parser("seng202_2018_example_data_clean.txt", userTest);
            parserTest.parseFile();
        } catch (NotCSVError e) {
            finished = 1;
        }
        assertEquals(finished, 1);
//        assertTrue(true);
    }

    /**
     * Checks that an error is thrown when a non existing file is sent in.
     * @throws Exception
     */
    @Test
    public void wrongFileName() throws Exception {
        int finished = 0;
        try {
            Parser parserTest = new Parser("Fake.csv", userTest);
            parserTest.parseFile();
        } catch (FileNotFoundError e) {
            finished = 1;
        }
        assertEquals(finished, 1);
    }

    /**
     * Checks that an error is thrown when a actitvity name doesn't match any of the key words
     * @throws Exception
     */
    @Test
    public void noType() throws Exception {
        int finished = 0;
        try {
            parserTest2.parseFile();
        } catch (noTypeError e) {
            finished = 1;
        }
        assertEquals(finished, 1);
//        assertTrue(true);
    }

    /**
     * Checks that the add() function works and you can add key words to trip the parser. This then checks that the first error is fixed when a new keyword is added.
     * @throws Exception
     */
    @Test
    public  void noTypeFix() throws Exception {
        try {
            parserTest2.parseFile();
        } catch (noTypeError e) {
            assertEquals(e.getMessage(), "The activity 'some outdoor exercise' doesn't match any of the activity types.");
        }
        parserTest2.add("exercise", 3, false);
        try {
            parserTest2.parseFile();
        } catch (noTypeError e) {
            assertEquals(e.getMessage(), "The activity 'exercising with friends' doesn't match any of the activity types.");
        }
    }

}
