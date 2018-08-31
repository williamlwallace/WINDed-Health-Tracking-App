package seng202.group8.services.goals_service;

import seng202.group8.activity_collection.ActivityListCollectionObserver;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.activity_collection.ActivityListCollection;

import java.util.ArrayList;

public class GoalsService extends ActivityListCollectionObserver {

    private ArrayList<Goal> currentActivityGoals;
    private ArrayList<Goal> previousActivityGoals;

    private ArrayList<Goal> currentWeightLossGoals;
    private ArrayList<Goal> previousWeightLossGoals;

    private ArrayList<Goal> currentTimesPerformedGoals;
    private ArrayList<Goal> previousTimesPerformedGoals;

    private ActivityListCollection activityListCollectionObserved;


    public GoalsService(ActivityListCollection activityListCollectionObserved) {
        this.currentActivityGoals = new ArrayList<Goal>();
        this.previousActivityGoals = new ArrayList<Goal>();

        this.currentWeightLossGoals = new ArrayList<Goal>();
        this.previousWeightLossGoals = new ArrayList<Goal>();

        this.currentTimesPerformedGoals = new ArrayList<Goal>();
        this.currentTimesPerformedGoals = new ArrayList<Goal>();
        this.activityListCollectionObserved = activityListCollectionObserved;
        this.activityListCollectionObserved.attach(this);
    }


    public void update() {
        tidyUpActivityGoals();
        tidyUpTimesPerformedGoals();
        tidyUpWeightLossGoals();
        System.out.println("The updater is called");
    }

    private void tidyUpActivityGoals() {
        for (Goal goal : currentActivityGoals) {
            goal.checkIsCompleted();
            if (goal.getIsCompleted()) {
                previousActivityGoals.add(goal);
                currentActivityGoals.remove(goal);
            }
        }
    }

    private void tidyUpWeightLossGoals() {
        for (Goal goal : currentWeightLossGoals) {
            goal.checkIsCompleted();
            if (goal.getIsCompleted()) {
                currentWeightLossGoals.add(goal);
                previousWeightLossGoals.remove(goal);
            }
        }
    }

    private void tidyUpTimesPerformedGoals() {
        for (Goal goal : currentTimesPerformedGoals) {
            goal.checkIsCompleted();
            if (goal.getIsCompleted()) {
                currentTimesPerformedGoals.add(goal);
                previousTimesPerformedGoals.remove(goal);
            }
        }
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
