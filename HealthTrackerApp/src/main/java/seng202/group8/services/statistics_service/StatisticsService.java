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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
        averageHeartRate = 0;
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
        this.averageHeartRate = averageHeartRate / heartRateValues;
    }

    /**
     * Gets the km ran in total
     * @return a Double of the amount of Km's
     */
    public Double getKmRunTotal() {
        return kmRunTotal;
    }

    /**
     * Calls the getKmOfActivity with type RUN to see how many km's the user has run in total
     */
    public void setKmRunTotal() {
        this.kmRunTotal = getKmOfActivityType(DataType.RUN);
    }

    /**
     * Gets the amount of km walked in total
     * @return a Double of the amount of Km's
     */
    public Double getkmWalkTotal() {
        return kmWalkTotal;
    }

    /**
     * Calls the getKmOfActivity with type WALK to see how many km's the user has walked in total
     */
    public void setKmWalkTotal() {
        this.kmWalkTotal = getKmOfActivityType(DataType.WALK);
    }

    /**
     * Gets the amount of km biked total
     * @return a Double of the amount of Km's
     */
    public Double getKmBikedTotal() {
        return kmBikedTotal;
    }

    /**
     * Calls the getKmOfActivity with type BIKE to see how many km's the user has biked total
     */
    public void setKmBikedTotal() {
        this.kmBikedTotal = getKmOfActivityType(DataType.BIKE);
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
        String healthStatus = "";
        Boolean hasRisk = Boolean.FALSE;
        if (healthService.isAtCardiovasMortalityRisk()) {
            healthStatus += "You are at risk of Cardiovascular Mortality\n";
            hasRisk = Boolean.TRUE;
        }
        if (healthService.isBradicardic()) {
            healthStatus += "You are at risk of Bradycardia\n";
            hasRisk = Boolean.TRUE;
        }
        if (healthService.isTachicardic()) {
            healthStatus += "You are at risk of Tachycardic\n";
            hasRisk = Boolean.TRUE;
        }
        if (!hasRisk) {
            healthStatus += "You currently have no health risks\nNice work!";
        } else {
            healthStatus += "Click a button below to find out more\nabout your health risks";
        }
        this.healthStatus = healthStatus;
    }

    /**
     * Gets the amount fo calories burned by the user in total
     * @return a Double of the amount of calories burned
     */
    public Double getCaloriesBurnedTotal() {
        return caloriesBurnedTotal;
    }

    /**
     * Searches through all data and adds how much calories were burned by the user
     */
    public void setCaloriesBurned() {

        Double calories = 0.0;
        for(int i = 0; i < arrayCollection.size(); i++) {
            ArrayList<Data> activityList = arrayCollection.get(i).getActivityList();
            for(int j = 0; j < activityList.size(); j++) {
                Data data = activityList.get(j);
                calories += data.getConsumedCalories();
            }

        }

        this.caloriesBurnedTotal = calories;
    }

    /**
     * Gets the amount of weight the user has lost in total
     * @return a Double with the weight lost
     */
    public Double getWeightLossTotal() {
        return weightLossTotal;
    }

    /**
     * Sets the weight loss in total for the user for all drops in weight
     */
    public void setWeightLossTotal() {
        Double totalLost = 0.0;
        Double current;
        ArrayList<WeightRecord> weightRecords = userStats.getUserWeightRecords();
        Double previous = weightRecords.get(0).getWeight();
        for (int i = 1; i < weightRecords.size() ; i++) {
            current = weightRecords.get(i).getWeight();
            if (( previous - current) > 0) {
                totalLost += previous - current;
            }
            previous = current;
        }
        this.weightLossTotal = totalLost;
    }

    /**
     * Gets the total amount the user has travelled in km's
     * @return a Double of the amount of Km's
     */
    public Double getKmTotal() {
        return totalKm;
    }

    /**
     * uses getKmOfActivityType with parameter ALL to gets the total amount of Km's in the past week
     */
    public void setTotalKm() {
        this.totalKm = getKmOfActivityType(DataType.ALL);
    }

    /**
     * Searches through all data and checks if the activity type is the same as the current data being checked
     * or if activity type is all and if that holds then add that to the km's
     * @param activityType the type of activity to figure out the Km's, can be ALL to get all types of activities
     * @return the amount of km's travelled by the certain activityType
     */
    public Double getKmOfActivityType(DataType activityType) {
        Double km = 0.0;

        for(int i = 0; i < arrayCollection.size(); i++) {
            ArrayList<Data> activityList = arrayCollection.get(i).getActivityList();
            for(int j = 0; j < activityList.size(); j++) {
                Data data = activityList.get(j);
                if (data.getDataType() == activityType || activityType == DataType.ALL) {
                    km += data.getDistanceCovered() / 1000;
                }
            }

        }
        return km;
    }


    /**
     * Variables used for the main statistics display screen/tab
     */

    private Integer averageHeartRate;
    private Double kmRunTotal;
    private Double kmWalkTotal;
    private Double kmBikedTotal;
    private String healthStatus;
    private Double caloriesBurnedTotal;
    private Double weightLossTotal;
    private Double totalKm;
    private HealthService healthService;

    private UserStats userStats;
    private ActivityListCollection collection;
    private ArrayList<ActivityList> arrayCollection;

    public StatisticsService(User user) {
        healthService = new HealthService(user);
        userStats = user.getUserStats();
        collection = user.getUserActivities();
        arrayCollection = collection.getActivityListCollection();
    }

    /**
     * A function to update all of the home statistics
     */
    public void updateHomeStats() {
        setAverageHeartRate();
        setWeightLossTotal();
        setCaloriesBurned();
        setHealthStatus();
        setKmRunTotal();
        setKmWalkTotal();
        setKmBikedTotal();
        setTotalKm();
    }

    //Functions for graphs

    /**
     * Grabs the record for weight records and grabs each date and weight and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Doubles in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataWeight() {
        GraphXY graph = new GraphXY();
        ArrayList<WeightRecord> record = userStats.getUserWeightRecords();
        if (record.size() > 0) {
            graph.addYAxis((record.get(0).getWeight()));
            graph.addXAxis(0.0);
        }
        for (int i = 1; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getWeight()));
            graph.addXAxis(getDifference(record.get(i).getDate(), record.get(0).getDate()));
        }
        return graph;
    }

    /**
     * Grabs the record for bmi records and grabs each date and bmi data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Doubles in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataBMIType() {
        GraphXY graph = new GraphXY();
        ArrayList<BMITypeRecord> record = userStats.getUserBMITypeRecords();
        if (record.size() > 0) {
            graph.addYAxis((record.get(0).getBmi().getBMIValue()));
            graph.addXAxis(0.0);
        }
        for (int i = 1; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getBmi().getBMIValue()));
            graph.addXAxis(getDifference(record.get(i).getDate(), record.get(0).getDate()));
        }
        return graph;
    }

    /**
     * Grabs the speed data for the current activity and assigns the date or the speed to the x or y axis ArrayLists
     * so that it can be plotted, these arrayLists are added to a graphXY object which the Graph Controller uses
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getSpeedGraphOverTime(Data data) {
        GraphXY graph = new GraphXY();
        ArrayList<Double> speedList = data.getKphSpeedsBetweenPoints();
        ArrayList<LocalDateTime> time = data.getAllDateTimes();
        ArrayList<Double> times = createTimes(time);
        for (int i = 0; i < speedList.size() - 1; i++) {
            graph.addYAxis(speedList.get(i));
            graph.addXAxis(times.get(i));
        }
        return graph;
    }

    /**
     * Grabs the data for Stress Level and grabs each date and stress level data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Doubles in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getStressLevelOverTimeGraph(Data data) {
        GraphXY graph = new GraphXY();
        ArrayList<Double> stressLevelList = data.getStressProportionsBetweenPoints();
        ArrayList<LocalDateTime> time = data.getAllDateTimes();
        ArrayList<Double> times = createTimes(time);
        for (int i = 0; i < stressLevelList.size() - 1; i++) {
            graph.addYAxis(stressLevelList.get(i));
            graph.addXAxis(times.get(i));
        }
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
        ArrayList<Double> times = createTimes(time);
        Double summary = 0.0;
        for (int i = 0; i < coordinatesArrayList.size() - 1; i++) {
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(coordinatesArrayList.get(i), coordinatesArrayList.get(i + 1));
            summary += coordinateDataDifference.getDistanceDifference();
            graph.addYAxis(summary);
            graph.addXAxis(times.get(i));
        }
        return graph;
    }

    /**
     * Creates a Double list of seconds between each of the local date times for the graphing functions
     * @param timeList a list full of local date times to be converted to doubles in seconds
     * @return an arrayList of doubles which contains starting from 0 the time in seconds after the start point
     */
    public ArrayList<Double> createTimes(ArrayList<LocalDateTime> timeList) {
        ArrayList<Double> newTimes = new ArrayList<Double>();
        for(int i = 0; i < timeList.size(); i++) {
            if (i != 0) {
                newTimes.add(i, getDifference(timeList.get(i), timeList.get(0)));
            } else {
                newTimes.add(0.0);
            }
        }
        return newTimes;
    }

    /**
     * Returns the amount of seconds in between 2 data points by comparing all available information in local date time
     * Has to sadly compare years too as we could be going from 2018 december to 2019 January on new years eve even if it was
     * only for a few seconds
     * @param toDateTime starting date value
     * @param fromDateTime current date value
     * @return a double of seconds between both dates
     */
    public Double getDifference(LocalDateTime toDateTime, LocalDateTime fromDateTime) {
        LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );
        long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );
        long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );
        long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );
        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );
        long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes( minutes );
        long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);
        Double doubleReturn = Long.valueOf(seconds).doubleValue();
        doubleReturn += minutes * 60;
        doubleReturn += hours * 60 * 60;
        doubleReturn += days * 60 * 60 * 24;
        doubleReturn += months * 60 * 60 * 24 * 30;
        doubleReturn += years * 60 * 60 * 24 * 30 * 365;
        return doubleReturn;
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
        ArrayList<Double> times = createTimes(time);
        for (int i = 0; i < heartRates.size() - 1; i++) {
            graph.addXAxis(times.get(i));
            graph.addYAxis(Double.valueOf(heartRates.get(i)));
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
        ArrayList<Double> calories = data.calculateCaloriesBurnedBetweenPointsFromUserStatsAndHeartRateAndTime();
        ArrayList<LocalDateTime> time = data.getAllDateTimes();
        ArrayList<Double> times = createTimes(time);
        for (int i = 0; i < time.size() - 1; i++) {
            graph.addXAxis(times.get(i));
            graph.addYAxis(calories.get(i));
        }
        return graph;
    }
}
