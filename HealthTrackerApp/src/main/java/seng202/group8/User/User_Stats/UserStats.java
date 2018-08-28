package seng202.group8.User.User_Stats;

import seng202.group8.User.BMIType;
import seng202.group8.User.StressLevelType;

import java.util.ArrayList;

public class UserStats {
    /**
     *Getters for arrayLists for records
     */

    public ArrayList getUserWeightRecords() {
        return userWeightRecords;
    }

    public ArrayList getUserFatToMuscleRecords() {
        return userFatToMuscleRecords;
    }

    public ArrayList getUserBMITypeRecords() {
        return userBMITypeRecords;
    }

    public ArrayList getUserStressLevelRecords() {
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
    public ArrayList userWeightRecords = new ArrayList<WeightRecord>();
    public ArrayList userFatToMuscleRecords = new ArrayList<FatToMuscleRecord>();
    public ArrayList userBMITypeRecords = new ArrayList<BMITypeRecord>();
    public ArrayList userStressLevelRecords = new ArrayList<StressLevelRecord>();
}
