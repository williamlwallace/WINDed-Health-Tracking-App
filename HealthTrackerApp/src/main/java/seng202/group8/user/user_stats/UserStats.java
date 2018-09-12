package seng202.group8.user.user_stats;

import seng202.group8.user.BMI;
import seng202.group8.user.StressLevelType;

import java.util.ArrayList;

public class UserStats {
    /**
     *Getters for arrayLists for records
     */

    public ArrayList<WeightRecord> getUserWeightRecords() {
        return userWeightRecords;
    }

    public ArrayList<BMITypeRecord> getUserBMITypeRecords() {
        return userBMITypeRecords;
    }

    /**
     *Adders for each arrayList to create the records and add them to the arrayLists
     * Each function takes the input needed for the record specified
     */

    public void addUserWeightRecords(Double weight) {
        WeightRecord record = new WeightRecord(weight);
        userWeightRecords.add(record);
    }

    public void addUserBMITypeRecords(BMI bmi) {
        BMITypeRecord record = new BMITypeRecord(bmi);
        userBMITypeRecords.add(record);
    }

    /**
     *Initial Variables
     */
    private ArrayList userWeightRecords = new ArrayList<WeightRecord>();
    private ArrayList userBMITypeRecords = new ArrayList<BMITypeRecord>();
}
