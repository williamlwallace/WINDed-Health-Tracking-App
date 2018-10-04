package seng202.group8.services.goals_service.goal_types;

import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author lfa69
 */
public class FrequencyGoal extends Goal {

    private Integer timesToPerformActivity;
    private Integer timesCurrentlyPerformedActivity;
    private DataType dataType;

    /**
     * The frequency goal constructor to create this goal
     * @param user
     * @param description
     * @param goalType
     * @param dataType
     * @param timesToPerformActivity
     * @param targetDate
     */
    public FrequencyGoal(User user, String description, GoalType goalType, DataType dataType, Integer timesToPerformActivity, LocalDateTime targetDate) {
        super(user, description, goalType);
        setTargetDate(targetDate);
        this.timesToPerformActivity = timesToPerformActivity;
        this.dataType = dataType;
        this.timesCurrentlyPerformedActivity = 0;
    }

    /**
     *
     * Sieves through the data logged by the user and retrieves the number of times an activity type was performed
     * If the the activity type has been performed as many times as required then the goal is completed.
     */
    public void checkIsCompleted() {
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());

        if (timesToPerformActivity <= sameTypeData.size()) {
            setIsCompleted(true);
        }
        timesCurrentlyPerformedActivity = sameTypeData.size();
    }

    /**
     * @return the timesToPerform parameter
     */
    public Double getTarget() {
        return Double.valueOf(timesToPerformActivity);
    }

    /**
     * @param timesToPerformActivity a new Integer value for timesToPerformActivity parameter
     */
    public void setTarget(Double timesToPerformActivity) {
        this.timesToPerformActivity = timesToPerformActivity.intValue();
    }

    /**
     * @return the amount of times the user has currently performed a specific type of activity
     */
    public Integer getTimesCurrentlyPerformedActivity() {
        return timesCurrentlyPerformedActivity;
    }

    /**
     * @param timesCurrentlyPerformedActivity sets the amount of times the user has done a specific activity
     */
    public void setTimesCurrentlyPerformedActivity(Integer timesCurrentlyPerformedActivity) {
        this.timesCurrentlyPerformedActivity = timesCurrentlyPerformedActivity;
    }

    /**
     * @return the data type that the user wants to do a frequency goal on
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType sets the data type for the user to use their frequency goal for
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Calculates the progress of the user and sets the progress as a percentage out of 1.0
     */
    public void calculateProgress() {
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        double test = ((double) timesToPerformActivity - sameTypeData.size()) / timesToPerformActivity;
        setProgress(1.0 - test);
    }

    /**
     * Calculates the targets and sets the target with calls using the goals service abstract calls
     */
    public void calculateTarget() {
        setTarget((double) timesToPerformActivity);
    }

    /**
     * Calculates the current progress of the user and sets the current as the number of data type activities they have
     * done from their goal creation date till now
     */
    public void calculateCurrent() {
        ArrayList<Data> sameTypeData = getUser().getUserActivities().retrieveSameTypeActivities(dataType, getStartDate());
        setCurrent((double) sameTypeData.size());
    }

}
