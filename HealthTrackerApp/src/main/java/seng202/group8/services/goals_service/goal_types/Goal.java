package seng202.group8.services.goals_service.goal_types;

import seng202.group8.data_entries.DataType;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lfa69
 */
public abstract class Goal {

    private String description;
    private Date startDate;
    private LocalDateTime targetDate;
    private GoalType goalType;
    private User user;
    private boolean isCompleted;
    private Double progress;
    private Double current;

    /**
     * Creates a abstract goal for all 3 different types and is called when you create either of the 3 different types of goals
     * @param user the user in the current application
     * @param description the description of the goal
     * @param goalType the type of goal that the use ris creating
     */
    public Goal(User user, String description, GoalType goalType) {
        this.user = user;
        this.description = description;
        this.startDate = new Date();
        this.goalType = goalType;
    }


    /**
     * @return gets the target valuye for the goal
     */
    public abstract Double getTarget();


    /**
     * @param target sets the target for the goal for any goal type
     */
    public abstract void setTarget(Double target);


    /**
     * @return the current progress of the users goal
     */
    public Double getProgress() {
        return progress;
    }


    /**
     * @param progress sets the prigress of the current user goal
     */
    public void setProgress(Double progress) {
        this.progress = progress;
    }


    /**
     * @return the current value that the user is at for their goal
     */
    public Double getCurrent() {
        return current;
    }


    /**
     * @param current sets the current users current status for their goal
     */
    public void setCurrent(Double current) {
        this.current = current;
    }


    /**
     * @return the description parameter
     */
    public String getDescription() {
        return description;
    }


    /**
     * @param description a new String value for description parameter
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the type of the Goal object
     */
    public GoalType getGoalType() {
        return goalType;
    }


    /**
     * @param goalType a new GoalType parameter
     */
    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }


    /**
     * @return the user parameter
     */
    public User getUser() {
        return user;
    }


    /**
     * @param user a new user object
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * @return a boolean value representing if the Goal is now complete
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }


    /**
     * @param isCompleted new boolean value to assign to isCompleted parameter
     */
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }


    /**
     * @return the goal startDate
     */
    public Date getStartDate() {
        return startDate;
    }


    /**
     * @param startDate a new Date object for startDate parameter
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    /**
     * @return gets the target date for the goal the user is trying to achieve
     */
    public LocalDateTime getTargetDate() {
        return targetDate;
    }


    /**
     * @param targetDate sets the target date for the goal
     */
    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }


    /**
     * Calls the below goals functions to check if the goal is completed or not
     */
    public abstract void checkIsCompleted();


    /**
     * Calls the below goals functions to calculate the progress for the goal
     */
    public abstract void calculateProgress();


    /**
     * Calls the below goals functions to calculate the current progress of the goal for the user
     */
    public abstract void calculateCurrent();


    /**
     * Calls the below goals functions to calculate the target for this goal
     */
    public abstract void calculateTarget();


    /**
     * Calls the below goals functions get the data type for the type of goal
     */
    public abstract DataType getDataType();


    /**
     * Calls the below goals functions to set the data type for type of goal
     */
    public abstract void setDataType(DataType dataType);

}