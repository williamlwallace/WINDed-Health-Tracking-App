package seng202.group8.data_entries;

import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a class child of an abstract class, used for calorie calculations. This abstraction was originally created
 * for custom MET value
 * calorie calculations taking various factors of speed and terrain changes into account. However, upon finishing the
 * calculations,
 * they were found to be less accurate than the heart rate based calorie calculation. Therefore that functionality
 * was deprecated, and now these abstract class child classes remain simply in order to sort the data objects into
 * different types, according to activity.
 */
public class BikeData extends AssistedSportsData{
    public BikeData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList, theCurrentUser);

    }

    /**
     * Uses an accumulator and a special formula to sum the calories burned between each pair of points.
     * @return  The calories burned for this activity.
     */
    public double getConsumedCalories() {
        ArrayList<Double> caloriesList = new ArrayList<>(calculateCaloriesBurnedBetweenPointsFromUserStatsAndHeartRateAndTime());
        double calories = 0.0;

        for (int i = 0; i < caloriesList.size(); i++) {
            calories += caloriesList.get(i);
        }

        return calories;
    }

    /**
     * Calculates the calories burned between each pair of data points. These can be used to graph calories over time.
     * @return  The calories burned over time between each pair of data points.
     */
    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {
        ArrayList<Double> caloriesList = new ArrayList<Double>();

        caloriesList = calculateCaloriesBurnedBetweenPointsFromUserStatsAndHeartRateAndTime();

        return caloriesList;
    }


}
