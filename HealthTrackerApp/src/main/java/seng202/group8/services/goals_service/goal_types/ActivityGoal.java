package seng202.group8.services.goals_service.goal_types;

import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.user.User;

import java.util.ArrayList;

public class ActivityGoal extends Goal {

//    private DataType dataType; implement once Data classes will be ready
    private Double distanceToCoverKm;
    private Double distanceCurrentlyCovered;
    private DataType dataType;

    public ActivityGoal(User user, String description, GoalType goalType, DataType dataType,  Double distanceToCover) {
        super(user, description, goalType);
        this.distanceToCoverKm = distanceToCover;
        this.distanceCurrentlyCovered = 0.0;
        this.dataType = dataType;
    }

    /**
     *
     * @return the distanceCovered parameter
     */
    public Double getDistanceToCoverKm() {
        return distanceToCoverKm;
    }

    /**
     *
     * @param distanceToCover a new Double value for the distanceCovered parameter
     */
    public void setDistanceToCoverKm(Double distanceToCover) {
        this.distanceToCoverKm = distanceToCover;
    }

    public Double getDistanceCurrentlyCovered() {
        return distanceCurrentlyCovered;
    }

    public void setDistanceCurrentlyCovered(Double distanceCurrentlyCovered) {
        this.distanceCurrentlyCovered = distanceCurrentlyCovered;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    // Uncomment to see what is failing, I mainly need a DataType and a function in each Data subclass to retrieve the distance covered
    /**
     *
     * Sieves through the data logged by the user until a given date and it adds
     * up the total distance covered by the user in a particular activity type.
     * If the distance covered is greater or equal the the goal distance then the goal is completed.
     */
    public void checkIsCompleted() {
        Double distanceCovered = 0.0;
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        for (Data data : sameTypeData) {
            distanceCovered += data.getDistanceCovered();
        }

        if (distanceCovered >= distanceToCoverKm) {
            setIsCompleted(true);
        }
        distanceCurrentlyCovered = distanceCovered;
    }

}
