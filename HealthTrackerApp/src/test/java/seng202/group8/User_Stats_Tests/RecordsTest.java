package seng202.group8.User_Stats_Tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.User.BMIType;
import seng202.group8.User.StressLevelType;
import seng202.group8.User.User_Stats.BMITypeRecord;
import seng202.group8.User.User_Stats.FatToMuscleRecord;
import seng202.group8.User.User_Stats.StressLevelRecord;
import seng202.group8.User.User_Stats.WeightRecord;

import static org.junit.Assert.*;
import static seng202.group8.User.BMIType.FIT;
import static seng202.group8.User.BMIType.LIGHT;
import static seng202.group8.User.StressLevelType.NOTPERCEIVED;
import static seng202.group8.User.StressLevelType.PERCEIVED;

import java.util.Date;

public class RecordsTest {

    private WeightRecord weightRecord;
    private BMITypeRecord bmiRecord;
    private FatToMuscleRecord fatRecord;
    private StressLevelRecord stressRecord;


    @Before
    public void recordsSetup() {
        weightRecord = new WeightRecord(10.5);
        BMIType bmi = BMIType.FIT;
        bmiRecord = new BMITypeRecord(bmi);
        fatRecord = new FatToMuscleRecord(55.99);
        StressLevelType stress = PERCEIVED;
        stressRecord = new StressLevelRecord(stress);
    }

    @After
    public void finish() {
        weightRecord = null;
        bmiRecord = null;
        fatRecord = null;
        stressRecord = null;
    }

    @Test
    public void correctWeightRecord() {
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
    public void checkDateOfWeightRecord() {
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
    public void checkDateOfFatRecord() {
        fatRecord.createDate();
        Date date = new Date();
        assertEquals(fatRecord.getDate(), date);
    }

    @Test
    public void correctBMIRecord() {
        BMIType testbmi = FIT;
        assertEquals(bmiRecord.getBmi(), testbmi);
    }

    @Test
    public void changeBMIRecord() {
        bmiRecord.setBmi(LIGHT);
        BMIType bmi = LIGHT;
        assertEquals(bmiRecord.getBmi(), bmi);
    }

    @Test
    public void checkDateOfBMIRecord() {
        bmiRecord.createDate();
        Date date = new Date();
        assertEquals(bmiRecord.getDate(), date);
    }

    @Test
    public void correctStressRecord() {
        StressLevelType stress = PERCEIVED;
        assertEquals(stressRecord.getStress(), stress);
    }

    @Test
    public void changeStressRecord() {
        stressRecord.setStress(NOTPERCEIVED);
        StressLevelType stress = NOTPERCEIVED;
        assertEquals(stressRecord.getStress(), stress);
    }

    @Test
    public void checkDateOfStressRecord() {
        stressRecord.createDate();
        Date date = new Date();
        assertEquals(stressRecord.getDate(), date);
    }
}
