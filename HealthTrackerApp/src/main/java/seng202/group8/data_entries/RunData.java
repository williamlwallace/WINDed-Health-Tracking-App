package seng202.group8.data_entries;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import seng202.group8.user.User;

public class RunData extends NotAssistedSportsData{
    public RunData(String newTitle, DataType dataType, ArrayList<LocalDateTime> newDateTimes, ArrayList<CoordinateData> newCoordinatesList, ArrayList<Integer> newHeartRateList, User theCurrentUser) {
        super(newTitle, dataType, newDateTimes, newCoordinatesList, newHeartRateList, theCurrentUser);

    }

    public double getConsumedCalories() {
        /** Calculates the calories burned whilst running based on a MET intensity value, which is determined from the
         * speed in miles per hour. Note that the MET intensity values are provided by the american compendium of
         * physical activities, and thus the calorie calculations are based on miles per hour. MPH is conserved to make
         * comparisons between the speed travelled at, and the MET value bounds that match that speed, more accurate.
         *
         * To improve accuracy, the incline of the slope is taken into account.
         */
        Double[] metValuesArray = {6.0, 8.3, 9.8, 11.0, 11.8, 12.8, 14.5, 16.0, 19.0, 19.8, 23.0};
        Double[] speedDivisionsArray = {4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0};

        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));


        Double calorieTotal = calculateCaloriesFromStats(metValues, speedDivisions);

        return calorieTotal;
    }

    public ArrayList<Double> getConsumedCaloriesBetweenPoints() {

        Double[] metValuesArray = {6.0, 8.3, 9.8, 11.0, 11.8, 12.8, 14.5, 16.0, 19.0, 19.8, 23.0};
        Double[] speedDivisionsArray = {4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0};

        ArrayList<Double> metValues = new ArrayList<Double>(Arrays.asList(metValuesArray));
        ArrayList<Double> speedDivisions = new ArrayList<Double>(Arrays.asList(speedDivisionsArray));
        ArrayList<Double> caloriesList;

        caloriesList = calculateCaloriesBurnedBetweenPointsFromStats(metValues, speedDivisions);
        return caloriesList;
    }

//        ArrayList<Double> localMphSpeedsBetweenPoints = this.getMphSpeedsBetweenPoints();
//        ArrayList<Double> localGradientsBetweenPoints = this.getGradientsBetweenPoints();
//        ArrayList<Long> localMillisecondsOfExerciseBetweenPoints = this.getMillisecondsOfExerciseBetweenPoints();
//
//        ArrayList<Double> calorieSpeedMets = calorieCalculationSetup(metValues, speedDivisions);
//        Double calories = 0.0;
//
//        for (int i = 0; i < (calorieSpeedMets.size()); i++) {
//            double caloriesBurned = ((calorieSpeedMets.get(i) * User.getWeight()) * ((double)(localMillisecondsOfExerciseBetweenPoints.get(i)) / 1000 / 60 / 60));
//            double caloriesBurnedWithIncline = caloriesBurned + (caloriesBurned * ((localGradientsBetweenPoints.get(i) / 100) * 0.12));
//            calories += caloriesBurnedWithIncline;
//        }
//
//        return calories;
//    }

//    private ArrayList<Double> calorieCalculationSetup(ArrayList<Double> metValues, ArrayList<Double> speedDivisions) {
//
//        ArrayList<Double> mphSpeeds = this.getMphSpeedsBetweenPoints();
//        ArrayList<Double> results = new ArrayList<Double>();
//
//        for (int speed = 0; speed < mphSpeeds.size(); speed += 1) {
//            //for (int divs = 0; divs < speedDivisions.size() - 1; mets += 1) {
//            int mets = 0;
//            for (int divs = 0; mphSpeeds.get(speed) < speedDivisions.get(divs); divs += 1) {
//                mets += 1;
//            }
//            results.add(metValues.get(mets));
//        }
//
//        return results;
//        //if (this.getHeartRateList() == 0);
//    }

//    private ArrayList<Double> calculateMPHSpeedsBetweenPoints() {
//
//        ArrayList<Double> speedsArrayList = new ArrayList<Double>();
//        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
//        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();
//        ArrayList<Double> gradientsPerPairOfPoints = new ArrayList<Double>();
//
//        if (localCoordinatesArrayList.size() < 2) {
//            speedsArrayList.set(0, 0.0);
//            return speedsArrayList;
//        } else if (localDateTimes.size() < 2) {
//            speedsArrayList.set(0, 0.0);
//            return speedsArrayList;
//        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
////            speedsArrayList.set(0, 0.0);
//            return speedsArrayList;
//        }
//
//        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {
//            final double kmToMilesConstant = 0.62137119223733;
//            long msTrained = 0;
//            Double hoursTrained = 0.0;
//            Double milesTrained = 0.0;
//            Double milesPerHourTrained = 0.0;
//            CoordinateDataDifference coordinateDataDifference =
//                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));
//
//            long msTimeOne = localDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//            long msTimeTwo = localDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//
//            msTrained = (msTimeTwo - msTimeOne);
//            hoursTrained = ((double)(msTrained)) / 1000 / 60 / 60;
//
//            milesTrained = ((coordinateDataDifference.getDistanceDifference() / 1000) * kmToMilesConstant);
//            milesPerHourTrained = (milesTrained / hoursTrained);
//            speedsArrayList.add(milesPerHourTrained);
//
//        }
//
//        return speedsArrayList;
//    }
//
//    private ArrayList<Double> calculateKPHSpeedsBetweenPoints() {
//
//        ArrayList<Double> speedsArrayList = new ArrayList<Double>();
//        ArrayList<CoordinateData> localCoordinatesArrayList = this.getCoordinatesArrayList();
//        ArrayList<LocalDateTime> localDateTimes = this.getAllDateTimes();
//        ArrayList<Double> gradientsPerPairOfPoints = new ArrayList<Double>();
//
//        if (localCoordinatesArrayList.size() < 2) {
//            speedsArrayList.set(0, 0.0);
//            return speedsArrayList;
//        } else if (localDateTimes.size() < 2) {
//            speedsArrayList.set(0, 0.0);
//            return speedsArrayList;
//        } else if (localDateTimes.size() != localCoordinatesArrayList.size()) {
////            speedsArrayList.set(0, 0.0);
//            return speedsArrayList;
//        }
//
//        for (int i = 0; i < (localCoordinatesArrayList.size()) - 1; i++) {
//            long msTrained = 0;
//            Double hoursTrained = 0.0;
//            Double kilometresTrained = 0.0;
//            Double kilometresPerHourTrained = 0.0;
//            CoordinateDataDifference coordinateDataDifference =
//                    new CoordinateDataDifference(localCoordinatesArrayList.get(i), localCoordinatesArrayList.get(i + 1));
//
//            long msTimeOne = localDateTimes.get(i).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//            long msTimeTwo = localDateTimes.get(i + 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//
//            msTrained = (msTimeTwo - msTimeOne);
//            hoursTrained = ((double)(msTrained)) / 1000 / 60 / 60;
//
//            kilometresTrained = ((coordinateDataDifference.getDistanceDifference() / 1000));
//            kilometresPerHourTrained = (kilometresTrained / hoursTrained);
//            speedsArrayList.add(kilometresPerHourTrained);
//
//        }
//
//        return speedsArrayList;
//    }

}

//    public RunData(String newTitle, DataType dataType, ArrayList<Double> newCoordinatesList, ArrayList<Integer> newHeartRateList) {
//        super(newTitle, dataType, newCoordinatesList, newHeartRateList);
//    }
//
//    double getConsumedCalories(String activityType) {
//        return 0;
//    }
//}
