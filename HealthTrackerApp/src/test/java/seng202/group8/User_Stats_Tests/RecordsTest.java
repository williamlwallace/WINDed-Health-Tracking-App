package seng202.group8.User_Stats_Tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.user.BMI;
import seng202.group8.user.user_stats.*;

import static org.junit.Assert.*;
import static seng202.group8.user.BMIType.NORMAL;
import static seng202.group8.user.BMIType.LIGHT;
import static seng202.group8.user.StressLevelType.NOTPERCEIVED;
import static seng202.group8.user.StressLevelType.PERCEIVED;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class RecordsTest {

    private WeightRecord weightRecord;
    private BMITypeRecord bmiRecord;
    private UserStats userStats = new UserStats();


    @Before
    public void recordsSetup() {
        weightRecord = new WeightRecord(10.5);
        BMI bmi = new BMI(20.0);
        bmiRecord = new BMITypeRecord(bmi);
    }

    @After
    public void finish(){
        weightRecord = null;
        bmiRecord = null;
    }

    @Test
    public void correctWeightRecord(){
        Double testWeight = 10.5;
        assertEquals(weightRecord.getWeight(), testWeight);
    }

    @Test
    public void changeWeightRecord() {
        weightRecord.setWeight(20.99);
        Double testWeight = 20.99;
        assertEquals(weightRecord.getWeight(), testWeight);
    }

    @Test
    public void checkDateOfWeightRecord(){
        weightRecord.createDate();
        Date date = new Date();
        assertEquals(weightRecord.getDate(), date);
    }

    @Test
    public void correctBMIRecord(){
        assertEquals(bmiRecord.getBmi().getBMICategory(), NORMAL);
    }

    @Test
    public void changeBMIRecord()  {
        BMI newBMI = new BMI(17.0);
        bmiRecord.setBmi(newBMI);
        assertEquals(LIGHT, bmiRecord.getBmi().getBMICategory());
    }

    @Test
    public void checkDateOfBMIRecord(){
        bmiRecord.createDate();
        Date date = new Date();
        assertEquals(bmiRecord.getDate(), date);
    }

    @Test
    public void checkWeightList() {
        Double weight = 12.9;
        userStats.addUserWeightRecords(weight);
        ArrayList<WeightRecord> list = userStats.getUserWeightRecords();
        assertEquals(list.get(0).getWeight(), weight);
    }

    @Test
    public void checkBMIList() {
        BMI bmi = new BMI(20.0);
        userStats.addUserBMITypeRecords(bmi);
        ArrayList<BMITypeRecord> list = userStats.getUserBMITypeRecords();
        assertEquals(NORMAL, list.get(0).getBmi().getBMICategory());
    }
}
