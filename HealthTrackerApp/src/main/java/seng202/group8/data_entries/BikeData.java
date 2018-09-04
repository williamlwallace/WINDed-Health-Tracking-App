package seng202.group8.data_entries;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BikeData extends AssistedSportsData{
    public BikeData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList);

        double consumedCalories = 0.0;
        this.consumedCalories = getConsumedCalories();
    }


//    public BikeData(String newTitle, DataType activityType,ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
//        super(newTitle, activityType, newCoordinatesList, newHeartRateList);
//    }


    public double getConsumedCalories() {
        /** Calculates the calories burned whilst biking based on a MET intensity value, which is determined from the
         * heartrate, and incline.
         */

        return 0;
        //if (this.getHeartRateList() == 0);
    }

}
