package seng202.group8.data_entries;

import java.util.ArrayList;

public class RunData extends NotAssistedSportsData{
    public RunData(String newTitle, String activityType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
        super(newTitle, activityType, newCoordinatesList, newHeartRateList);
    }

    double getConsumedCalories(String activityType) {
        return 0;
    }
}
