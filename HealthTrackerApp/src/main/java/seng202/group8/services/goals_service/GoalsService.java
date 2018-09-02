package seng202.group8.services.goals_service;

import seng202.group8.activity_collection.ActivityListCollectionObserver;
import seng202.group8.services.Service;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.user.User;
import seng202.group8.user.UserObserver;

import javax.naming.ServiceUnavailableException;
import java.util.ArrayList;
import java.util.Iterator;

public class GoalsService extends Service implements ActivityListCollectionObserver, UserObserver {



    private ArrayList<Goal> currentActivityGoals;
    private ArrayList<Goal> previousActivityGoals;

    private ArrayList<Goal> currentWeightLossGoals;
    private ArrayList<Goal> previousWeightLossGoals;

    private ArrayList<Goal> currentTimesPerformedGoals;
    private ArrayList<Goal> previousTimesPerformedGoals;




    public GoalsService(User user) {
        super(user);

        this.currentActivityGoals = new ArrayList<Goal>();
        this.previousActivityGoals = new ArrayList<Goal>();

        this.currentWeightLossGoals = new ArrayList<Goal>();
        this.previousWeightLossGoals = new ArrayList<Goal>();

        this.currentTimesPerformedGoals = new ArrayList<Goal>();
        this.previousTimesPerformedGoals = new ArrayList<Goal>();
        user.getUserActivities().attach(this);
        user.attach(this);
    }


    public void update() {
        tidyUpActivityGoals();
        tidyUpTimesPerformedGoals();
        tidyUpWeightLossGoals();
        System.out.println("The updater is called");
    }

    private void tidyUpActivityGoals() {
        Iterator<Goal> it = currentActivityGoals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            goal.checkIsCompleted();
            if (goal.getIsCompleted()) {
                previousActivityGoals.add(goal);
                it.remove();
            }
        }
    }

    private void tidyUpWeightLossGoals() {
        Iterator<Goal> it = currentWeightLossGoals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            goal.checkIsCompleted();
            if (goal.getIsCompleted()) {
                previousWeightLossGoals.add(goal);
                it.remove();
            }
        }
    }

    private void tidyUpTimesPerformedGoals() {
        Iterator<Goal> it = currentTimesPerformedGoals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            goal.checkIsCompleted();
            if (goal.getIsCompleted()) {
                previousTimesPerformedGoals.add(goal);
                it.remove();
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
