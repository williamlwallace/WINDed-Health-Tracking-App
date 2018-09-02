package seng202.group8.data_entries;

//import seng202.group8.parser.Distance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
//import java.time.Duration;

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



    public ArrayList<CoordinateData> getCoordinatesArrayList() {
        return coordinatesArrayList;
    }

    public ArrayList<Integer> getHeartRateList() {
        return heartRateList;
    }

    abstract double getConsumedCalories(String activityType);


    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    //    public void setActivityType(String newActivityType) { this.DataType = newActivityType; }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCoordinatesArrayList(ArrayList<CoordinateData> newCoordinatesList) {
        this.coordinatesArrayList = new ArrayList<CoordinateData>();

    }
//        for (int i = 3; i < newCoordinatesList.size(); i += 3) {
//            ArrayList<Double> tempInitialList = new ArrayList<Double>();
//            tempInitialList.add(0.0);
//            tempInitialList.add(0.0);
//            tempInitialList.add(0.0);
//
//            CoordinateData tempCoordinateData = new CoordinateData(tempInitialList);
//            tempCoordinateData.setLatitude(newCoordinatesList.get(i - 3));
//            tempCoordinateData.setLongitude(newCoordinatesList.get(i - 2));
//            tempCoordinateData.setAltitude(newCoordinatesList.get(i - 1));
//            this.coordinatesArrayList.add(tempCoordinateData);

        //        ArrayList<Integer> latitudeIndices = new ArrayList<Integer>();
//        ArrayList<Integer> longitudeIndices = new ArrayList<Integer>();
//        ArrayList<Integer> altitudeIndices = new ArrayList<Integer>();

//        for (int i = 0; i < newCoordinatesList.size(); i++) {
//            latitudeIndices.set(0, 0);
//            longitudeIndices.set(0, 1);
//            altitudeIndices.set(0, 2);
//            if (latitudeIndices.size() == (int) (i / 3)) {
//                latitudeIndices.add(i);
//            } else if (longitudeIndices.size() == (int) (i / 3)) {
//                longitudeIndices.add(i);
//            } else if (altitudeIndices.size() == (int) (i / 3))
//                altitudeIndices.add(i);
//        }
//


    public void setHeartRateList(ArrayList<Integer> newHeartRateList) {
        this.heartRateList = newHeartRateList;
    }
//    public void setConsumedCalories(double consumedCalories) {
//        this.consumedCalories = consumedCalories;
//    }
//    private ArrayList<CSVDataLine> inputData;

    public Data(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList) {

//        double inputData = getCSVActivities();
        this.title = newTitle;
        this.creationDate = new Date(newDateTimes.get(0).getYear(), newDateTimes.get(0).getMonthValue(), newDateTimes.get(0).getDayOfMonth());
//        this.dataSubType = DataType.parseSubDataType(activityType);
        this.dataType = dataType;
        this.allDateTimes = newDateTimes;
        this.heartRateList = newHeartRateList;
        setCoordinatesArrayList(newCoordinatesList);
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