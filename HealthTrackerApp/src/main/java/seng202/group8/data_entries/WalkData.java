package seng202.group8.data_entries;

import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class WalkData extends NotAssistedSportsData {
    public WalkData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList, theCurrentUser);
    }

    public double getConsumedCalories() {

        Double[] metValuesArray = {2.8, 3.0, 3.5, 4.3, 5.0, 7.0, 8.3};
        Double[] speedDivisionsArray = {2.0, 2.5, 3.2, 3.5, 4.0, 4.5, 5.0};

        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));


        Double calorieTotal = calculateCaloriesFromStats(metValues, speedDivisions);

        return calorieTotal;
        //if (this.getHeartRateList() == 0);
    }

    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {

        Double[] metValuesArray = {2.8, 3.0, 3.5, 4.3, 5.0, 7.0, 8.3};
        Double[] speedDivisionsArray = {2.0, 2.5, 3.2, 3.5, 4.0, 4.5, 5.0};

        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
        ArrayList<Double> caloriesList;

        caloriesList = calculateCaloriesBurnedBetweenPointsFromStats(metValues, speedDivisions);
        return caloriesList;
    }
}
//    public WalkData(String newTitle, DataType activityType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
//        super(newTitle, activityType, newCoordinatesList, newHeartRateList);
//    }
//
//    double getConsumedCalories(String activityType) {
//        return 0;
//    }
//}
