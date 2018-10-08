package java_sqlite_db;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.parser.Parser;
import seng202.group8.services.goals_service.GoalsService;
import seng202.group8.services.goals_service.goal_types.ActivityGoal;
import seng202.group8.services.goals_service.goal_types.FrequencyGoal;
import seng202.group8.services.goals_service.goal_types.GoalType;
import seng202.group8.services.goals_service.goal_types.WeightLossGoal;
import seng202.group8.user.BMI;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.BMITypeRecord;
import seng202.group8.user.user_stats.Sex;
import seng202.group8.user.user_stats.UserStats;
import seng202.group8.user.user_stats.WeightRecord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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
    public void testKeywords() {
        // checking if added a keyword works.
        database.addParserKeyword(testUser.getUserID(), "test", 1);
        ArrayList<String> keywords = database.getKeyWordsByType(testUser.getUserID(), 1);
        for (String s : keywords) {
            assertEquals(s, "test");
        }
        // Checking now if duplicate keywords arn't accepted
        assertEquals(false, database.addParserKeyword(testUser.getUserID(), "test", 1));
        assertEquals(false, database.addParserKeyword(testUser.getUserID(), "testing", 1));
        assertEquals(false, database.addParserKeyword(testUser.getUserID(), "tes", 1));
        keywords = database.getKeyWordsByType(testUser.getUserID(), 1);
        for (String s : keywords) {
            assertEquals(s, "test");
        }
        // Testing if keyword can be deleted.
        database.deleteParserKeyword(testUser.getUserID(), "test");
        keywords = database.getKeyWordsByType(testUser.getUserID(), 1);
        assertEquals(0, keywords.size());
    }


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
    public void testUpdateWithListOfData() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), insertedList.getCreationDate(), 1);
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
        database.insertActivityList(insertedList.getTitle(), insertedList.getCreationDate(), 1);
        User retrievedUser = database.retrieveUser(1);
        ActivityList retrievedActivityList = retrievedUser.getUserActivities().getActivityListCollection().get(0);
        assertEquals(insertedList.getCreationDate().toString(), retrievedActivityList.getCreationDate().toString());
        assertEquals(insertedList.getTitle(), retrievedActivityList.getTitle());

    }

    @Test
    public void testDeleteUser() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), insertedList.getCreationDate(), 1);
        database.updateWithListOfData(dataListToBeInserted, insertedList.getTitle(), insertedList.getCreationDate(), 1);
        database.saveUser(testUser, 2);
        database.deleteUser(1);
        ArrayList<User> userList = database.retrieveAllUsers();
        assertEquals(1, userList.size());
    }

    @Test
    public void testRecordStorage() {
        Boolean equal = true;
        UserStats userStats = new UserStats();
        userStats.addUserWeightRecords(65.0);
        userStats.addUserBMITypeRecords(new BMI(19.0));
        testUser.setUserStats(userStats);
        WeightRecord weightRecord = new WeightRecord(60.0);
        weightRecord.setDate(LocalDateTime.now().plusMinutes(5));
        testUser.getUserStats().getUserWeightRecords().add(weightRecord);
        BMITypeRecord bmiTypeRecord = new BMITypeRecord(new BMI(15.0));
        bmiTypeRecord.setDate(LocalDateTime.now().plusMinutes(5));
        testUser.getUserStats().getUserBMITypeRecords().add(bmiTypeRecord);

        database.saveUser(testUser, 1);
        User retrievedUser = database.retrieveUser(1);
        for (int i = 0; i < testUser.getUserStats().getUserWeightRecords().size(); i+=1) {
            if (!testUser.getUserStats().getUserWeightRecords().get(i).getWeight().equals(retrievedUser.getUserStats().getUserWeightRecords().get(i).getWeight())) {
                equal = false;
            }
        }

        for (int i = 0; i < testUser.getUserStats().getUserBMITypeRecords().size(); i++) {
            if (!testUser.getUserStats().getUserBMITypeRecords().get(i).getBmi().getBMIValue().equals(retrievedUser.getUserStats().getUserBMITypeRecords().get(i).getBmi().getBMIValue())) {
                equal = false;
            }
        }
        assertTrue(equal);
    }

    @Test
    public void testGoalsStorage() {
        Boolean equal = true;
        ActivityGoal activityGoal = new ActivityGoal(testUser, "", GoalType.ActivityGoal, DataType.WALK, 200.0, LocalDateTime.now());
        activityGoal.setStartDate(new Date(0));
        FrequencyGoal frequencyGoal = new FrequencyGoal(testUser, "", GoalType.TimePerformedGoal, DataType.RUN, 3, LocalDateTime.now());
        frequencyGoal.setStartDate(new Date(0));
        WeightLossGoal weightLossGoal = new WeightLossGoal(testUser, "", GoalType.WeightLossGoal, 20.0, LocalDateTime.now());
        weightLossGoal.setStartDate(new Date(0));
        testUser.getGoalsService().getCurrentActivityGoals().add(activityGoal);
        testUser.getGoalsService().getCurrentTimesPerformedGoals().add(frequencyGoal);
        testUser.getGoalsService().getCurrentWeightLossGoals().add(weightLossGoal);
        database.saveUser(testUser, 1);
        User retrievedUser = database.retrieveUser(1);
        if (!retrievedUser.getGoalsService().getCurrentActivityGoals().get(0).getTarget().equals(testUser.getGoalsService().getCurrentActivityGoals().get(0).getTarget())) {
            equal = false;
        }
        if (!retrievedUser.getGoalsService().getCurrentTimesPerformedGoals().get(0).getTarget().equals(testUser.getGoalsService().getCurrentTimesPerformedGoals().get(0).getTarget())) {
            equal = false;
        }
        if (!retrievedUser.getGoalsService().getCurrentWeightLossGoals().get(0).getTarget().equals(testUser.getGoalsService().getCurrentWeightLossGoals().get(0).getTarget())) {
            equal = false;
        }
        assertTrue(equal);
    }

    @Test
    public void testUpdateActivity() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), insertedList.getCreationDate(), 1);
        ActivityList newList = new ActivityList("Modified List");
        Date date = new Date();
        date.setTime(1000);
        newList.setCreationDate(date);
        database.insertActivityList(newList.getTitle(), newList.getCreationDate(),1);
        database.updateWithListOfData(dataListToBeInserted, insertedList.getTitle(), insertedList.getCreationDate(), 1);

        testUser.getUserActivities().getMostCurrentActivity().setTitle("Modified Title");
        testUser.getUserActivities().getMostCurrentActivity().setDataType(DataType.HIKE);

        database.updateActivity(testUser.getUserActivities().getMostCurrentActivity(),newList);

        User retrievedUser = database.retrieveUser(1);
        Boolean equal = true;
        Data retrievedActivity = retrievedUser.getUserActivities().getActivityListCollection().get(1).getActivity(0);
        if (!retrievedActivity.getTitle().equals("Modified Title") || !retrievedActivity.getDataType().equals(DataType.HIKE)) {
            equal = false;
        }
        assertTrue(equal);
    }

    @Test
    public void testDeleteActivity() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), insertedList.getCreationDate(), 1);
        database.updateWithListOfData(dataListToBeInserted, insertedList.getTitle(), insertedList.getCreationDate(), 1);

        Data toBeDeleteddata = testUser.getUserActivities().getMostCurrentActivity();
        database.deleteActivity(toBeDeleteddata.getDataId());
        User retrievedUser = database.retrieveUser(1);

        assertTrue(!toBeDeleteddata.equals(retrievedUser.getUserActivities().getMostCurrentActivity().getDataId()));
    }

    @Test
    public void testDeleteActivityList() {
        database.saveUser(testUser, 1);
        ActivityList insertedList = testUser.getUserActivities().getActivityListCollection().get(0);
        database.insertActivityList(insertedList.getTitle(), insertedList.getCreationDate(), 1);
        database.updateWithListOfData(dataListToBeInserted, insertedList.getTitle(), insertedList.getCreationDate(), 1);

        database.deleteActivityList(insertedList.getTitle(), insertedList.getCreationDate());

        User retrievedUser = database.retrieveUser(1);

        assertTrue(retrievedUser.getUserActivities().getActivityListCollection().size() == 0);
    }


}
