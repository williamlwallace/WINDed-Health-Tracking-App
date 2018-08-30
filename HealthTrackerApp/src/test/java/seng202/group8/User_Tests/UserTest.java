package seng202.group8.User_Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.user.BMIType;
import seng202.group8.user.User;

import static org.junit.Assert.*;

public class UserTest {

    private User newUser;

    @Before
    public void setUp() {
        newUser = new User("Jack", 19, 70.5, 183.5);
    }

    @After
    public void tearDown() {
        newUser = null;
    }

    @Test
    public void calculateBMI() {
        assertEquals(BMIType.NORMAL, newUser.getBMICategory());
    }
}