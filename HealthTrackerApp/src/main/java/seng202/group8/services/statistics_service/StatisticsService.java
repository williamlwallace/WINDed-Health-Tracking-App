package seng202.group8.services.statistics_service;

import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.Data;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.*;

import java.util.ArrayList;

public class StatisticsService {

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

    public Double getKmRunWeek() {
        return kmRunWeek;
    }

    public void setKmRunWeek(Double kmRunWeek) {
        this.kmRunWeek = kmRunWeek;
    }

    public Double getmSwamWeek() {
        return mSwamWeek;
    }

    public void setmSwamWeek(Double mSwamWeek) {
        this.mSwamWeek = mSwamWeek;
    }

    public Double getKmBikedWeek() {
        return kmBikedWeek;
    }

    public void setKmBikedWeek(Double kmBikedWeek) {
        this.kmBikedWeek = kmBikedWeek;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Double getCaloriesBurnedWeek() {
        return caloriesBurnedWeek;
    }

    public void setCaloriesBurnedWeek(Double caloriesBurnedWeek) {
        this.caloriesBurnedWeek = caloriesBurnedWeek;
    }

    public Double getWeightLossWeek() {
        return weightLossWeek;
    }

    public void setWeightLossWeek(Double weightLossWeek) {
        this.weightLossWeek = weightLossWeek;
    }

    /**
     * Variables used for the main statistics display screen/tab
     */

    public Integer averageHeartRate;
    public Double kmRunWeek;
    public Double mSwamWeek;
    public Double kmBikedWeek;
    public String healthStatus;
    public Double caloriesBurnedWeek;
    public Double weightLossWeek;

    public UserStats userStats;
    public ActivityListCollection collection;
    public ArrayList<ActivityList> arrayCollection;


    public StatisticsService(User user) {
        userStats = user.getUserStats();
        arrayCollection = collection.getActivityListCollection();
    }

    /**
     * Functions for graphs
     */

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
            graph.addXAxis((record.get(i).getDate()).toString());
        }
        return graph;
    }

    /**
     * Grabs the record for fat to muscle records and grabs each date and fat to muscle data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Strings in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataFatToMuscle() {
        GraphXY graph = new GraphXY();
        ArrayList<FatToMuscleRecord> record = userStats.getUserFatToMuscleRecords();
        for (int i = 0; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getFatToMuscle()).toString());
            graph.addXAxis((record.get(i).getDate()).toString());
        }
        return graph;
    }

    /**
     * Grabs the record for bmi records and grabs each date and bmi data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Strings in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataBMIType() { //CHANGE TO BE VALUE NOT OVERALL NAME
        GraphXY graph = new GraphXY();
        ArrayList<BMITypeRecord> record = userStats.getUserBMITypeRecords();
        for (int i = 0; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getBmi()).toString());
            graph.addXAxis((record.get(i).getDate()).toString());
        }
        return graph;
    }

    /**
     * Grabs the record for Stress Level records and grabs each date and stress level data and assigns them to an x or y axis arrayList
     * so that it can be plotted and adds these arrayLists to a graph object which only stores Strings in the lists
     * @return a graphXY object that contains the x and y axis arrayLists
     */
    public GraphXY getGraphDataStressLevel() {
        GraphXY graph = new GraphXY();
        ArrayList<StressLevelRecord> record = userStats.getUserStressLevelRecords();
        for (int i = 0; i < record.size(); i++) {
            graph.addYAxis((record.get(i).getStress()).toString());
            graph.addXAxis((record.get(i).getDate()).toString());
        }
        return graph;
    }
}
