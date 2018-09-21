package java_sqlite_db;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.util.ArrayList;

public class SQLiteJDBCTest {

    private SQLiteJDBC database;
    private User testUser;

    @Before
    public void setup() {
        try {
            User testUser = new User("Jack", 19, 73.0, 183.0, Sex.MALE);
            ActivityList activityList = new ActivityList("Test activity List");
            Parser parserTest = new Parser("seng202_2018_example_data_clean.csv", testUser);
            parserTest.parseFile();
            ArrayList<Data> data = new ArrayList<>(parserTest.getDataList());
            for (Data d : data) {
                System.out.println(d.getTitle());
                activityList.insertActivity(d);
            }
            testUser.getUserActivities().insertActivityList(activityList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown() {
        testUser = null;
    }

    @Test
    public void testSaveUser() {

    }

    @Test
    public void testRetrieveUser() {

    }

    @Test
    public void testUpdateWithListOfData() {

    }

    @Test
    public void testInsertActivityList() {

    }

}
