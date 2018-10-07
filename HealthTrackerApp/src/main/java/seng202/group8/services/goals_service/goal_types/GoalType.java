package seng202.group8.services.goals_service.goal_types;

/**
 * @author lfa69
 */
public enum GoalType {


    WeightLossGoal,

    ActivityGoal,

    TimePerformedGoal;


    /**
     *
     * @param goalType
     * @return a String representing one of the GoalType
     */
    public static String fromEnumToString(GoalType goalType) {
        switch (goalType) {
            case WeightLossGoal:
                return "Weight Loss";
            case ActivityGoal:
                return "Activity";
            default:
                return "Frequency";
        }
    }
}
