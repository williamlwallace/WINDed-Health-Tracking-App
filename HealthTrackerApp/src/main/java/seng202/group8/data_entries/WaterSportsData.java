package seng202.group8.data_entries;

import java.util.ArrayList;

public class WaterSportsData extends AssistedSportsData{
    public WaterSportsData(String newTitle, String activityType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
        super(newTitle, activityType, newCoordinatesList, newHeartRateList);
    }

    double getConsumedCalories(String activityType) {
        return 0;
    }
}
