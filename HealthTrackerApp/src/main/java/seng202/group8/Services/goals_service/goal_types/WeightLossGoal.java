package seng202.group8.Services.goals_service.goal_types;

import seng202.group8.Services.goals_service.Goal;
import seng202.group8.User.User;
import utils.exceptions.NotCoherentWeightLossGoalException;


public class WeightLossGoal extends Goal {

    private Double targetWeight;

    /**
     *
     * @param user the user associated with the goal
     * @param targetWeight the weight the user wants to achieve
     * @throws NotCoherentWeightLossGoalException when the targetWeight is lower or equal to the user currentWeight.
     */
    public WeightLossGoal(User user, Double targetWeight) throws NotCoherentWeightLossGoalException {
        super(user);
        if (targetWeight >= user.getWeight()) {
            throw new NotCoherentWeightLossGoalException();
        } else {
            this.targetWeight = targetWeight;
        }
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
}
