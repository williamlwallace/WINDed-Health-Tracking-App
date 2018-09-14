package seng202.group8.user;

import seng202.group8.services.statistics_service.StatisticsService;
import seng202.group8.user.user_stats.Sex;
import seng202.group8.user.user_stats.UserStats;
import seng202.group8.activity_collection.ActivityListCollection;

import java.util.ArrayList;

/**
 * user is the class which stores the user data for a user of the Winded App
 * @author jco165
 */
public class User {

    private String name;
    private Integer age;
    private Double weight;
    private Double height;
    private Sex sex;
    //private ArrayList<ActivityType> favouriteActivities;
    private BMI bmi;
    private UserStats userStats;
    private ActivityListCollection userActivities;
    private ArrayList<UserObserver> observers;
    //private HealthService userHealth;
    private StatisticsService statsService;



    /**
     * Construct a new user
     * @param name the full name of the new user
     * @param age the age of the new user
     * @param weight the weight of the new user in kg
     * @param height the height of the new user in cm
     */
    public User(String name, Integer age, Double weight, Double height, Sex sex) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.userStats = new UserStats();
        this.observers = new ArrayList<UserObserver>();
        this.userActivities = new ActivityListCollection(name + "'s activity collection");
        this.bmi = new BMI(calculateBMI());
        this.sex = sex;
        //this.userHealth = new HealthService(this);
        this.statsService = new StatisticsService(this);
        userStats.addUserBMITypeRecords(bmi);
        userStats.addUserWeightRecords(weight);
    }

    /**
     * Get the users Stats Service Data
     * @return a Statistics Service object which holds valuable user data
     */
    public StatisticsService getStatsService() {
        return statsService;
    }

    /**
     * Get the users Health Service data
     * @return a Health Service object which contains all health risk information
     */
    //public HealthService getUserHealth() {
        //return userHealth;
    //}

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
    public void setBMI(Double BMI) {
        bmi.setBMI(BMI);
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
    public BMI getBMI() {
        return bmi;
    }

    /**
     *
     * @return The Sex of the user Male or Female
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Set the sex of the user to a passed Sex enum value
     * @param sex the new Sex constant of the User
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     *
     * @return The users stats from activities
     */
    public UserStats getUserStats() {
        return userStats;
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
     * Calculates the BMI of the user based on current weight and height
     * @return The BMI value of the user based on their weight and height
     */
    public Double calculateBMI() {
        Double heightMetres = height * 0.01;
        Double numericalBMI = weight/(heightMetres * heightMetres);
        return numericalBMI;
    }

    /**
     * Update the weight of the user, notifying observers and updating records.
     * @param newWeight the new weight of the user (kg)
     */
    public void updateWeight(Double newWeight) {
        setWeight(newWeight);
        userStats.addUserWeightRecords(newWeight);
        updateBMI(calculateBMI());
        notifyAllObservers();
    }

    /**
     * Update the BMI of the user, notifying observers and updating records.
     * @param newBMI the new BMI of the user (kg/m**2)
     */
    public void updateBMI(Double newBMI) {
        setBMI(newBMI);
        userStats.addUserBMITypeRecords(bmi);
        notifyAllObservers();
    }

    /**
     * Add an observer to be updated when User's attributes change
     * @param observer the new UserObserver to be notified of changes
     */
    public void attach(UserObserver observer) {
        observers.add(observer);
    }

    /**
     * Update all observers
     */
    public void notifyAllObservers() {
        for (UserObserver observer : observers) {
            observer.update();
        }
    }



}
