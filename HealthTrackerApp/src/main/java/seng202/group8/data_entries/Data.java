package seng202.group8.data_entries;


import java_sqlite_db.SQLiteJDBC;
import javafx.scene.Node;
import org.apache.commons.lang3.ObjectUtils;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;

/**
 * This is the Data abstract class definition. Data objects contain all of the information from an activity. They also
 * calculate values, and ArrayLists of values, based on the activity. For example, the calories burned over time, the
 * total number of calories burned in the activity, or the speeds between two particular coordinates.
 *
 * @author cmc280
 */
public abstract class Data {

    /*This structure uses the coordinates list, and heart rate list as arguments to calculate the calories consumed in
     * an activity

        When there are times that are too difficult to calculate the calories
        using the coordinates, an attempt will be made to give an
        estimation purely based on the heart rate over that time period.
     * XX TESTS NOT WRITTEN JUST YET*/

    private String title;
    //    private DataType = new DataType();
    public boolean isGraphable;
    private User currentUser;
    private Date creationDate;
    private DataType dataType;
    private DataType dataSuperType;
    private ArrayList<LocalDateTime> allDateTimes;
    private ArrayList<CoordinateData> coordinatesArrayList;
    private ArrayList<Integer> heartRateList;
    private ArrayList<Double> mphSpeedsBetweenPoints;
    private ArrayList<Double> kphSpeedsBetweenPoints;
    private ArrayList<Double> gradientsBetweenPoints;
    private ArrayList<Double> kmDistancesBetweenPoints;
    private ArrayList<Double> milesDistancesBetweenPoints;
    private ArrayList<Long> millisecondsOfExerciseBetweenPoints;
    private ArrayList<Double> consumedCaloriesBetweenPoints;
    private ArrayList<Integer> stressLevelsBetweenPoints;
    private ArrayList<Double> stressProportionsBetweenPoints;

    private Integer stressLevelMax;
    private Integer stressLevelMin;
    private HeartRateData heartRateData;
    private Double dataSpeedKph;
    private Double dataSpeedMph;
    private Double distanceCovered;
    private long millisecondsOfExercise;
    public double consumedCalories;

    private Integer dataId = 0;


    /**
     * This is the constructor for the Data class. A Data object contains the values of different parameters,
     * and calculates other values and ArrayLists of values so that those can be graphed. Those values are calculated
     * upon initialisation of the object, so that they are ready for use later. This means that even if adding a csv
     * file takes a little longer, the graphing portion of the program will function more quickly. isGraphable is a
     * notable boolean variable here: there are particular variable ArrayLists with calculate methods that can only be
     * operated if the input contains csv data. If it is instead from the custom activity creator, the calculate methods
     * that require csvdata will not be run, and their values will be left as null. The only class that attempts to call
     * those null values is Graphs, and Graphs will check the value of isGraphable anyway. This is simply a second layer
     * of crash prevention.
     *
     *
     * @param newTitle is the String title of the incoming activity data.
     * @param dataType is DataType enumerator value of the incoming activity data.
     * @param newDateTimes is an ArrayList of LocalDateTime objects which match each line in the activity.
     * @param newCoordinatesList is an ArrayList containing the coordinates of each line of data as CoordinateData
     *                           objects.
     * @param newHeartRateList is an ArrayList containing the heart rates values of each line of the activity.
     * @param theCurrentUser is the User who is currently using the application and has imported the data.
     *
     */
    public Data(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes,
                ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList,
                User theCurrentUser) {

        this.title = newTitle;
        this.currentUser = theCurrentUser;
        //From Lorenzo: not the prettiest but now it works;)
        this.creationDate = Date.from(newDateTimes.get(0).atZone(ZoneId.systemDefault()).toInstant());
        //
        this.dataType = dataType;
        this.allDateTimes = new ArrayList<>(newDateTimes);
        ArrayList<Integer> toSendHeartRateList = new ArrayList<Integer>(newHeartRateList);
        this.heartRateData = new HeartRateData(toSendHeartRateList);
        this.heartRateList = heartRateData.getHeartRateList();
//        setCoordinatesArrayList(newCoordinatesList);
        this.coordinatesArrayList = new ArrayList<>(newCoordinatesList);
        this.distanceCovered = calculateDistanceCovered();
        this.millisecondsOfExercise = calculateMillisecondsOfExercise();
        this.dataSpeedKph = calculateDataSpeedKilometresPerHour();
        this.dataSpeedMph = calculateDataSpeedMilesPerHour();
        if (newCoordinatesList.get(0).getLatitude() == -1 && newCoordinatesList.get(0).getLongitude() == -1 && newCoordinatesList.get(0).getAltitude() == -1) {
            if (newHeartRateList.get(0) == -1) {
                this.isGraphable = false;
            }
        } else {
            this.isGraphable = true;
        }
        if (this.isGraphable) {
            this.mphSpeedsBetweenPoints = calculateMphSpeedsBetweenPoints();
            this.kphSpeedsBetweenPoints = calculateKphSpeedsBetweenPoints();
            this.gradientsBetweenPoints = calculateGradientsBetweenPoints();
            this.millisecondsOfExerciseBetweenPoints = calculateMillisecondsOfExerciseBetweenPoints();
            this.kmDistancesBetweenPoints = calculateKmDistancesBetweenPoints();
            this.milesDistancesBetweenPoints = calculateMilesDistancesBetweenPoints();
            this.consumedCalories = getConsumedCalories();
            this.consumedCaloriesBetweenPoints = getConsumedCaloriesBetweenPoints();
            this.stressLevelsBetweenPoints = calculateStressLevelsBetweenPoints();
            this.stressLevelMax = calculateStressLevelMax();
            this.stressLevelMin = calculateStressLevelMin();
            this.stressProportionsBetweenPoints = calculateStressProportionsBetweenPoints();
        }
    }

    /**
     * Writted by Sam, compares two data classes to see if they are duplicate.
     * @param data
     * @return
     */
    public boolean equalsNewData(Data data) {
        boolean to_return  = true;
        int i = 0;
        if (this.coordinatesArrayList.size() == data.getCoordinatesArrayList().size()) {
            for (i = 0; i < this.coordinatesArrayList.size(); i++) {
                if (this.coordinatesArrayList.get(i).getLatitude() != data.getCoordinatesArrayList().get(i).getLatitude()) {
                    to_return = false;
                } else if (this.coordinatesArrayList.get(i).getLongitude() != data.getCoordinatesArrayList().get(i).getLongitude()) {
                    to_return = false;
                } else if (this.coordinatesArrayList.get(i).getAltitude() != data.getCoordinatesArrayList().get(i).getAltitude()) {
                    to_return = false;
                }
            }
        } else {
            to_return = false;
        }
        if (!this.title.equals(data.getTitle())) {
            //System.out.println("Title: "+this.title+" - "+data.getTitle());
            to_return  = false;
        } else if (!this.dataType.equals(data.getDataType())) {
            //System.out.println("Type: "+this.dataType+" - "+data.getDataType());
            to_return  = false;
        } else if (!this.allDateTimes.equals(data.getAllDateTimes())) {
            //System.out.println("Date: "+this.allDateTimes.get(0)+" - "+data.getAllDateTimes().get(0));
            to_return  = false;
        } else if (!this.heartRateList.equals(data.getHeartRateList())) {
            //System.out.println("Heart: "+this.heartRateList.get(0)+" - "+data.getHeartRateList().get(0));
        }

        return to_return;
    }




    /**
     * This function calculates the total distance covered in metres over the course of the activity, based on the coordinates
     * the user moves through. The calculation is based on the coordinatesArrayList of the Data object. The differences
     * between each pair of coordinate objects are calculated by the CoordinateDataDifference class constructor. By
     * passing coordinate values into that constructor, the distance differences between two coordinates can then be
     * acquired with coordinateDataDifference.getDistanceDifference(), and added to a running total.
     *
     * @return the total distance covered during the activity.
     */
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

    /**
     * Calculates the total number of milliseconds of exercise performed throughout the whole activity. It does this by
     * comparing every pair of date time values from the activity, and adding the difference between each pair to a
     * running total.
     *
     * @return the total number of milliseconds of exercise performed throughout the activity.
     */
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

    /**
     * Calculates the average speed over the whole activity based on the distance covered, and the number of
     * milliseconds of exercise, and converts it to kilometres per hour.
     *
     * @return the average speed over the whole activity, in kilometres per hour.
     */
    private Double calculateDataSpeedKilometresPerHour() {
        Double dataSpeedKilometresPerHour = 0.0;
        if (calculateMillisecondsOfExercise() == 0.0) {
            System.out.println("Error: To calculate speed, a value for time is needed. \n" +
                    "       The time value for this data set is corrupt. \n" +
                    "       Therefore 0.0 will be returned.\n");
        } else {
            dataSpeedKilometresPerHour = (calculateDistanceCovered() / 1000) / ((double) (calculateMillisecondsOfExercise())
                    / 1000 / 60 / 60);
        }
        return dataSpeedKilometresPerHour;
    }

    /**
     * Calculates the average speed over the whole activity based on the distance covered, and the number of
     * milliseconds of exercise, and converts it to miles per hour.
     *
     * @return the average speed over the whole activity, in miles per hour.
     */
    private Double calculateDataSpeedMilesPerHour() {
        final double kmToMilesConstant = 0.62137119223733;
        Double dataSpeedMilesPerHour = 0.0;

        if (calculateMillisecondsOfExercise() == 0.0) {
            System.out.println("Error: To calculate speed, a value for time is needed. \n" +
                    "       The time value for this data set is corrupt. \n" +
                    "       Therefore 0.0 will be returned.\n");
        } else {
            dataSpeedMilesPerHour = ((calculateDistanceCovered() / 1000) * kmToMilesConstant) /
                    ((double) (calculateMillisecondsOfExercise()) / 1000 / 60 / 60);
        }

        return dataSpeedMilesPerHour;
    }

    /**
     * Calculates the speed between each pair of points in miles per hour, and adds it to an ArrayList. This is based
     * on the speeds which are calculated in coordinate data difference.
     *
     * @return an ArrayList containing the speeds across each pair of points, in miles per hour.
     */
    private ArrayList<Double> calculateMphSpeedsBetweenPoints() {

        ArrayList<Double> speedsArrayList = new ArrayList<Double>();
        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();
        //ArrayList<Double> gradientsPerPairOfPoints = new ArrayList<Double>();

        if ((localCoordinatesArrayList.size() < 2) || (localDateTimes.size() < 2)) {
            speedsArrayList.add(0.0);
            return speedsArrayList;

        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
            speedsArrayList.add(0.0);
            return speedsArrayList;
        }

        final double kmToMilesConstant = 0.62137119223733;
        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {

            long msTrained = 0;
            Double hoursTrained = 0.0;
            Double milesTrained = 0.0;
            Double milesPerHourTrained = 0.0;
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));

            long msTimeOne = localDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long msTimeTwo = localDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            msTrained = (msTimeTwo - msTimeOne);
            hoursTrained = ((double) (msTrained)) / 1000 / 60 / 60;

            milesTrained = ((coordinateDataDifference.getDistanceDifference() / 1000) * kmToMilesConstant);
            milesPerHourTrained = (milesTrained / hoursTrained);
            speedsArrayList.add(milesPerHourTrained);

        }

        return speedsArrayList;
    }

    /**
     * Calculates the speed between each pair of points in kilometres per hour, and adds it to an ArrayList. This is based
     * on the speeds which are calculated in coordinate data difference.
     *
     * @return an ArrayList containing the speeds across each pair of points, in kilometres per hour.
     */
    private ArrayList<Double> calculateKphSpeedsBetweenPoints() {

        ArrayList<Double> speedsArrayList = new ArrayList<Double>();
        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();
        //ArrayList<Double> gradientsPerPairOfPoints = new ArrayList<Double>();

        if ((localCoordinatesArrayList.size() < 2) || (localDateTimes.size() < 2)) {
            speedsArrayList.add(0.0);
            return speedsArrayList;

        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
            speedsArrayList.add(0.0);
            return speedsArrayList;
        }

        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {
            long msTrained = 0;
            Double hoursTrained = 0.0;
            Double kilometresTrained = 0.0;
            Double kilometresPerHourTrained = 0.0;
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));

            long msTimeOne = localDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long msTimeTwo = localDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            msTrained = (msTimeTwo - msTimeOne);
            hoursTrained = ((double) (msTrained)) / 1000 / 60 / 60;

            kilometresTrained = ((coordinateDataDifference.getDistanceDifference() / 1000));
            kilometresPerHourTrained = (kilometresTrained / hoursTrained);
            speedsArrayList.add(kilometresPerHourTrained);

        }

        return speedsArrayList;
    }

    /**
     * Calculates the distance between each pair of points in kilometres, and adds it to an ArrayList. This is based
     * on the distances which are calculated in coordinate data difference.
     *
     * @return an ArrayList containing the distances between each pair of points, in kilometres.
     */
    private ArrayList<Double> calculateKmDistancesBetweenPoints() {

        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();

        ArrayList<Double> kilometresTrained = new ArrayList<Double>();


        if ((localCoordinatesArrayList.size() < 2) || (localDateTimes.size() < 2)) {
            kilometresTrained.add(0.0);
            return kilometresTrained;

        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
            kilometresTrained.add(0.0);
            return kilometresTrained;
        }

        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {

            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));

            kilometresTrained.add((coordinateDataDifference.getDistanceDifference() / 1000));

        }

        return kilometresTrained;
    }

    /**
     * Calculates the distance between each pair of points in miles, and adds it to an ArrayList. This is based
     * on the distances which are calculated in coordinate data difference.
     *
     * @return an ArrayList containing the distances between each pair of points, in miles.
     */
    private ArrayList<Double> calculateMilesDistancesBetweenPoints() {

        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();

        ArrayList<Double> milesTrained = new ArrayList<Double>();


        if ((localCoordinatesArrayList.size() < 2) || (localDateTimes.size() < 2)) {
            milesTrained.add(0.0);
            return milesTrained;

        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
            milesTrained.add(0.0);
            return milesTrained;
        }

        final double kmToMilesConstant = 0.62137119223733;
        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {

            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));

            milesTrained.add(((coordinateDataDifference.getDistanceDifference() / 1000) * kmToMilesConstant));

        }

        return milesTrained;
    }

    /**
     * Calculates the difference in gradient between every pair of points, using the gradient calculation in the
     * coordinate data difference class.
     *
     * @return the gradient between every pair of points as an ArrayList of doubles.
     */
    private ArrayList<Double> calculateGradientsBetweenPoints() {

        ArrayList<Double> gradients = new ArrayList<Double>();

        for (int i = 0; i < coordinatesArrayList.size() - 1; i++) {
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(coordinatesArrayList.get(i), coordinatesArrayList.get(i + 1));

            gradients.add(coordinateDataDifference.getGradient());
        }

        return gradients;
    }

    /**
     * Calculates the number of milliseconds of exercise between each pair of points, by converting both to milliseconds
     * and subtracting the smaller from the larger.
     *
     * @return an ArrayList containing the number of milliseconds between each pair of points.
     */
    private ArrayList<Long> calculateMillisecondsOfExerciseBetweenPoints() {
        ArrayList<Long> millisecondsTrained = new ArrayList<Long>();

        if (this.allDateTimes.size() < 2) {
            millisecondsTrained.add((long) 0);
            return millisecondsTrained;
        }

        for (int i = 0; i < this.allDateTimes.size() - 1; i++) {
            long msTimeOne = this.allDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long msTimeTwo = this.allDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            millisecondsTrained.add((msTimeTwo - msTimeOne));
        }

        return millisecondsTrained;
    }

    /**
     * Calculates the maximum stress level by taking the last value from a sorted copy of the ArrayList of stress levels.
     *
     * @return returns the maximum stress level generated from the activity data.
     */
    private Integer calculateStressLevelMax() {
        ArrayList<Integer> stressLevelsCopy = new ArrayList<>(getStressLevelsBetweenPoints());

        Collections.sort(stressLevelsCopy);

        return stressLevelsCopy.get(0);
    }

    /**
     * Calculates the minimum stress level by taking the first value from a sorted copy of the ArrayList of stress levels.
     *
     * @return returns the minimum stress level generated from the activity data.
     */
    private Integer calculateStressLevelMin() {
        ArrayList<Integer> stressLevelsCopy = new ArrayList<>(getStressLevelsBetweenPoints());

        Collections.sort(stressLevelsCopy);

        return stressLevelsCopy.get((stressLevelsCopy.size() - 1));
    }

    /**
     * Calculates the stress level between each pair of points by subtracting the stress level of one point from the
     * stress level of the next point. That value is the heart rate variance for that pair of values. That variance is
     * the best measure of stress: when a persons heart rate variance is large, they are not stressed, but when it is
     * low, they are stressed.
     *
     * @return returns the minimum stress level generated from the activity data.
     */
    private ArrayList<Integer> calculateStressLevelsBetweenPoints() {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> localHeartRates = this.heartRateData.getHeartRateList();

        if (localHeartRates.size() > 1) {
            for (int i = 0; i < (localHeartRates.size() - 1); i++) {
                result.add(100 - (Math.abs(localHeartRates.get(i) - localHeartRates.get(i + 1))));
            }
        } else {
            result.add(0);
        }

        return result;
    }

    /**
     * This function reverses the heart rate variance stress levels so that they are higher the more stressed the person
     * is. Then, it calculates the percentage of stress that they are currently at (out of 100). The maximum stress
     * level(100%) is the lowest heart rate variance(and therefore highest stress level) measured for that activity, and
     * the minimum stress level(0%) is the highest heart rate variance (and therefore lowest stress level) measured for
     * that activity.
     *
     * @return the "percentage of stress" out of the total activity's max and min stress levels.
     */
    private ArrayList<Double> calculateStressProportionsBetweenPoints() {
        ArrayList<Integer> stressLevels = new ArrayList<>(getStressLevelsBetweenPoints());
        Integer stressMax = new Integer(getStressLevelMax());
        Integer stressMin = new Integer(getStressLevelMin());
        ArrayList<Double> result = new ArrayList<>();

        for (int i = 0; i < stressLevels.size(); i++) {
            double stressProportionRatio = (((double)(stressLevels.get(i) - stressMin)) /
                    ((double)(stressMax - stressMin)));
            result.add((100 - (stressProportionRatio * 100.0)));
            //System.out.println((100 - (stressProportionRatio * 100.0)) + " Stress: " + stressMax + "  " + stressMin);
        }
        //System.out.println(result + " Stress");
        return result;
    }


    /**
     * Calculates the calories burned based on the user statistics, the heart rates, and the time interval the
     * exercise is performed over.
     *
     * @return an ArrayList containing the total calories burned between each pair of points in the activity.
     */
    ArrayList<Double> calculateCaloriesBurnedBetweenPointsFromUserStatsAndHeartRateAndTime() {

        ArrayList<Long> localMillisecondsOfExerciseBetweenPoints = new ArrayList<>(this.getMillisecondsOfExerciseBetweenPoints());
        ArrayList<Integer> localHeartRateList = new ArrayList<>(this.heartRateData.getHeartRateList());
        ArrayList<Integer> averageHeartRateBetweenPoints = new ArrayList<Integer>();
        User localCurrentUser = getCurrentUser();
        ArrayList<Double> calories = new ArrayList<Double>();

        for (int i = 0; i < (localHeartRateList.size() - 1); i++) {
            averageHeartRateBetweenPoints.add((int)((((double)localHeartRateList.get(i)) +
                    ((double)localHeartRateList.get(i + 1)) / 2)));
        }
        //Uncomment the three lines here once biological sex is added.
        for (int i = 0; i < (localMillisecondsOfExerciseBetweenPoints.size()); i += 1) {
            if (localCurrentUser.getSex() == Sex.MALE) {
                //System.out.println(localMillisecondsOfExerciseBetweenPoints.get(i) + "    " + (((double)(localMillisecondsOfExerciseBetweenPoints.get(i))) / 1000.0) + " ALERT!");
                calories.add(((-55.0969 + (0.6309 * averageHeartRateBetweenPoints.get(i)) + (0.1988 * localCurrentUser.getWeight())
                        + (0.2017 * localCurrentUser.getAge()))/4.184) * 60 * (((double)(localMillisecondsOfExerciseBetweenPoints.get(i)) / 1000) / 60 / 60));
            } else if (localCurrentUser.getSex() == Sex.FEMALE) {
                calories.add(((-20.4022 + (0.4472 * averageHeartRateBetweenPoints.get(i)) - (0.1263 * localCurrentUser.getWeight()) +
                        (0.074 * localCurrentUser.getAge()))/4.184) * 60 * (((double)(localMillisecondsOfExerciseBetweenPoints.get(i)) / 1000) / 60 / 60));
            }
        }
        return calories;
    }

    public boolean getIsGraphable() {
        return isGraphable;
    }

    public void setIsGraphable(boolean isGraphabe) {
        this.isGraphable = isGraphable;
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

    public Double getDataSpeedKph() {
        return dataSpeedKph;
    }

    public Double getDataSpeedMph() {
        return dataSpeedMph;
    }

    public ArrayList<Double> getMphSpeedsBetweenPoints() {
        return mphSpeedsBetweenPoints;
    }

    public ArrayList<Double> getKphSpeedsBetweenPoints() {
        return kphSpeedsBetweenPoints;
    }

    public ArrayList<Double> getGradientsBetweenPoints() {
        return gradientsBetweenPoints;
    }

    public ArrayList<Long> getMillisecondsOfExerciseBetweenPoints() {
        return millisecondsOfExerciseBetweenPoints;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Double> getKmDistancesBetweenPoints() {
        return kmDistancesBetweenPoints;
    }

    public ArrayList<Double> getMilesDistancesBetweenPoints() {
        return milesDistancesBetweenPoints;
    }

    public ArrayList<Integer> getStressLevelsBetweenPoints() {
        return stressLevelsBetweenPoints;
    }

    public Integer getStressLevelMax() {
        return stressLevelMax;
    }

    public Integer getStressLevelMin() {
        return stressLevelMin;
    }

    public Integer getDataId() { return  dataId;}

    public void setDataId(Integer dataId) {this.dataId = dataId;}

    public ArrayList<Double> getStressProportionsBetweenPoints() {
        return stressProportionsBetweenPoints;
    }




    public abstract double getConsumedCalories();
    //Note: The two arrays in each activity data child read as follows: the MET value at index
    //x of the mets array is valid for calculation for speeds up to the value x of the speeds
    //array.
    public abstract ArrayList<Double> getConsumedCaloriesBetweenPoints();

    public void setDataSpeedKph(Double dataSpeedKph) {
        this.dataSpeedKph = dataSpeedKph;
    }
}

