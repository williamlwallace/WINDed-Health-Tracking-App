package seng202.group8.data_entries;

import java.util.ArrayList;

public class BikeData extends AssistedSportsData{


    public BikeData(String newTitle, DataType activityType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
        super(newTitle, activityType, newCoordinatesList, newHeartRateList);
    }

    double getConsumedCalories(String activityType) {
        return 0;
    }

}
