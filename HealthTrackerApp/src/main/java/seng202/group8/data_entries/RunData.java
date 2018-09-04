package seng202.group8.data_entries;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RunData extends NotAssistedSportsData{
    public RunData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList);
    }

    public double getConsumedCalories() {

        return 0;
        //if (this.getHeartRateList() == 0);
    }
}

//    public RunData(String newTitle, DataType dataType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
//        super(newTitle, dataType, newCoordinatesList, newHeartRateList);
//    }
//
//    double getConsumedCalories(String activityType) {
//        return 0;
//    }
//}
