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


    /**
     * Creates an activity goal and calls super functions
     * @param user the user adding the goal
     * @param description the description of the goal
     * @param goalType the type of goal, activity in this case
     * @param dataType the data type that the user wants to set the goal too, run or walk etc
     * @param distanceToCover the aim for the user in terms of distance to cover
     * @param targetDate the target date at whoch the user wants to complete the goal by
     */
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
     * @param distanceToCover a new Double value for the distanceCovered parameter
     */
    public void setTarget(Double distanceToCover) {
        this.distanceToCoverKm = distanceToCover;
    }


    /**
     * @return the distcnae that the user has currently travelled for this goal
     */
    public Double getDistanceCurrentlyCovered() {
        return distanceCurrentlyCovered;
    }


    /**
     * @param distanceCurrentlyCovered sets the current distance covered
     */
    public void setDistanceCurrentlyCovered(Double distanceCurrentlyCovered) {
        this.distanceCurrentlyCovered = distanceCurrentlyCovered;
    }


    /**
     * @return the data type of this goal for activities
     */
    public DataType getDataType() {
        return dataType;
    }


    /**
     * @param dataType sets the data type for the activity goal
     */
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


    /**
     * Calculates the current progress of the goal by retrieving all the activity data from the goal creation date onwards
     * and seeing how much they have achieved uit by and setting the progress as a percentage out of 1.0
     */
    public void calculateProgress() {
        Double distanceCovered = 0.0;
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        for (Data data : sameTypeData) {
            distanceCovered += data.getDistanceCovered();
        }
        setProgress(1 - (distanceToCoverKm - distanceCovered) / distanceToCoverKm);
    }


    /**
     * calculates the target for the goal, is an abstract function tha can be called for all goal types from goals service
     */
    public void calculateTarget() {
        setTarget(distanceToCoverKm);
    }


    /**
     * Calculates the current amount of distance the user has travelled for the goals display
     */
    public void calculateCurrent() {
        Double distanceCovered = 0.0;
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        for (Data data : sameTypeData) {
            distanceCovered += data.getDistanceCovered();
        }
        setCurrent(distanceCovered);
    }
}