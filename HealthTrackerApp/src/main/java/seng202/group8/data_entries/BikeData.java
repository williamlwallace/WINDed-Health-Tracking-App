package seng202.group8.data_entries;

import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class BikeData extends AssistedSportsData{
    public BikeData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList, theCurrentUser);

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

        Double[] metValuesArray = {3.5, 5.8, 6.8, 8.0, 10.0};
        Double[] speedDivisionsArray = {5.5, 9.4, 11.9, 13.9, 15.9};

        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));


        Double calorieTotal = calculateCaloriesFromStats(metValues, speedDivisions);

        return calorieTotal;
    }

    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {

        Double[] metValuesArray = {3.5, 5.8, 6.8, 8.0, 10.0};
        Double[] speedDivisionsArray = {5.5, 9.4, 11.9, 13.9, 15.9};

        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
        ArrayList<Double> caloriesList;

        caloriesList = calculateCaloriesBurnedBetweenPointsFromStats(metValues, speedDivisions);
        return caloriesList;
    }

}
