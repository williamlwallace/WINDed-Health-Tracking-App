package seng202.group8.services.goals_service.goal_types;

import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author lfa69
 */
public class ActivityGoal extends Goal {

    private Double distanceToCoverKm;
    private Double distanceCurrentlyCovered;
    private DataType dataType;

    public ActivityGoal(User user, String description, GoalType goalType, DataType dataType, Double distanceToCover, LocalDateTime targetDate) {
        super(user, description, goalType);
        setTargetDate(targetDate);
        this.distanceToCoverKm = distanceToCover;
        this.distanceCurrentlyCovered = 0.0;
        this.dataType = dataType;
    }

    /**
     *
     * @return the distanceCovered parameter
     */
    public Double getTarget() {
        return distanceToCoverKm;
    }

    /**
     *
     * @param distanceToCover a new Double value for the distanceCovered parameter
     */
    public void setTarget(Double distanceToCover) {
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

        if ((1 - (distanceToCoverKm - distanceCovered) / distanceToCoverKm) >= 1.0) {
            setIsCompleted(true);
        }
        setDistanceCurrentlyCovered(distanceCovered);
    }

    public void calculateProgress() {
        Double distanceCovered = 0.0;
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        for (Data data : sameTypeData) {
            distanceCovered += data.getDistanceCovered();
        }
        setProgress(1 - (distanceToCoverKm - distanceCovered) / distanceToCoverKm);
    }

    public void calculateTarget() {
        setTarget(distanceToCoverKm);
    }

    public void calculateCurrent() {
        Double distanceCovered = 0.0;
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        for (Data data : sameTypeData) {
            distanceCovered += data.getDistanceCovered();
        }
        setCurrent(distanceCovered);
    }

}
