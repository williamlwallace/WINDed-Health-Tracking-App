package seng202.group8.data_entries;

import java.util.ArrayList;
import java.util.Date;

public abstract class Data {

    /*This structure uses the coordinates list, and heart rate list as arguments to calculate the calories consumed in
    * an activity
    * XX TESTS NOT WRITTEN JUST YET*/

    private String title;
//    private DataType = new DataType();
    private Date creationDate;
    private DataType dataType;
    private DataType dataSuperType;
    private ArrayList<Double> coordinatesList;
    private ArrayList<Integer> heartRateList;
    private double consumedCalories;

    private Double distanceCovered;

    public String getTitle() {
        return title;
    }

//    public String getDataType() {
//        return DataType.getDataType();
//    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ArrayList<Double> getCoordinatesList() {
        return coordinatesList;
    }
    public ArrayList<Integer> getHeartRateList() {
        return heartRateList;
    }

    abstract double getConsumedCalories(String activityType);

    public void setTitle(String newTitle) { this.title = newTitle; }
//    public void setActivityType(String newActivityType) { this.DataType = newActivityType; }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public void setCoordinatesList(ArrayList<Double> newCoordinatesList) { this.coordinatesList = newCoordinatesList; }
    public void setHeartRateList(ArrayList<Integer> newHeartRateList)  { this.heartRateList = newHeartRateList; }
//    public void setConsumedCalories(double consumedCalories) {
//        this.consumedCalories = consumedCalories;
//    }
//    private ArrayList<CSVDataLine> inputData;

    public Data(String newTitle, DataType dataType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {

//        double inputData = getCSVActivities();
        this.title = newTitle;
//        this.dataSubType = DataType.parseSubDataType(activityType);
        this.dataType = dataType;
        this.heartRateList = newHeartRateList;
        this.coordinatesList = newCoordinatesList;
        this.creationDate = new Date();
//        this.distanceCovered = calculateDistanceCovered();
//        this.consumedCalories = getConsumedCalories();

    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataSubType) {
        this.dataType = dataSubType;
    }

    public Double getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(Double distanceCovered) {
        this.distanceCovered = distanceCovered;
    }
}