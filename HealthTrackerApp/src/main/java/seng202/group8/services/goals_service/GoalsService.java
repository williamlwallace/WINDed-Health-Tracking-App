package seng202.group8.services.goals_service;

import seng202.group8.activity_collection.ActivityListCollectionObserver;
import seng202.group8.services.Service;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.user.User;
import seng202.group8.user.UserObserver;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author lfa69
 * The actual service the user will have to store. This offers the list of all the current and previous
 * user goals divided into the 3 different types.
 * Listens to user and their activities changes implementing ActivityListCollectionObserver and UserObserver observers.
 *
 */
public class GoalsService extends Service implements ActivityListCollectionObserver, UserObserver {



    private ArrayList<Goal> currentActivityGoals;
    private ArrayList<Goal> previousActivityGoals;

    private ArrayList<Goal> currentWeightLossGoals;
    private ArrayList<Goal> previousWeightLossGoals;

    private ArrayList<Goal> currentTimesPerformedGoals;
    private ArrayList<Goal> previousTimesPerformedGoals;


    /**
     * Creates a goals service for the current user
     * @param user the user that is currently using the application
     */
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


    /**
     * Function called when the observers are triggered.
     */
    public void update() {
        tidyUpActivityGoals();
        tidyUpTimesPerformedGoals();
        tidyUpWeightLossGoals();
    }

    /**
     * Helper function called every time a new Data value is added to the user data collection
     */
    private void tidyUpActivityGoals() {
        Iterator<Goal> it = currentActivityGoals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            goal.checkIsCompleted();
            System.out.println(goal.getIsCompleted());
            if (goal.getIsCompleted() || LocalDateTime.now().isAfter(goal.getTargetDate())) {
                previousActivityGoals.add(goal);
                it.remove();
            }
        }
    }

    /**
     * Helper function called every time a new Data value is added to the user data collection
     */
    private void tidyUpWeightLossGoals() {
        Iterator<Goal> it = currentWeightLossGoals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            goal.checkIsCompleted();
            if (goal.getIsCompleted() || LocalDateTime.now().isAfter(goal.getTargetDate())) {
                previousWeightLossGoals.add(goal);
                it.remove();
            }
        }
    }

    /**
     * Helper function called every time a new Data value is added to the user data collection
     */
    private void tidyUpTimesPerformedGoals() {
        Iterator<Goal> it = currentTimesPerformedGoals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            goal.checkIsCompleted();
            if (goal.getIsCompleted() || LocalDateTime.now().isAfter(goal.getTargetDate())) {
                previousTimesPerformedGoals.add(goal);
                it.remove();
            }
        }
    }


    /**
     * Gets all the current goals for the user in 1 big list
     * @return the a list of all of the current goals
     */
    public ArrayList<Goal> getAllCurrentGoals() {
        ArrayList<Goal> goals = new ArrayList<>();
        goals.addAll(this.currentActivityGoals);
        goals.addAll(this.currentWeightLossGoals);
        goals.addAll(this.currentTimesPerformedGoals);
        return goals;
    }

    /**
     * Gets all the current goals where their target is on a particular date
     * @param selectedDate the date at which to see if there are any goals on that given day
     * @return a list of goals targeted for that day
     */
    public ArrayList<Goal> getAllCurrentGoalsExpiringOnGivenDate(LocalDate selectedDate) {
        ArrayList<Goal> goals = getAllCurrentGoals();
        ArrayList<Goal> onDateGoals = new ArrayList<>();
        for (Goal goal : goals) {
            LocalDate expiryDate = goal.getTargetDate().toLocalDate();
            if (expiryDate.isEqual(selectedDate)) {
                onDateGoals.add(goal);
            }
        }
        return onDateGoals;

    }

    /**
     * @return currentActivityGoals parameter
     */
    public ArrayList<Goal> getCurrentActivityGoals() {
        return currentActivityGoals;
    }

    /**
     * @param currentActivityGoals a new ArrayList<Goal> parameter
     */
    public void setCurrentActivityGoals(ArrayList<Goal> currentActivityGoals) {
        this.currentActivityGoals = currentActivityGoals;
    }

    /**
     * @return previousActivityGoals parameter
     */
    public ArrayList<Goal> getPreviousActivityGoals() {
        return previousActivityGoals;
    }

    /**
     * @param previousActivityGoals a new ArrayList<Goal> parameter
     */
    public void setPreviousActivityGoals(ArrayList<Goal> previousActivityGoals) {
        this.previousActivityGoals = previousActivityGoals;
    }

    /**
     * @return currentWeightLossGoals parameter
     */
    public ArrayList<Goal> getCurrentWeightLossGoals() {
        return currentWeightLossGoals;
    }

    /**
     * @param currentWeightLossGoals a new ArrayList<Goal> parameter
     */
    public void setCurrentWeightLossGoals(ArrayList<Goal> currentWeightLossGoals) {
        this.currentWeightLossGoals = currentWeightLossGoals;
    }

    /**
     * @return previousWeightLossGoals parameter
     */
    public ArrayList<Goal> getPreviousWeightLossGoals() {
        return previousWeightLossGoals;
    }

    /**
     * @param previousWeightLossGoals a new ArrayList<Goal> parameter
     */
    public void setPreviousWeightLossGoals(ArrayList<Goal> previousWeightLossGoals) {
        this.previousWeightLossGoals = previousWeightLossGoals;
    }

    /**
     * @return currentTimesPerformedGoals parameter
     */
    public ArrayList<Goal> getCurrentTimesPerformedGoals() {
        return currentTimesPerformedGoals;
    }

    /**
     * @param currentTimesPerformedGoals a new ArrayList<Goal> parameter
     */
    public void setCurrentTimesPerformedGoals(ArrayList<Goal> currentTimesPerformedGoals) {
        this.currentTimesPerformedGoals = currentTimesPerformedGoals;
    }

    /**
     * @return previousTimesPerformeGoals parameter
     */
    public ArrayList<Goal> getPreviousTimesPerformedGoals() {
        return previousTimesPerformedGoals;
    }

    /**
     * @param previousTimesPerformedGoals a new ArrayList<Goal> parameter
     */
    public void setPreviousTimesPerformedGoals(ArrayList<Goal> previousTimesPerformedGoals) {
        this.previousTimesPerformedGoals = previousTimesPerformedGoals;
    }

}
