package seng202.group8.User.User_Stats;

import seng202.group8.User.BMIType;
import seng202.group8.User.StressLevelType;

import java.util.ArrayList;

public class UserStats {
    /**
     *Getters for arrayLists for records
     */

    public static ArrayList getUserWeightRecords() {
        return userWeightRecords;
    }

    public static ArrayList getUserFatToMuscleRecords() {
        return userFatToMuscleRecords;
    }

    public static ArrayList getUserBMITypeRecords() {
        return userBMITypeRecords;
    }

    public static ArrayList getUserStressLevelRecords() {
        return userStressLevelRecords;
    }

    /**
     *Adders for each arrayList to create the records and add them to the arrayLists
     * Each function takes the input needed for the record specified
     */

    public static void addUserFatToMuscleRecords(Double fatToMuscle) {
        FatToMuscleRecord record = new FatToMuscleRecord(fatToMuscle);
        userFatToMuscleRecords.add(record);
    }

    public static void addUserWeightRecords(Double weight) {
        WeightRecord record = new WeightRecord(weight);
        userWeightRecords.add(record);
    }

    public static void addUserBMITypeRecords(BMIType bmi) {
        BMITypeRecord record = new BMITypeRecord(bmi);
        userBMITypeRecords.add(record);
    }

    public static void addUserStressLevelRecords(StressLevelType stress) {
        StressLevelRecord record = new StressLevelRecord(stress);
        userStressLevelRecords.add(record);
    }


    /**
     *Initial Variables
     */
    private static ArrayList userWeightRecords = new ArrayList<WeightRecord>();
    private static ArrayList userFatToMuscleRecords = new ArrayList<FatToMuscleRecord>();
    private static ArrayList userBMITypeRecords = new ArrayList<BMITypeRecord>();
    private static ArrayList userStressLevelRecords = new ArrayList<StressLevelRecord>();
}
