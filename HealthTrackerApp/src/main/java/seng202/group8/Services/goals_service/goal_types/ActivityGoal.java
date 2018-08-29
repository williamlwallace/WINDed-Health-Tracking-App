package seng202.group8.Services.goals_service.goal_types;

import seng202.group8.Services.goals_service.Goal;
import seng202.group8.User.User;
import seng202.group8.dataEntries.Data;

import java.util.ArrayList;

public class ActivityGoal extends Goal {

//    private DataType dataType; implement once Data classes will be ready
    private Double distanceToCover;

    public ActivityGoal(User user, Double distanceToCover) {
        super(user);
        this.distanceToCover = distanceToCover;
    }

    public void checkIsCompleted() {
        //JUST CREATED TO MAKE THE COMPILER HAPPY, ONCE i CAN
        //UNCOMMENT I WILL ELIMINATE IT
    }


// Uncomment to see what is failing, I mainly need a DataType and a function in each Data subclass to retrieve the distance covered
//    /**
//     *
//     * Sieves through the data logged by the user until a given date and it adds
//     * up the total distance covered by the user in a particular activity type.
//     * If the distance covered is greater or equal the the goal distance then the goal is completed.
//     */
//    public void checkIsCompleted() {
//        Double distanceCovered = 0.0;
//        ArrayList<Data> sameTypeData = user.getActivitiesCollection().retrieveSameTypeActivities(dataType, getStartDate());
//        for (Data data : sameTypeData) {
//            distanceCovered += data.getDistanceCovered();
//        }
//
//        if (distanceCovered >= distanceToCover) {
//            setIsCompleted(true);
//        }
//    }

}
