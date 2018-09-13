package seng202.group8.data_entries;


import org.apache.commons.lang3.ObjectUtils;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;

public abstract class Data {

    /*This structure uses the coordinates list, and heart rate list as arguments to calculate the calories consumed in
     * an activity

        When there are times that are too difficult to calculate the calories
        using the coordinates, an attempt will be made to give an
        estimation purely based on the heart rate over that time period.
     * XX TESTS NOT WRITTEN JUST YET*/

    private String title;
    //    private DataType = new DataType();
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


    public Data(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {

        this.title = newTitle;
        this.currentUser = theCurrentUser;
        this.creationDate = new Date(newDateTimes.get(0).getYear(), newDateTimes.get(0).getMonthValue(), newDateTimes.get(0).getDayOfMonth());
        this.dataType = dataType;
        this.allDateTimes = newDateTimes;
        this.heartRateData = new HeartRateData(newHeartRateList);
//        setCoordinatesArrayList(newCoordinatesList);
        this.coordinatesArrayList = newCoordinatesList;
        this.distanceCovered = calculateDistanceCovered();
        this.millisecondsOfExercise = calculateMillisecondsOfExercise();
        this.dataSpeedKph = calculateDataSpeedKilometresPerHour();
        this.dataSpeedMph = calculateDataSpeedMilesPerHour();
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

    private ArrayList<Double> calculateMphSpeedsBetweenPoints() {

        ArrayList<Double> speedsArrayList = new ArrayList<Double>();
        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();
        ArrayList<Double> gradientsPerPairOfPoints = new ArrayList<Double>();

        if (localCoordinatesArrayList.size() < 2) {
            speedsArrayList.set(0, 0.0);
            return speedsArrayList;
        } else if (localDateTimes.size() < 2) {
            speedsArrayList.set(0, 0.0);
            return speedsArrayList;
        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
//            speedsArrayList.set(0, 0.0);
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

    private ArrayList<Double> calculateKphSpeedsBetweenPoints() {

        ArrayList<Double> speedsArrayList = new ArrayList<Double>();
        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();
        ArrayList<Double> gradientsPerPairOfPoints = new ArrayList<Double>();

        if (localCoordinatesArrayList.size() < 2) {
            speedsArrayList.set(0, 0.0);
            return speedsArrayList;
        } else if (localDateTimes.size() < 2) {
            speedsArrayList.set(0, 0.0);
            return speedsArrayList;
        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
//            speedsArrayList.set(0, 0.0);
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

    private ArrayList<Double> calculateKmDistancesBetweenPoints() {

        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();

        ArrayList<Double> kilometresTrained = new ArrayList<Double>();


        if (localCoordinatesArrayList.size() < 2) {
            kilometresTrained.set(0, 0.0);
            return kilometresTrained;
        } else if (localDateTimes.size() < 2) {
            kilometresTrained.set(0, 0.0);
            return kilometresTrained;
        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
//            speedsArrayList.set(0, 0.0);
            return kilometresTrained;
        }

        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {

            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));

            kilometresTrained.add((coordinateDataDifference.getDistanceDifference() / 1000));

        }

        return kilometresTrained;
    }

    private ArrayList<Double> calculateMilesDistancesBetweenPoints() {

        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();

        ArrayList<Double> milesTrained = new ArrayList<Double>();


        if (localCoordinatesArrayList.size() < 2) {
            milesTrained.set(0, 0.0);
            return milesTrained;
        } else if (localDateTimes.size() < 2) {
            milesTrained.set(0, 0.0);
            return milesTrained;
        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
//            speedsArrayList.set(0, 0.0);
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

    private ArrayList<Double> calculateGradientsBetweenPoints() {

        ArrayList<Double> gradients = new ArrayList<Double>();

        for (int i = 0; i < coordinatesArrayList.size() - 1; i++) {
            CoordinateDataDifference coordinateDataDifference =
                    new CoordinateDataDifference(coordinatesArrayList.get(i), coordinatesArrayList.get(i + 1));

            gradients.add(coordinateDataDifference.getGradient());
        }

        return gradients;
    }

    private ArrayList<Long> calculateMillisecondsOfExerciseBetweenPoints() {
        ArrayList<Long> millisecondsTrained = new ArrayList<Long>();

        if (this.allDateTimes.size() < 2) {
            return millisecondsTrained;
        }

        for (int i = 0; i < this.allDateTimes.size() - 1; i++) {
            long msTimeOne = this.allDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long msTimeTwo = this.allDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            millisecondsTrained.add((msTimeTwo - msTimeOne));
        }

        return millisecondsTrained;
    }

    private Integer calculateStressLevelMax() {
        ArrayList<Integer> stressLevelsCopy = getStressLevelsBetweenPoints();

        Collections.sort(stressLevelsCopy);

        return stressLevelsCopy.get(0);
    }

    private Integer calculateStressLevelMin() {
        ArrayList<Integer> stressLevelsCopy = getStressLevelsBetweenPoints();

        Collections.sort(stressLevelsCopy);

        return stressLevelsCopy.get((stressLevelsCopy.size() - 1));
    }

    private ArrayList<Integer> calculateStressLevelsBetweenPoints() {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> localHeartRates = this.heartRateData.getHeartRateList();

        if (localHeartRates.size() > 1) {
            for (int i = 0; i < (localHeartRates.size() - 1); i++) {
                result.add(Math.abs(localHeartRates.get(i) - localHeartRates.get(i + 1)));
            }
        } else {
            result.add(Math.abs(localHeartRates.get(0) - localHeartRates.get(0)));
        }

        return result;
    }

    private ArrayList<Double> calculateStressProportionsBetweenPoints() {
        ArrayList<Integer> stressLevels = getStressLevelsBetweenPoints();
        Integer stressMax = getStressLevelMax();
        Integer stressMin = getStressLevelMin();
        ArrayList<Double> result = new ArrayList<>();

        for (int i = 0; i < stressLevels.size(); i++) {
            double stressProportionRatio = (((double)(stressLevels.get(i) - stressMin)) /
                    ((double)(stressMax - stressMin)));
            result.add((100 - (stressProportionRatio * 100.0)));
        }
        return result;
    }

    public ArrayList<Double> calorieCalculationSetup(ArrayList<Double> metValues, ArrayList<Double> speedDivisions) {
//        Once the individual speed value that is being compared in the inner loop is greater than or
//        equal to the speed value divisions that match the met values, the met value corresponding to
//        that speed division will be associated with the value. Unless, there is a met value and speed
//        division above the current ones. If there are, the difference between the current MET value
//        and the next one is multiplied by the ratio of the current speed with the next speed division,
//        relative to the current speed. Essentially, this will generate a factor by which to multiply
//        MET value difference so that it is proportional to the difference between the current exact
//        speed, and the speed divisons before and after it. This difference is then added to the
//        resultant MET value.

//        In short, the final calorie calculation is an approximation based on the accuracy of MET
//        speed value divisions provided. (ie the range of speed between two specific MET values.)

        ArrayList<Double> mphSpeeds = this.getMphSpeedsBetweenPoints();
        ArrayList<Double> results = new ArrayList<Double>();

        for (int speed = 0; speed < mphSpeeds.size(); speed += 1) {
            //for (int divs = 0; divs < speedDivisions.size() - 1; mets += 1) {
            int mets = 0;
            double resultantMet = 0.0;
            int divs = 0;
            double speedDivisionChooser = speedDivisions.get(divs);
            while (mphSpeeds.get(speed) > speedDivisionChooser) {   //Note to self: the condition on this while loop
                                                                    // prevents speed from being higher than chooser in
                                                                    // the calculation.
                mets += 1;
                if (((divs + 1) < speedDivisions.size()) && ((mets + 1) < metValues.size())) {
                    double thisSpeedRatioWithChangeInSpeedDivision = ((mphSpeeds.get(speed) - speedDivisions.get(divs)) /
                            (speedDivisions.get(divs + 1) - speedDivisions.get(divs)));
                    double changeInMet = metValues.get(mets + 1) - metValues.get(mets);
                    resultantMet = changeInMet * thisSpeedRatioWithChangeInSpeedDivision;
                    resultantMet += metValues.get(mets);
                } else if (((mets) < metValues.size())){
                    resultantMet = metValues.get(mets);
                } else {
                    resultantMet = metValues.get(metValues.size() - 1);
                }
                divs += 1;
                if (divs < speedDivisions.size()) {
                    speedDivisionChooser = speedDivisions.get(divs);
                } else {
                    break;  //The current resultantMet value will be used, because there are
                            //no further speedDivisions, so the loop would not be able to
                            //continue past this point.
                }
            }
            results.add(resultantMet);
        }

        return results;
        //if (this.getHeartRateList() == 0);
    }

    public Double calculateCaloriesFromStats(ArrayList<Double> metValues, ArrayList<Double> speedDivisions) {

        ArrayList<Double> localMphSpeedsBetweenPoints = this.getMphSpeedsBetweenPoints();
        ArrayList<Double> localGradientsBetweenPoints = this.getGradientsBetweenPoints();
        ArrayList<Long> localMillisecondsOfExerciseBetweenPoints = this.getMillisecondsOfExerciseBetweenPoints();

        ArrayList<Double> calorieSpeedMets = calorieCalculationSetup(metValues, speedDivisions);
        Double calories = 0.0;

        for(int i = 0; i<(calorieSpeedMets.size());i++) {
            double caloriesBurned = ((calorieSpeedMets.get(i) * this.currentUser.getWeight()) * ((double) (localMillisecondsOfExerciseBetweenPoints.get(i)) / 1000 / 60 / 60));
            double caloriesBurnedWithIncline = caloriesBurned + (caloriesBurned * ((localGradientsBetweenPoints.get(i) / 100) * 0.12));
            calories += caloriesBurnedWithIncline;
        }

        return calories;
    }

    public Double calculateCaloriesFromUserStatsAndHeartRateAndTime() {
        //NOTE JACK NEEDS TO ADD BIOLOGICAL SEX TO USER STATS for this to work.
        ArrayList<Long> localMillisecondsOfExerciseBetweenPoints = this.getMillisecondsOfExerciseBetweenPoints();
        ArrayList<Integer> localHeartRateList = this.heartRateData.getHeartRateList();
        ArrayList<Integer> averageHeartRateBetweenPoints = new ArrayList<Integer>();
        User localCurrentUser = getCurrentUser();
        double calories = 0.0;

        for (int i = 0; i < (localHeartRateList.size() - 1); i++) {
            averageHeartRateBetweenPoints.add((int)((((double)localHeartRateList.get(i)) +
                    ((double)localHeartRateList.get(i + 1)) / 2)));
        }
        //Uncomment the three lines here once biological sex is added.
        for (int i = 0; i < (localMillisecondsOfExerciseBetweenPoints.size()); i += 1) {
            //if (localCurrentUser.getSex() == MALE) {
                calories += ((-55.0969 + (0.6309 * averageHeartRateBetweenPoints.get(i)) + (0.1988 * localCurrentUser.getWeight())
                        + (0.2017 * localCurrentUser.getAge()))/4.184) * 60 * localMillisecondsOfExerciseBetweenPoints.get(i);
            //} else if (localCurrentUser.getSex() == FEMALE) {
                calories += ((-20.4022 + (0.4472 * averageHeartRateBetweenPoints.get(i)) - (0.1263 * localCurrentUser.getWeight()) +
                        (0.074 * localCurrentUser.getAge()))/4.184) * 60 * localMillisecondsOfExerciseBetweenPoints.get(i);
            //}
        }
        return calories;
    }

    public ArrayList<Double> calculateCaloriesBurnedBetweenPointsFromStats(ArrayList<Double> metValues, ArrayList<Double> speedDivisions) {

        ArrayList<Double> localMphSpeedsBetweenPoints = this.getMphSpeedsBetweenPoints();
        ArrayList<Double> localGradientsBetweenPoints = this.getGradientsBetweenPoints();
        ArrayList<Long> localMillisecondsOfExerciseBetweenPoints = this.getMillisecondsOfExerciseBetweenPoints();

        ArrayList<Double> calorieSpeedMets = calorieCalculationSetup(metValues, speedDivisions);
        ArrayList<Double> calories = new ArrayList<Double>();

        for(int i = 0; i<(calorieSpeedMets.size());i++) {
            double caloriesBurned = ((calorieSpeedMets.get(i) * this.currentUser.getWeight()) * ((double) (localMillisecondsOfExerciseBetweenPoints.get(i)) / 1000 / 60 / 60));
            double caloriesBurnedWithIncline = caloriesBurned + (caloriesBurned * ((localGradientsBetweenPoints.get(i) / 100) * 0.12));
            calories.add(caloriesBurnedWithIncline);
        }

        return calories;
    }

    public ArrayList<Double> calculateCaloriesBurnedBetweenPointsFromUserStatsAndHeartRateAndTime() {
        //NOTE JACK NEEDS TO ADD BIOLOGICAL SEX TO USER STATS for this to work.
        ArrayList<Long> localMillisecondsOfExerciseBetweenPoints = this.getMillisecondsOfExerciseBetweenPoints();
        ArrayList<Integer> localHeartRateList = this.heartRateData.getHeartRateList();
        ArrayList<Integer> averageHeartRateBetweenPoints = new ArrayList<Integer>();
        User localCurrentUser = getCurrentUser();
        ArrayList<Double> calories = new ArrayList<Double>();

        for (int i = 0; i < (localHeartRateList.size() - 1); i++) {
            averageHeartRateBetweenPoints.add((int)((((double)localHeartRateList.get(i)) +
                    ((double)localHeartRateList.get(i + 1)) / 2)));
        }
        //Uncomment the three lines here once biological sex is added.
        for (int i = 0; i < (localMillisecondsOfExerciseBetweenPoints.size()); i += 1) {
            //if (localCurrentUser.getSex() == MALE) {
            calories.add(((-55.0969 + (0.6309 * averageHeartRateBetweenPoints.get(i)) + (0.1988 * localCurrentUser.getWeight())
                    + (0.2017 * localCurrentUser.getAge()))/4.184) * 60 * localMillisecondsOfExerciseBetweenPoints.get(i));
            //} else if (localCurrentUser.getSex() == FEMALE) {
            calories.add(((-20.4022 + (0.4472 * averageHeartRateBetweenPoints.get(i)) - (0.1263 * localCurrentUser.getWeight()) +
                    (0.074 * localCurrentUser.getAge()))/4.184) * 60 * localMillisecondsOfExerciseBetweenPoints.get(i));
            //}
        }
        return calories;
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

    public ArrayList<Double> getStressProportionsBetweenPoints() {
        return stressProportionsBetweenPoints;
    }

    public abstract double getConsumedCalories();
    //Note: The two arrays in each activity data child read as follows: the MET value at index
    //x of the mets array is valid for calculation for speeds up to the value x of the speeds
    //array.
    public abstract ArrayList<Double> getConsumedCaloriesBetweenPoints();
}

