package seng202.group8.Services.goals_service.goal_types;

import seng202.group8.Services.goals_service.Goal;
import seng202.group8.User.BMIType;
import seng202.group8.User.User;

public class WeightLossGoal extends Goal {

    private BMIType currentBMI;

    public WeightLossGoal(User user) {
        super(user);

    }

}
