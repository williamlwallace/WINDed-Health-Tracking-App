package seng202.group8.Services.goals_service.goal_types;

import seng202.group8.Services.goals_service.Goal;
import seng202.group8.User.User;

public class ActivityGoal extends Goal {

//    private DataType dataType;
    private Integer distanceToCover;

    public ActivityGoal(User user) {
        super(user);
    }

    public void checkIsCompleted() {

    }
}
