package seng202.group8.user.user_stats;

import seng202.group8.user.BMIType;
import seng202.group8.user.StressLevelType;

import java.util.ArrayList;

public class UserStats {
    /**
     *Getters for arrayLists for records
     */

    public ArrayList<WeightRecord> getUserWeightRecords() {
        return userWeightRecords;
    }

    public ArrayList<FatToMuscleRecord> getUserFatToMuscleRecords() {
        return userFatToMuscleRecords;
    }

    public ArrayList<BMITypeRecord> getUserBMITypeRecords() {
        return userBMITypeRecords;
    }

    public ArrayList<StressLevelRecord> getUserStressLevelRecords() {
        return userStressLevelRecords;
    }

    /**
     *Adders for each arrayList to create the records and add them to the arrayLists
     * Each function takes the input needed for the record specified
     */

    public void addUserFatToMuscleRecords(Double fatToMuscle) {
        FatToMuscleRecord record = new FatToMuscleRecord(fatToMuscle);
        userFatToMuscleRecords.add(record);
    }

    public void addUserWeightRecords(Double weight) {
        WeightRecord record = new WeightRecord(weight);
        userWeightRecords.add(record);
    }

    public void addUserBMITypeRecords(BMIType bmi) {
        BMITypeRecord record = new BMITypeRecord(bmi);
        userBMITypeRecords.add(record);
    }

    public void addUserStressLevelRecords(StressLevelType stress) {
        StressLevelRecord record = new StressLevelRecord(stress);
        userStressLevelRecords.add(record);
    }


    /**
     *Initial Variables
     */
    private ArrayList userWeightRecords = new ArrayList<WeightRecord>();
    private ArrayList userFatToMuscleRecords = new ArrayList<FatToMuscleRecord>();
    private ArrayList userBMITypeRecords = new ArrayList<BMITypeRecord>();
    private ArrayList userStressLevelRecords = new ArrayList<StressLevelRecord>();
}
