package seng202.group8.User_Stats_Tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.User.BMIType;
import seng202.group8.User.StressLevelType;
import seng202.group8.User.User_Stats.*;

import static org.junit.Assert.*;
import static seng202.group8.User.BMIType.NORMAL;
import static seng202.group8.User.BMIType.LIGHT;
import static seng202.group8.User.StressLevelType.NOTPERCEIVED;
import static seng202.group8.User.StressLevelType.PERCEIVED;

import java.util.Date;
import java.util.ArrayList;

public class RecordsTest {

    private WeightRecord weightRecord;
    private BMITypeRecord bmiRecord;
    private FatToMuscleRecord fatRecord;
    private StressLevelRecord stressRecord;


    @Before
    public void recordsSetup() {
        weightRecord = new WeightRecord(10.5);
        bmiRecord = new BMITypeRecord(NORMAL);
        fatRecord = new FatToMuscleRecord(55.99);
        stressRecord = new StressLevelRecord(PERCEIVED);
    }

    @After
    public void finish(){
        weightRecord = null;
        bmiRecord = null;
        fatRecord = null;
        stressRecord = null;
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
    public void correctFatRecord() {
        Double testFat = 55.99;
        assertEquals(fatRecord.getFatToMuscle(), testFat);
    }

    @Test
    public void changeFatRecord() {
        fatRecord.setFatToMuscle(100.00);
        Double testFat = 100.00;
        assertEquals(fatRecord.getFatToMuscle(), testFat);
    }

    @Test
    public void checkDateOfFatRecord(){
        fatRecord.createDate();
        Date date = new Date();
        assertEquals(fatRecord.getDate(), date);
    }

    @Test
    public void correctBMIRecord(){
        assertEquals(bmiRecord.getBmi(), NORMAL);
    }

    @Test
    public void changeBMIRecord()  {
        bmiRecord.setBmi(LIGHT);
        assertEquals(bmiRecord.getBmi(), LIGHT);
    }

    @Test
    public void checkDateOfBMIRecord(){
        bmiRecord.createDate();
        Date date = new Date();
        assertEquals(bmiRecord.getDate(), date);
    }

    @Test
    public void correctStressRecord() {
        assertEquals(stressRecord.getStress(), PERCEIVED);
    }

    @Test
    public void changeStressRecord() {
        stressRecord.setStress(NOTPERCEIVED);
        assertEquals(stressRecord.getStress(), NOTPERCEIVED);
    }

    @Test
    public void checkDateOfStressRecord() {
        stressRecord.createDate();
        Date date = new Date();
        assertEquals(stressRecord.getDate(), date);
    }

    @Test
    public void checkWeightList() {
        Double weight = 12.9;
        UserStats.addUserWeightRecords(weight);
        ArrayList<WeightRecord> list = UserStats.getUserWeightRecords();
        assertEquals(list.get(0).getWeight(), weight);
    }

    @Test
    public void checkBMIList() {
        UserStats.addUserBMITypeRecords(NORMAL);
        ArrayList<BMITypeRecord> list = UserStats.getUserBMITypeRecords();
        assertEquals(list.get(0).getBmi(), NORMAL);
    }

    @Test
    public void checkFatList() {
        Double fat = 16.75;
        UserStats.addUserFatToMuscleRecords(fat);
        ArrayList<FatToMuscleRecord> list = UserStats.getUserFatToMuscleRecords();
        assertEquals(list.get(0).getFatToMuscle(), fat);
    }

    @Test
    public void checkStressList() {
        UserStats.addUserStressLevelRecords(PERCEIVED);
        ArrayList<StressLevelRecord> list = UserStats.getUserStressLevelRecords();
        assertEquals(list.get(0).getStress(), PERCEIVED);
    }
}
