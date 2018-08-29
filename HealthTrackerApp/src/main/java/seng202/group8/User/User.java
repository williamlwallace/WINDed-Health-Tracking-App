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
     * @param name the full name of the new User
     * @param age the age of the new User
     * @param weight the weight of the new User in kg
     * @param height the height of the new User in cm
     */
    public User(String name, Integer age, Double weight, Double height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        //setBMI()
    }

    /**
     * Set the Name of the user
     * @param name the given new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the age of the User
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
     * Set the BMI of the user to a given BMI type enumeration value
     * @param BMI the new BMI type of the user
     */
    public void setBMI(BMIType BMI) {
        this.BMI = BMI;
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

    /**
     * Calculates the BMI of the user and parses it into the BMIType Enum to get a value for what BMI category they are in
     * @return The BMIType enum value of the user based on their weight and height
     */
    public BMIType calculateBMI() {
        double heightMetres = height * 0.01;
        double numericalBMI = weight/(heightMetres * heightMetres);
        return BMIType.parseBMI(numericalBMI);
    }


}
