package seng202.group8.data_entries;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Date;

public abstract class Data {

    /*This structure uses the coordinates list, and heart rate list as arguments to calculate the calories consumed in
     * an activity
     * Note:
     * Added a CoordinateData class for the Data abstract class to manage
        incoming coordinate info. It makes each set of three coordinates into
        a coordinate object, and adds them to a list of such objects which are
        stored as a variable of the data class. The data class now has an
        appropriate setter to do this.

        That list of coordinate objects will then be used for
        calorie calculation. Similarly, the next thing to do will be to store
        time values as temporal objects in order to calculate time differences
        between coordinate values, for the purpose of calorie calculation.
        To do this, a list of time values will be passed
        into the Data object.

        This information will then be compared with the heart rate during that
        period to estimate calories burned.

        When there are times that are to difficult to calculate the calories
        using the coordinates, an attempt will be made to give an
        estimation purely based on the heart rate over that time period.
     * XX TESTS NOT WRITTEN JUST YET*/

    private String title;
    //    private DataType = new DataType();
    private Date creationDate;
    private DataType dataType;
    private DataType dataSuperType;
    private ArrayList<LocalDateTime> allDateTimes;
    private ArrayList<CoordinateData> coordinatesArrayList;
    private ArrayList<Integer> heartRateList;
    private HeartRateData heartRateData;
    private double consumedCalories;

    private Double distanceCovered;
    private long millisecondsOfExercise;


    public Data(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList) {

        this.title = newTitle;
        this.creationDate = new Date(newDateTimes.get(0).getYear(), newDateTimes.get(0).getMonthValue(), newDateTimes.get(0).getDayOfMonth());
        this.dataType = dataType;
        this.allDateTimes = newDateTimes;
        this.heartRateData = new HeartRateData(newHeartRateList);
//        setCoordinatesArrayList(newCoordinatesList);
        this.coordinatesArrayList = newCoordinatesList;
        this.distanceCovered = calculateDistanceCovered();
        this.millisecondsOfExercise = calculateMillisecondsOfExercise();
//        this.consumedCalories = getConsumedCalories();

    }


//    public void setCoordinatesArrayList(ArrayList<CoordinateData> newCoordinatesList) {
//        this.coordinatesArrayList = new ArrayList<CoordinateData>();
//
//    }


    private Double calculateDistanceCovered() {
        Double distanceCovered = 0.0;

        if (coordinatesArrayList.size() < 2) {
            return distanceCovered;
        }

        for (int i = 0; i < coordinatesArrayList.size() - 1; i++) {
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(coordinatesArrayList.get(i), coordinatesArrayList.get(i + 1));
            distanceCovered += coordinateDataDifference.getDistanceDifference();
        }

        return distanceCovered;
    }

    private long calculateMillisecondsOfExercise() {
        long millisecondsTrained = 0;

        if (this.allDateTimes.size() < 2) {
            return millisecondsTrained;
        }

        for (int i = 0; i < this.allDateTimes.size() - 1; i++) {
            long msTimeOne = this.allDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long msTimeTwo = this.allDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            millisecondsTrained += msTimeTwo - msTimeOne;
        }

        return millisecondsTrained;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataSuperType() {
        return dataSuperType;
    }

    public void setDataSuperType(DataType dataSuperType) {
        this.dataSuperType = dataSuperType;
    }

    public ArrayList<LocalDateTime> getAllDateTimes() {
        return allDateTimes;
    }

    public void setAllDateTimes(ArrayList<LocalDateTime> allDateTimes) {
        this.allDateTimes = allDateTimes;
    }

    public ArrayList<CoordinateData> getCoordinatesArrayList() {
        return coordinatesArrayList;
    }

    public ArrayList<Integer> getHeartRateList() {
        return heartRateList;
    }

    public void setHeartRateList(ArrayList<Integer> heartRateList) {
        this.heartRateList = heartRateList;
    }

    public HeartRateData getHeartRateData() {
        return heartRateData;
    }

    public void setHeartRateData(HeartRateData heartRateData) {
        this.heartRateData = heartRateData;
    }

    public Double getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(Double distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public long getMillisecondsOfExercise() {
        return millisecondsOfExercise;
    }

    public void setMillisecondsOfExercise(long millisecondsOfExercise) {
        this.millisecondsOfExercise = millisecondsOfExercise;
    }

    public abstract double getConsumedCalories(String activityType);
}

