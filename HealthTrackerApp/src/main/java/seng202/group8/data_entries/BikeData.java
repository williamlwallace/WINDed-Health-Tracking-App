package seng202.group8.data_entries;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BikeData extends AssistedSportsData{
    public BikeData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList);
    }


//    public BikeData(String newTitle, DataType activityType,ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
//        super(newTitle, activityType, newCoordinatesList, newHeartRateList);
//    }

    double getConsumedCalories(String activityType) {

        return 0;
        //if (this.getHeartRateList() == 0);
    }

}
