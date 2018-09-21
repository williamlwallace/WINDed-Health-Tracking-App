package java_sqlite_db;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

public class SQLiteJDBCTest {

    private SQLiteJDBC database;
    User testUser = new User("Jack", 19, 73.0, 183.0, Sex.MALE);


    @Before
    public void setup() {

    }
}
