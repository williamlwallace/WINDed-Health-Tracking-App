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
        StressLevelType stress = StressLevelType.PERCEIVED;
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
    public void checkDateOFWeightRecord() {
        weightRecord.createDate();
        Date date = new Date();
        assertEquals(weightRecord.getDate(), date);
    }
}
