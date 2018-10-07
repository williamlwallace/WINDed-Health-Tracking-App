package seng202.group8.data_entries;

import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * This is the NotAssistedSportsData abstract class. Functionality was initially suggested to sort
 * activities based on whether they were assisted or not assisted, and calorie calculations based on
 * assisted or unassisted data initially seemed to be needed. However, later in development those features
 * became unnecessary, and so this abstract class level performs no significant function.
 */
public abstract class NotAssistedSportsData extends Data {
    public NotAssistedSportsData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList, theCurrentUser);
    }

    public abstract double getConsumedCalories();

    public abstract ArrayList<Double> getConsumedCaloriesBetweenPoints();

}

