package java_sqlite_db;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.Data;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SQLiteJDBCTest {

    private SQLiteJDBC database;
    private User testUser;
    private ArrayList<Data> dataListToBeInserted;


    private static Boolean compareData(Data firstData, Data secondData) {
        Boolean areEqual = true;
        ArrayList<Integer> firstHeartRateList = firstData.getHeartRateList();
        ArrayList<CoordinateData> firstCoordinateList = firstData.getCoordinatesArrayList();
        ArrayList<LocalDateTime> firstTimes = firstData.getAllDateTimes();
        ArrayList<Integer> secondHeartRateList = secondData.getHeartRateList();
        ArrayList<CoordinateData> secondCoordinateList = secondData.getCoordinatesArrayList();
        ArrayList<LocalDateTime> secondTimes = secondData.getAllDateTimes();

        for (int i = 0; i < firstHeartRateList.size(); i++) {
            if (!(firstHeartRateList.get(i).equals(secondHeartRateList.get(i)))) {
                areEqual = false;
            }
        }

        for (int i = 0; i < firstCoordinateList.size(); i++) {
            Double firstLatitude = firstCoordinateList.get(i).getLatitude();
            Double secondLatitude = secondCoordinateList.get(i).getLatitude();
            if (!(firstLatitude.equals(secondLatitude))) {
                System.out.println("Coordinate mismatch");
                areEqual = false;
            }
        }

        for (int i = 0; i < firstTimes.size(); i++) {
            if(!(firstTimes.get(i).isEqual(secondTimes.get(i)))) {
                System.out.println("Time mismatch");
                areEqual = false;
            }
        }

        return areEqual;

    }


    @Before
    public void setup() {
        try {
            testUser = new User("Jack", 19, 73.0, 183.0, Sex.MALE);
            ActivityList activityList = new ActivityList("Test activity List");
            Parser parserTest =  new Parser("src/main/resources/resources/views/test_resources/seng202_2018_example_data_clean.csv", testUser);
            parserTest.parseFile();
            dataListToBeInserted = parserTest.getDataList();
            for (Data d : dataListToBeInserted) {
                System.out.println(d.getTitle());
                activityList.insertActivity(d);
            }
            testUser.getUserActivities().insertActivityList(activityList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        database = new SQLiteJDBC();
        database.setIsTest(true);
    }

    @After
    public void tearDown() {
        database.deleteAllData();
        database.setIsTest(false);
        testUser = null;
        database = null;
        dataListToBeInserted = null;
    }

    @Test
    public void testSaveUser() {
        database.saveUser(testUser, 1);
        User retrievedUser = database.retrieveUser(1);
        Boolean isEqualUser = (testUser.getName().equals(retrievedUser.getName())
                            && testUser.getAge().equals(retrievedUser.getAge())
                            && testUser.getWeight().equals(retrievedUser.getWeight())
                            && testUser.getHeight().equals(retrievedUser.getHeight())
                            && testUser.getSex().equals(retrievedUser.getSex()));
        assertTrue(isEqualUser);
    }

    @Test
    public void testRetrieveUserNone() {
        User retrievedUser = database.retrieveUser(1);
        assertNull(retrievedUser);
    }


    @Test
    public void testUpdateWithListOfData() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), database.getStringFromLocalDateTime(database.convertToLocalDateTimeViaInstant(insertedList.getCreationDate())), 1);
        database.updateWithListOfData(dataListToBeInserted, insertedList.getTitle(), insertedList.getCreationDate(), 1);
        User retrievedUser = database.retrieveUser(1);
        ActivityList retrievedList = retrievedUser.getUserActivities().getActivityListCollection().get(0);
        Boolean isEqualData = true;
        Data retrievedData;
        Data originalData;
        for (int i = 0; i < insertedList.getActivityList().size(); i++) {
            retrievedData = retrievedList.getActivity(i);
            originalData = insertedList.getActivity(i);
            if (!compareData(originalData, retrievedData)) {
                isEqualData = false;
            }
        }
        assertTrue(isEqualData);
    }

    @Test
    public void testInsertActivityList() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), database.getStringFromLocalDateTime(database.convertToLocalDateTimeViaInstant(insertedList.getCreationDate())), 1);
        User retrievedUser = database.retrieveUser(1);
        ActivityList retrievedActivityList = retrievedUser.getUserActivities().getActivityListCollection().get(0);
        assertEquals(insertedList.getCreationDate().toString(), retrievedActivityList.getCreationDate().toString());
        assertEquals(insertedList.getTitle(), retrievedActivityList.getTitle());

    }

}
