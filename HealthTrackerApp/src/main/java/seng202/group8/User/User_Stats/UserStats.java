package seng202.group8.User.User_Stats;

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
     *Adders for each arrayList to add records
     */

    public void addUserFatToMuscleRecords(FatToMuscleRecord record) {
        userFatToMuscleRecords.add(record);
    }

    public void addUserWeightRecords(WeightRecord record) {
        userWeightRecords.add(record);
    }

    public void addUserBMITypeRecords(BMITypeRecord record) {
        userBMITypeRecords.add(record);
    }

    public void addUserStressLevelRecords(StressLevelRecord record) {
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
