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
//
//        Double[] metValuesArray = {2.8, 3.0, 3.5, 4.3, 5.0, 7.0, 8.3};
//        Double[] speedDivisionsArray = {2.0, 2.5, 3.2, 3.5, 4.0, 4.5, 5.0};
//
//        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
//        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
//
//
//        Double calorieTotal = calculateCaloriesFromStats(metValues, speedDivisions);
//
//        return calorieTotal;
//        //if (this.getHeartRateList() == 0);
//    }
//
//    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {
//
//        Double[] metValuesArray = {2.8, 3.0, 3.5, 4.3, 5.0, 7.0, 8.3};
//        Double[] speedDivisionsArray = {2.0, 2.5, 3.2, 3.5, 4.0, 4.5, 5.0};
//
//        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
//        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
//        ArrayList<Double> caloriesList = new ArrayList<>();
//
//        caloriesList = calculateCaloriesBurnedBetweenPointsFromStats(metValues, speedDivisions);
//        return caloriesList;
//    }
}

