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

    /**
     * Construct a new user
     * @param name
     * @param age
     * @param weight
     * @param height
     */
    public User(String name, Integer age, Double weight, Double height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        //setBMI()
    }

    /**
     *
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The age of the user
     */
    public Integer getAge() {
        return age;
    }

    /**
     *
     * @return The weight of the user (kg)
     */
    public Double getWeight() {
        return weight;
    }


    /**
     *
     * @return The height of the user (cm)
     */
    public Double getHeight() {
        return height;
    }

    /**
     *
     * @return The BMI type of the user
     */
    public BMIType getBMI() {
        return BMI;
    }

    /**
     *
     * @return The users stats from activities
     */
    public UserStats getUserStats() {
        return userStats;
    }

    /**
     *
     * @return the Stress Level Type of the user
     */
    public StressLevelType getStressLevel() {
        return stressLevel;
    }


}
