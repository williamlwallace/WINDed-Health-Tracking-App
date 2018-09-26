package seng202.group8.services.goals_service.goal_types;

import seng202.group8.user.User;
import utils.exceptions.NotCoherentWeightLossGoalException;

import java.util.ArrayList;

/**
 * @author lfa69
 */
public class WeightLossGoal extends Goal {

    private Double targetWeight;

    private Double startWeight;

    public WeightLossGoal(User user, String description, GoalType goalType, Double targetWeight) throws NotCoherentWeightLossGoalException {
        super(user, description, goalType);
        if (targetWeight >= user.getWeight()) {
            throw new NotCoherentWeightLossGoalException();
        } else {
            this.targetWeight = targetWeight;
            this.startWeight = user.getWeight();
        }
    }

    /**
     *
     * @param user the user associated with the goal
     * @param targetWeight the weight the user wants to achieve
     * @throws NotCoherentWeightLossGoalException when the targetWeight is lower or equal to the user currentWeight.
     */


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
     *
     * @return the targetWeight parameter
     */
    public Double getTargetWeight() {
        return targetWeight;
    }

    /**
     *
     * @param targetWeight a new Double value for the targetWeight parameter.
     */
    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }

    public void calculateProgress() {
        setProgress(1 - (startWeight - targetWeight) / targetWeight);
    }

    public void calculateTarget() {
        setTarget(targetWeight);
    }

    public void calculateCurrent() {
        setCurrent(startWeight);
    }
}
