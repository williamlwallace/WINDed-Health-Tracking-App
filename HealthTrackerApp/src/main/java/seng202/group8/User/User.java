package seng202.group8.User;

import seng202.group8.User.User_Stats.UserStats;

/**
 * User is the class which stores the User data for a user of the Winded App
 * @author jco165
 */
public class User {

    private String name;
    private Integer age;
    private Double weight;
    private Double height;
    //private ArrayList<ActivityType> favouriteActivities;
    private BMIType BMI;
    private UserStats userStats;
    private StressLevelType stressLevel;




    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    public BMIType getBMI() {
        return BMI;
    }

    public UserStats getUserStats() {
        return userStats;
    }

    public StressLevelType getStressLevel() {
        return stressLevel;
    }


}
