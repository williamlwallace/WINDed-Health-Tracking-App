package seng202.group8.dataEntries;

import java.util.ArrayList;

public abstract class Data {

    /*This structure uses the coordinates list, and heart rate list as arguments to calculate the calories consumed in
    * an activity
    * XX TESTS NOT WRITTEN JUST YET*/

    private ArrayList<Double> coordinatesList;
    private ArrayList<Integer> heartRateList;
    private double consumedCalories;

    public ArrayList<Double> getCoordinatesList() { return coordinatesList; }
    public ArrayList<Integer> getHeartRateList() { return heartRateList; }

    public void setCoordinatesList(ArrayList<Double> newCoordinatesList) { this.coordinatesList = newCoordinatesList; }
    public void setHeartRateList(ArrayList<Integer> newHeartRateList)  { this.heartRateList = newHeartRateList; }
//    private ArrayList<CSVDataLine> inputData;

    abstract double getConsumedCalories();

    public Data(ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList, String activityType) {

//        double inputData = getCSVActivities();
        this.heartRateList = newHeartRateList;
        this.coordinatesList = newCoordinatesList;
        this.consumedCalories = getConsumedCalories();

    }

}