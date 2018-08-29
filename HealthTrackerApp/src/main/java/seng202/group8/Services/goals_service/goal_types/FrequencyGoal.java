package seng202.group8.Services.goals_service.goal_types;

import seng202.group8.User.User;

public class FrequencyGoal extends Goal {

    private Integer timesToPerformActivity;

    public FrequencyGoal(User user, String description, GoalType goalType, Integer timesToPerformActivity) {
        super(user, description, goalType);
        this.timesToPerformActivity = timesToPerformActivity;
    }


    public void checkIsCompleted() {

    }


    // Uncomment to see what is failing, I mainly need a DataType and a function in each Data subclass to retrieve the distance covered
//    /**
//     *
//     * Sieves through the data logged by the user and retrieves the number of times an activity type was performed
//     * If the the activity type has been performed as many times as required then the goal is completed.
//     */
//    public void checkIsCompleted() {
//        Double distanceCovered = 0.0;
//        ArrayList<Data> sameTypeData = user.getActivitiesCollection().retrieveSameTypeActivities(dataType, getStartDate());
//
//        if (timesToPerformActivity <= sameTypeData.size()) {
//            setIsCompleted(true);
//        }
//    }

    /**
     *
     * @return the timesToPerform parameter
     */
    public Integer getTimesToPerformActivity() {
        return timesToPerformActivity;
    }

    /**
     *
     * @param timesToPerformActivity a new Integer value for timesToPerformActivity parameter
     */
    public void setTimesToPerformActivity(Integer timesToPerformActivity) {
        this.timesToPerformActivity = timesToPerformActivity;
    }
}
