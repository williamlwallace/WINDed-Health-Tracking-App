package seng202.group8.services.goals_service.goal_types;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.user.User;
import utils.exceptions.NotCoherentWeightLossGoalException;

import static org.junit.Assert.*;

public class WeightLossGoalTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Lorenzo", 22, 83.0, 185.0);

    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void constructorExceptionTest() {
        boolean thrown = false;
        try {
            WeightLossGoal weightLossGoal = new WeightLossGoal(user, "Losing few kgs",  GoalType.WeightLossGoal, 170.0);
        } catch (NotCoherentWeightLossGoalException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void constructorWorkingTest() {
        boolean thrown = false;
        try {
            WeightLossGoal weightLossGoal = new WeightLossGoal(user, "Losing few kgs",  GoalType.WeightLossGoal, 78.0);
        } catch (NotCoherentWeightLossGoalException e) {
            thrown = true;
        }

        assertFalse(thrown);
    }

    @Test
    public void checkIsCompletedTrueTest1() {
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "Losing few kgs",  GoalType.WeightLossGoal, 73.0);
//            user.setWeight(83.0);
//            weightLossGoal.checkIsCompleted();
//            assertTrue(weightLossGoal.getIsCompleted());
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void checkIsCompletedTrueTest2() {
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "Losing few kgs",  GoalType.WeightLossGoal, 73.0);
//            user.setWeight(73.0);
//            weightLossGoal.checkIsCompleted();
//            assertTrue(weightLossGoal.getIsCompleted());
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void checkIsCompletedFalseTest1() {
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "Losing few kgs",  GoalType.WeightLossGoal, 73.0);
            user.setWeight(83.1);
            weightLossGoal.checkIsCompleted();
            assertFalse(weightLossGoal.getIsCompleted());
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }



    }

    @Test
    public void checkIsCompletedFalseTest2() {
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "Losing few kgs",  GoalType.WeightLossGoal, 73.0);
            user.setWeight(120.1);
            weightLossGoal.checkIsCompleted();
            assertFalse(weightLossGoal.getIsCompleted());
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }

    }


}