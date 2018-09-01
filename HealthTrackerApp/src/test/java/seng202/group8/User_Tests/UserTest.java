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
    public void constructBMIValue() {
        assertEquals(20.937, newUser.getBMI().getBMIValue(), 0.001);
    }

    @Test
    public void constructBMICategory() {
        assertEquals(newUser.getBMI().getBMICategory(), BMIType.NORMAL);
    }

    @Test
    public void updateBMI() {
        newUser.updateWeight(120.0); //Calls updateBMI() for new Weight
        assertEquals(newUser.getBMI().getBMICategory(), BMIType.OBESE);
    }

}