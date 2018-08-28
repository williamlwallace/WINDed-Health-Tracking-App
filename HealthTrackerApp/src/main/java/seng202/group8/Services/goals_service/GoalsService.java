package seng202.group8.Services.goals_service;

import seng202.group8.Services.goals_service.goal_types.GoalType;
import seng202.group8.Services.goals_service.goal_types.WeightLossGoal;

import java.util.ArrayList;

public class GoalsService {

    private ArrayList<Goal> currentActivityGoals;
    private ArrayList<Goal> previousActivityGoals;

    private ArrayList<Goal> currentWeightLossGoals;
    private ArrayList<Goal> previousWeightLossGoals;

    private ArrayList<Goal> currentTimesPerformedGoals;
    private ArrayList<Goal> previousTimesPerformedGoals;


    public GoalsService() {
        this.currentActivityGoals = new ArrayList<Goal>();
        this.previousActivityGoals = new ArrayList<Goal>();

        this.currentWeightLossGoals = new ArrayList<Goal>();
        this.previousWeightLossGoals = new ArrayList<Goal>();

        this.currentTimesPerformedGoals = new ArrayList<Goal>();
        this.currentTimesPerformedGoals = new ArrayList<Goal>();
    }


    /**
     *
     * @return currentActivityGoals parameter
     */
    public ArrayList<Goal> getCurrentActivityGoals() {
        return currentActivityGoals;
    }

    /**
     *
     * @param currentActivityGoals a new ArrayList<Goal> parameter
     */
    public void setCurrentActivityGoals(ArrayList<Goal> currentActivityGoals) {
        this.currentActivityGoals = currentActivityGoals;
    }

    /**
     *
     * @return previousActivityGoals parameter
     */
    public ArrayList<Goal> getPreviousActivityGoals() {
        return previousActivityGoals;
    }

    /**
     *
     * @param previousActivityGoals a new ArrayList<Goal> parameter
     */
    public void setPreviousActivityGoals(ArrayList<Goal> previousActivityGoals) {
        this.previousActivityGoals = previousActivityGoals;
    }

    /**
     *
     * @return currentWeightLossGoals parameter
     */
    public ArrayList<Goal> getCurrentWeightLossGoals() {
        return currentWeightLossGoals;
    }

    /**
     *
     * @param currentWeightLossGoals a new ArrayList<Goal> parameter
     */
    public void setCurrentWeightLossGoals(ArrayList<Goal> currentWeightLossGoals) {
        this.currentWeightLossGoals = currentWeightLossGoals;
    }

    /**
     *
     * @return previousWeightLossGoals parameter
     */
    public ArrayList<Goal> getPreviousWeightLossGoals() {
        return previousWeightLossGoals;
    }

    /**
     *
     * @param previousWeightLossGoals a new ArrayList<Goal> parameter
     */
    public void setPreviousWeightLossGoals(ArrayList<Goal> previousWeightLossGoals) {
        this.previousWeightLossGoals = previousWeightLossGoals;
    }

    /**
     *
     * @return currentTimesPerformedGoals parameter
     */
    public ArrayList<Goal> getCurrentTimesPerformedGoals() {
        return currentTimesPerformedGoals;
    }

    /**
     *
     * @param currentTimesPerformedGoals a new ArrayList<Goal> parameter
     */
    public void setCurrentTimesPerformedGoals(ArrayList<Goal> currentTimesPerformedGoals) {
        this.currentTimesPerformedGoals = currentTimesPerformedGoals;
    }

    /**
     *
     * @return previousTimesPerformeGoals parameter
     */
    public ArrayList<Goal> getPreviousTimesPerformedGoals() {
        return previousTimesPerformedGoals;
    }

    /**
     *
     * @param previousTimesPerformedGoals a new ArrayList<Goal> parameter
     */
    public void setPreviousTimesPerformedGoals(ArrayList<Goal> previousTimesPerformedGoals) {
        this.previousTimesPerformedGoals = previousTimesPerformedGoals;
    }
}
