package seng202.group8.services.statistics_service;

import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.*;
import seng202.group8.services.health_service.HealthService;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.*;
import sun.security.pkcs11.wrapper.Functions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class StatisticsService {

    /**
     * Gets the average heart rate variable
     * @return an Integer containing the heart rate average value stored
     */
    public Integer getAverageHeartRate() {
        return averageHeartRate;
    }

    /**
     * Searches through all activities and grabs the heart rate Data
     * Then it adds all of the heart rates together and records the amount of times heart rate is recorded
     * then uses that to determine the heart rate average
     */
    public void setAverageHeartRate() {
        int heartRateValues = 0;
        for(int i = 0; i < arrayCollection.size(); i++) {
            ArrayList<Data> activityList = arrayCollection.get(i).getActivityList();
            for(int j = 0; j < activityList.size(); j++) {
                Data data = activityList.get(j);
                ArrayList<Integer> heartRateList = data.getHeartRateList();
                for(int k = 0; k < heartRateList.size(); k++){
                    averageHeartRate += heartRateList.get(k);
                }
                heartRateValues += heartRateList.size();
            }

        }
        averageHeartRate = averageHeartRate / heartRateValues;
    }

    /**
     * Gets the km ran between now and last week
     * @return a Double of the amount of Km's
     */
    public Double getKmRunWeek() {
        return kmRunWeek;
    }

    /**
     * Calls the getKmOfActivity with type RUN to see how many km's the user has run in the past week
     */
    public void setKmRunWeek() {
        this.kmRunWeek = getKmOfActivityType(DataType.RUN);
    }

    /**
     * Gets the amount of km swum since last week
     * @return a Double of the amount of Km's
     */
    public Double getmSwamWeek() {
        return mSwamWeek;
    }

    /**
     * Calls the getKmOfActivity with type SWIM to see how many km's the user has swum in the past week
     */
    public void setmSwamWeek() {
        this.mSwamWeek = getKmOfActivityType(DataType.SWIM);
    }

    /**
     * Gets the amount of km biked since last week
     * @return a Double of the amount of Km's
     */
    public Double getKmBikedWeek() {
        return kmBikedWeek;
    }

    /**
     * Calls the getKmOfActivity with type BIKE to see how many km's the user has biked in the past week
     */
    public void setKmBikedWeek() {
        this.kmBikedWeek = getKmOfActivityType(DataType.BIKE);
    }

    /**
     * Gets the current health Status of the user as a readable String
     * @return a String with the format to be shown on the GUI
     */
    public String getHealthStatus() {
        return healthStatus;
    }

    /**
     * Checks the health service class to determine the users health risks and displays them in a readable way
     */
    public void setHealthStatus() {
        String healthStatus = "Current Health Status:\n";
        Boolean hasRisk = Boolean.FALSE;
        if (healthService.isAtCardiovasMortalityRisk()) {
            healthStatus += "You are at risk of Cardiovas Mortality\n";
            hasRisk = Boolean.TRUE;
        }
        if (healthService.isBradicardic()) {
            healthStatus += "You are at risk of Bradicardic\n";
            hasRisk = Boolean.TRUE;
        }
        if (healthService.isTachicardic()) {
            healthStatus += "You are at risk of Tachicardic\n";
            hasRisk = Boolean.TRUE;
        }
        if (!hasRisk) {
            healthStatus += "You currently have no health risks\n Nice work!";
        } else {
            healthStatus += "Use the search bar to find out more about your risks";
        }
        this.healthStatus = healthStatus;
    }

    /**
     * Gets the amount fo calories burned by the user in the last week
     * @return a Double of the amount of calories burned
     */
    public Double getCaloriesBurnedWeek() {
        return caloriesBurnedWeek;
    }

    /**
     * Searches through all data in the past week and adds how much calories were burned by the user
     */
    public void setCaloriesBurnedWeek() {

        Double calories = 0.0;
        for(int i = 0; i < arrayCollection.size(); i++) {
            ArrayList<Data> activityList = arrayCollection.get(i).getActivityList();
            for(int j = 0; j < activityList.size(); j++) {
                Data data = activityList.get(j);
                if (inLastWeek(data.getCreationDate())) {
                    //calories += data.getConsumedCalories(data.getTitle()); // NEEDS TO CHANGE AND BE UPDATED WHEN CALORIES COMPLETED
                }
            }

        }

        this.caloriesBurnedWeek = calories;
    }

    /**
     * Gets the amount of weight the user has lost in the past week
     * @return a Double with the weight lost
     */
    public Double getWeightLossWeek() {
        return weightLossWeek;
    }

    /**
     * Looks back 1 week to see the weight of the user and compares it with today's weight
     */
    public void setWeightLossWeek() {
        Double lastWeekWeight = 0.0;
        ArrayList<WeightRecord> weightRecords = userStats.getUserWeightRecords();
        for (int i = weightRecords.size() - 1; i >= 0; i--) {
            if (!inLastWeek(weightRecords.get(i).getDate())) {
                lastWeekWeight = weightRecords.get(i).getWeight();
                break;
            }
        }
        this.weightLossWeek = lastWeekWeight - weightRecords.get(weightRecords.size() - 1).getWeight();
        if (this.weightLossWeek < 0) {
            this.weightLossWeek = 0.0;
        }
    }

    /**
     * Gets the total amount the user has travelled in km's over the past week
     * @return a Double of the amount of Km's
     */
    public Double getTotalKmWeek() {
        return totalKmWeek;
    }

    /**
     * uses getKmOfActivityType with parameter ALL to gets the total amount of Km's in the past week
     */
    public void setTotalKmWeek() {
        this.totalKmWeek = getKmOfActivityType(DataType.ALL);
    }

    /**
     * Searches through all data and checks if the data is within the last week by comparing it with a dateWeek variable
     * If it is within the week then it checks the activityType to see if it wants to add the distance covered to the km Double
     * @param activityType the type of activity to figure out the Km's, can be ALL to get all types of activities
     * @return the amount of km's travelled by the certain activityType
     */
    public Double getKmOfActivityType(DataType activityType) {
        Double km = 0.0;

        for(int i = 0; i < arrayCollection.size(); i++) {
            ArrayList<Data> activityList = arrayCollection.get(i).getActivityList();
            for(int j = 0; j < activityList.size(); j++) {
                Data data = activityList.get(j);
                if (inLastWeek(data.getCreationDate())) {
                    if (data.getDataType() == activityType || activityType == DataType.ALL) {
                        km += data.getDistanceCovered();
                    }
                }

            }

        }
        return km;
    }


    /**
     * Gets the date for the past week time
     * @return a Date variabel of last weeks date
     */
    public Date getDateWeek() {
        return dateWeek;
    }

    /**
     * Calculates the Date last week by using milliseconds in a day * 7 taken away from the current time
     */
    public void calDateWeek() {
        long day = 86400000;
        Date date = new Date(System.currentTimeMillis() - (7 * day));
        this.dateWeek = date;
    }

    /**
     * Checks if the date passed in is within the last week or not
     * @param compare the date that could or could not be within the last week
     * @return True if it is within the last week, False if it is not within the last week
     */
    public Boolean inLastWeek(Date compare) {
        if (dateWeek.before(compare)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }


    /**
     * Variables used for the main statistics display screen/tab
     */

    private Integer averageHeartRate;
    private Double kmRunWeek;
    private Double mSwamWeek;
    private Double kmBikedWeek;
    private String healthStatus;
    private Double caloriesBurnedWeek;
    private Double weightLossWeek;
    private Double totalKmWeek;
    private HealthService healthService;

    private UserStats userStats;
    private ActivityListCollection collection;
    private ArrayList<ActivityList> arrayCollection;
    private Date dateWeek;

    public StatisticsService(User user) {
        healthService = new HealthService(user);
        userStats = user.getUserStats();
        collection = user.getUserActivities();
        arrayCollection = collection.getActivityListCollection();
    }

    //Functions for graphs

    /**
     * Grabs the record for weight records and grabs each date and weight and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Strings in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataWeight() {
        GraphXY graph = new GraphXY();
        ArrayList<WeightRecord> record = userStats.getUserWeightRecords();
        for (int i = 0; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getWeight()).toString());
            graph.addXAxis(new SimpleDateFormat("dd-MM-YYYY HH ").format(record.get(i).getDate()));
        }
        return graph;
    }

    /**
     * Grabs the record for bmi records and grabs each date and bmi data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Strings in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataBMIType() {
        GraphXY graph = new GraphXY();
        ArrayList<BMITypeRecord> record = userStats.getUserBMITypeRecords();
        for (int i = 0; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getBmi().getBMIValue()).toString());
            graph.addXAxis(new SimpleDateFormat("dd-MM-YYYY HH ").format(record.get(i).getDate()));
        }
        return graph;
    }

    /**
     * Grabs the record for Stress Level records and grabs each date and stress level data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Strings in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getStressLevelOverTimeGraph() {
        GraphXY graph = new GraphXY();
        return graph;
    }

    /**
     * Grabs the data for an activity, then it gets from that activity data the coordinates list and the times
     * From there it creates both the x and y axis objects to be able to be graphed using coordinate data difference to get the distance calculations
     * @param data the activity that the user wants to plot
     * @return a graph type object that contains x and y axis array Lists
     */
    public GraphXY getDistanceOverTimeGraph(Data data) {
        GraphXY graph = new GraphXY();
        ArrayList<CoordinateData> coordinatesArrayList = data.getCoordinatesArrayList();
        ArrayList<LocalDateTime> time = data.getAllDateTimes();
        for (int i = 0; i < coordinatesArrayList.size() - 1; i++) {
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(coordinatesArrayList.get(i), coordinatesArrayList.get(i + 1));
            graph.addYAxis(Double.toString(coordinateDataDifference.getDistanceDifference()));
            graph.addXAxis(time.get(i).toString());
        }
        return graph;
    }

    /**
     * Grabs the data for an activity, then it gets from that activity data the heart rate and the times
     * From there is adds to both the x and y axis of the graph object from the heart rate List and the times List
     * @param data the activity that the user wants to plot
     * @return a graph type object that contains x and y axis array Lists
     */
    public GraphXY getHeartRateOverTimeGraph(Data data) {
        GraphXY graph = new GraphXY();
        ArrayList<Integer> heartRates = data.getHeartRateList();
        ArrayList<LocalDateTime> time = data.getAllDateTimes();
        for (int i = 0; i < heartRates.size() - 1; i++) {
            graph.addXAxis(time.get(i).toString());
            graph.addYAxis(heartRates.get(i).toString());
        }
        return graph;
    }

    /**
     * Grabs the data for an activity, then it gets from that activity data the calories burned and the times
     * From there is adds to both the x and y axis of the graph object from the calories burned List and the times List
     * @param data the activity that the user wants to plot
     * @return a graph type object that contains x and y axis array Lists
     */
    public GraphXY getCaloriesBurnedOverTimeGraph(Data data) {
        GraphXY graph = new GraphXY();
        //ArrayList<Double> calories = data.getConsumedCalories();
        ArrayList<LocalDateTime> time = data.getAllDateTimes();
        for (int i = 0; i < time.size() - 1; i++) {
            graph.addXAxis(time.get(i).toString());
            //graph.addYAxis(calories.get(i).toString()); // WAITING FOR CALORIES CALCULATOR
        }
        return graph;
    }
}
