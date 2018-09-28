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
    private Double target;

    public Goal(User user, String description, GoalType goalType) {
        this.user = user;
        this.description = description;
        this.startDate = new Date();
        this.goalType = goalType;

    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    /**
     *
     * @return the description parameter
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description a new String value for description parameter
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the type of the Goal object
     */
    public GoalType getGoalType() {
        return goalType;
    }

    /**
     *
     * @param goalType a new GoalType parameter
     */
    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    /**
     *
     * @return the user parameter
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user a new user object
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return a boolean value representing if the Goal is now complete
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }


    /**
     *
     * @param isCompleted new boolean value to assign to isCompleted parameter
     */
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     *
     * @return the goal startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate a new Date object for startDate parameter
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }

    public abstract void checkIsCompleted();

    public abstract void calculateProgress();

    public abstract void calculateCurrent();

    public abstract void calculateTarget();

    public abstract DataType getDataType();

}