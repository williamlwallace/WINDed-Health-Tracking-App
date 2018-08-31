package seng202.group8.user;

import seng202.group8.user.user_stats.UserStats;
import seng202.group8.activity_collection.ActivityListCollection;

/**
 * user is the class which stores the user data for a user of the Winded App
 * @author jco165
 */
public class User {

    private String name;
    private Integer age;
    private Double weight;
    private Double height;
    //private ArrayList<ActivityType> favouriteActivities;
    private BMIType BMICategory;
    private Double BMIValue;
    private UserStats userStats;
    private StressLevelType stressLevel;
    private ActivityListCollection userActivities;


    /**
     * Construct a new user
     * @param name the full name of the new user
     * @param age the age of the new user
     * @param weight the weight of the new user in kg
     * @param height the height of the new user in cm
     */
    public User(String name, Integer age, Double weight, Double height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.userActivities = new ActivityListCollection(name + "'s activity collection");
        setBMI(calculateBMI());
    }

    /**
     * Set the Name of the user
     * @param name the given new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the age of the user
     * @param age the given new age for the user
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Set the users weight
     * @param weight the new weight of the user
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Set the users height
     * @param height the new height of the user
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * Set the BMICategory of the user to a given BMI type enumeration value and the BMIValue double to its value kg/m**2
     * @param BMI the new BMI value of the user
     */
    public void setBMI(double BMI) {
        this.BMIValue = BMI;
        BMICategory = BMIType.parseBMI(BMI);
    }

    /**
     * Set the StressLevel of the user
     * @param stressLevel the new StressLevelType of the user
     */
    public void setStressLevel(StressLevelType stressLevel) {
        this.stressLevel = stressLevel;
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
    public BMIType getBMICategory() {
        return BMICategory;
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

    /**
     * @return The activity list collection of all the users activities
     */
    public ActivityListCollection getUserActivities() {
        return userActivities;
    }

    /**
     * Assign an ActivityListCollection to the user for the activities they have done
     * @param userActivities the ActivityListCollection that belongs to the user
     */
    public void setUserActivities(ActivityListCollection userActivities) {
        this.userActivities = userActivities;
    }

    /**
     * Calculates the BMI of the user and parses it into the BMIType Enum to get a value for what BMI category they are in
     * @return The BMIType enum value of the user based on their weight and height
     */
    public double calculateBMI() {
        double heightMetres = height * 0.01;
        double numericalBMI = weight/(heightMetres * heightMetres);
        return numericalBMI;
    }


}
