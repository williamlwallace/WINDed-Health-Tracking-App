package seng202.group8.data_entries;

//import seng202.group8.parser.Distance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private HeartRateData heartRateData;


    private double consumedCalories;

    private Double distanceCovered;


    public Data(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList) {

//        double inputData = getCSVActivities();
        this.title = newTitle;
        this.creationDate = new Date(newDateTimes.get(0).getYear(), newDateTimes.get(0).getMonthValue(), newDateTimes.get(0).getDayOfMonth());
//        this.dataSubType = DataType.parseSubDataType(activityType);
        this.dataType = dataType;
        this.allDateTimes = newDateTimes;
        this.heartRateData = new HeartRateData(newHeartRateList);
        setCoordinatesArrayList(newCoordinatesList);
        this.distanceCovered = calculateDistanceCovered();
//        this.consumedCalories = getConsumedCalories();

    }


    public void setCoordinatesArrayList(ArrayList<CoordinateData> newCoordinatesList) {
        this.coordinatesArrayList = new ArrayList<CoordinateData>();

    }


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


}

