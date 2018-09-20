package seng202.group8.data_entries;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import seng202.group8.user.User;

public class RunData extends NotAssistedSportsData {
    public RunData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList, theCurrentUser);

    }

    public double getConsumedCalories() {

        double calories = calculateCaloriesFromUserStatsAndHeartRateAndTime();

        return calories;
        //if (this.getHeartRateList() == 0);
    }

    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {
        ArrayList<Double> caloriesList = new ArrayList<Double>();

        caloriesList = calculateCaloriesBurnedBetweenPointsFromUserStatsAndHeartRateAndTime();

        return caloriesList;
    }
    /**
     * Below is a calorie calculation that should be more accurate for users that have not updated their information
     * (such as weight) for a long time. However, it does not work properly. It may be fixed before deliverable three,
     * to improve usability,
     * but the existing calculation is valid as long as the user information is correct, so this may have to take a lower
     * priority (its like an extra feature).
     *
     * This calculation was designed first, but one that was easier to implement was found near the end. So the more
     * easily implemented one will be used for now.
     */

//    public double getConsumedCalories() {
//        /** Calculates the calories burned whilst running based on a MET intensity value, which is determined from the
//         * speed in miles per hour. Note that the MET intensity values are provided by the american compendium of
//         * physical activities, and thus the calorie calculations are based on miles per hour. MPH is conserved to make
//         * comparisons between the speed travelled at, and the MET value bounds that match that speed, more accurate.
//         *
//         * To improve accuracy, the incline of the slope is taken into account.
//         */
//        Double[] metValuesArray = {6.0, 8.3, 9.8, 11.0, 11.8, 12.8, 14.5, 16.0, 19.0, 19.8, 23.0};
//        Double[] speedDivisionsArray = {4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0};
//
//        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
//        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
//
//
//        Double calorieTotal = calculateCaloriesFromStats(metValues, speedDivisions);
//
//        return calorieTotal;
//    }
//
//    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {
//
//        Double[] metValuesArray = {6.0, 8.3, 9.8, 11.0, 11.8, 12.8, 14.5, 16.0, 19.0, 19.8, 23.0};
//        Double[] speedDivisionsArray = {4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0};
//
//        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
//        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
//        ArrayList<Double> caloriesList;
//
//        caloriesList = calculateCaloriesBurnedBetweenPointsFromStats(metValues, speedDivisions);
//        return caloriesList;
//    }

}