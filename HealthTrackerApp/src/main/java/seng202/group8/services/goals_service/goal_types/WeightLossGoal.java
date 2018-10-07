package seng202.group8.services.goals_service.goal_types;

import org.apache.commons.lang3.ObjectUtils;
import seng202.group8.data_entries.DataType;
import seng202.group8.user.User;
import utils.exceptions.NotCoherentWeightLossGoalException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author lfa69
 */
public class WeightLossGoal extends Goal {

    private Double targetWeight;
    private DataType dataType;
    private Double startWeight;

    /**
     * Creates a weight loss goal and calls the super functions
     * @param user the user creating the goal
     * @param description the description of the goal
     * @param goalType the goal type, in this case the weight loss type
     * @param targetWeight the target weight for the user to end up as
     * @param targetDate the target date at whoch the user aims to complete this goal by
     */
    public WeightLossGoal(User user, String description, GoalType goalType, Double targetWeight, LocalDateTime targetDate) {
        super(user, description, goalType);
        setTargetDate(targetDate);
        this.targetWeight = targetWeight;
        this.startWeight = user.getWeight();
    }


    /**
     *
     * The goal is set to completed in case the user current weight is lower or equal to the established goal
     */
    public void checkIsCompleted() {
        if (targetWeight >= getUser().getWeight()) {
            setIsCompleted(true);
        }
    }

    /**
     * @return the targetWeight of the user with the goals service abstract methods
     */
    public Double getTarget() {
        return targetWeight;
    }

    /**
     * @param targetWeight a new Double value for the targetWeight parameter.
     */
    public void setTarget(Double targetWeight) {
        this.targetWeight = targetWeight;
    }

    /**
     * @return returns the starting weight of the user when they created the goal
     */
    public Double getStartWeight() {
        return startWeight;
    }

    /**
     * @param startWeight sets the starting weight of the user when they create their goal
     */
    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }

    /**
     * Calculates the progress that the user has currently lost adn sets the progress variable as a percentage out of 1.0
     */
    public void calculateProgress() {
        Double currentLost = startWeight - getUser().getWeight();
        if (currentLost < 0.0 ) {
            setProgress(0.0);
        } else {
            setProgress((currentLost / (startWeight - targetWeight)));
        }

    }

    /**
     * Calculates the target and sets the target weight based on the goals service abstract method for all goal types
     */
    public void calculateTarget() {
        setTarget(targetWeight);
    }

    /**
     * Calculates the current for the users weight and sets the current status of the users weight for the goal
     */
    public void calculateCurrent() {
        setCurrent(startWeight);
    }

    /**
     * @return gets the data Type for the weight loss goal which is all types as it doesn't need a type
     */
    public DataType getDataType() {return DataType.ALL;}

    /**
     * @param dataType sets the data type for the weight loss goal, onyl needed for the abstract class as weight loss doesn't need a type
     */
    public void setDataType(DataType dataType) {this.dataType = dataType;}
}
