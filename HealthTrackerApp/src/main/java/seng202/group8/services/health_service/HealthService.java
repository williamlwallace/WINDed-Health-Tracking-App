package seng202.group8.services.health_service;


import seng202.group8.activity_collection.ActivityListCollectionObserver;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.services.Service;
import seng202.group8.user.User;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 *@author lfa69
 * A maximum person heart rate should be equal to: max_heart_rate =  220 - person_age.
 * To be considered within standards a person heart rate should be between:
 * min_standard_heart_rate = max_heart_rate * 0.50 and max_standard_heart_rate = max_heart_rate * 0.85 .
 *
 *
 * GENERAL FORMULA: min_standard_heart_rate <= acceptable_heart_rate <= max_standard_heart_rate
 *
 */
public class HealthService extends Service implements ActivityListCollectionObserver {

    private static final int MAX_LIGHT_WORK_HEART_RATE = 100;
    private static final int MIN_LIGHT_WORK_HEART_RATE = 60;
    private static final int LIGHT_WORK_CARDIOVASCULAR_RISK_HEART_RATE = 83;


    private boolean isTachicardic;
    private boolean isBradicardic;
    private boolean isAtCardiovasMortalityRisk;
    private boolean isUserNotExercising;

    private int maxAcceptableHeartRate;

    public HealthService(User user) {
        super(user);
        user.getUserActivities().attach(this);
        isTachicardic = false;
        isBradicardic = false;
        isAtCardiovasMortalityRisk = false;
        maxAcceptableHeartRate = (int) ((220 - getUser().getAge()) * 0.85);
    }

    /**
     *
     * @return an ArrayList<Data> object containing those data points where the
     * the heart rate is too high to categorise the user as bradicardic.
     */
    public ArrayList<Data> checkIsTachicardic() {

        ArrayList<Data> valuesAtRisk = new ArrayList<Data>();

        ArrayList<Data> walkData =
                getUser().getUserActivities().retrieveSameTypeActivities(DataType.WALK, new Date(0));
        //////////////////////////
        //This wants to be a proof of concept, doing so I can keep the complexity to n and collect
        // those items that could yield the dangerous heart rate.
        ArrayList<Data> runData =
                getUser().getUserActivities().retrieveSameTypeActivities(DataType.RUN, new Date(0));

        ArrayList<Data> swimData =
                getUser().getUserActivities().retrieveSameTypeActivities(DataType.SWIM, new Date(0));
        ArrayList<Data> bikeData =
                getUser().getUserActivities().retrieveSameTypeActivities(DataType.BIKE, new Date(0));

        ArrayList<Data> lightWorkData = new ArrayList<Data>();
        lightWorkData.addAll(walkData);

        ArrayList<Data> heavyWorkData = new ArrayList<Data>();
        heavyWorkData.addAll(runData);
        heavyWorkData.addAll(swimData);
        heavyWorkData.addAll(bikeData);


        for (Data data : heavyWorkData) {
            for (Integer heartRate : data.getHeartRateList()) {
                if (heartRate >= maxAcceptableHeartRate) {
                    valuesAtRisk.add(data);
                }
            }
        }

        for (Data data : lightWorkData) {
            for (Integer heartRate : data.getHeartRateList()) {
                if (heartRate > HealthService.MAX_LIGHT_WORK_HEART_RATE) {
                    valuesAtRisk.add(data);
                }
            }
        }

        // 2 is an arbitrary number to exclude the possiblility of mistakes, might have to change this.
        if (valuesAtRisk.size() > 2) {
            isTachicardic = true;
        }

        return valuesAtRisk;
    }


    /**
     *
     * @return an ArrayList<Data> object containing those data points where the
     * the heart rate is too low to categorise the user as bradicardic.
     */
    public ArrayList<Data> checkIsBradicardic() {
        ArrayList<Data> valuesAtRisk = new ArrayList<Data>();

        ArrayList<Data> walkData =
                getUser().getUserActivities().retrieveSameTypeActivities(DataType.WALK, new Date(0));

        ArrayList<Data> lightWorkData = new ArrayList<Data>();
        lightWorkData.addAll(walkData);

        for (Data data : lightWorkData) {
            for (Integer heartRate : data.getHeartRateList()) {
                if (heartRate < HealthService.MIN_LIGHT_WORK_HEART_RATE) {
                    valuesAtRisk.add(data);
                }
            }
        }

        // 2 is an arbitrary number to exclude the possiblility of mistakes, might have to change this.
        if (valuesAtRisk.size() > 2) {
            isBradicardic = true;
        }

        return valuesAtRisk;
    }


    /**
     *
     * @return an ArrayList<Data> object containing those data points where the
     * the heart rate is too low to categorise the user at cardiovascular risk.
     */
    public ArrayList<Data> checkIsAtCardiovascularRisk() {
        ArrayList<Data> valuesAtRisk = new ArrayList<Data>();

        ArrayList<Data> walkData =
                getUser().getUserActivities().retrieveSameTypeActivities(DataType.WALK, new Date(0));

        ArrayList<Data> lightWorkData = new ArrayList<Data>();
        lightWorkData.addAll(walkData);

        for (Data data : lightWorkData) {
            for (Integer heartRate : data.getHeartRateList()) {
                if (heartRate > HealthService.LIGHT_WORK_CARDIOVASCULAR_RISK_HEART_RATE) {
                    valuesAtRisk.add(data);
                }
            }
        }

        // 2 is an arbitrary number to exclude the possiblility of mistakes, might have to change this.
        if (valuesAtRisk.size() > 2) {
            isAtCardiovasMortalityRisk = true;
        }

        return valuesAtRisk;
    }

    /**
     *
     * Checks if user is training enough, if not modifies the value of the isUserNotExercising
     */
    public void checkUserIsNotTraining() {
        Date twoWeeksAgoDate = new Date();
        twoWeeksAgoDate.setTime((long) (twoWeeksAgoDate.getTime() - (2 * 6.048e8))); // date of 2 weeks ago;
        ArrayList<Data> activitiesOfLastTwoWeeks =
                getUser().getUserActivities().retrieveActivititesBeforeDate(twoWeeksAgoDate);

        isUserNotExercising = activitiesOfLastTwoWeeks.size() < 3;
    }


    /**
     *
     * @return the isTachicardic parameter
     */
    public boolean isTachicardic() {
        return isTachicardic;
    }

    /**
     *
     * @param tachicardic a new boolean value to represent if the user is tachicardic
     */
    public void setTachicardic(boolean tachicardic) {
        isTachicardic = tachicardic;
    }

    /**
     *
     * @return the isBradicardic parameter
     */
    public boolean isBradicardic() {
        return isBradicardic;
    }

    /**
     *
     * @param bradicardic a new boolean value to represent if the user is bradicardic
     */
    public void setBradicardic(boolean bradicardic) {
        isBradicardic = bradicardic;
    }

    /**
     *
     * @return the isAtCardiovascularRisk parameter
     */
    public boolean isAtCardiovasMortalityRisk() {
        return isAtCardiovasMortalityRisk;
    }

    /**
     *
     * @param atCardiovasMortalityRisk a new boolean value to represent if the user is at cardiovascular disease risk
     */
    public void setAtCardiovasMortalityRisk(boolean atCardiovasMortalityRisk) {
        isAtCardiovasMortalityRisk = atCardiovasMortalityRisk;
    }

    /**
     *
     * @return the isUserNotExercising parameter
     */
    public boolean isUserNotExercising() {
        return isUserNotExercising;
    }

    /**
     *
     * @param userNotExercising a new boolean value to represent if the user is not training enough.
     */
    public void setUserNotExercising(boolean userNotExercising) {
        isUserNotExercising = userNotExercising;
    }

    /**
     * Method called every time new activities are added to the ActivityListCollection object.
     */
    public void update() {
        checkIsAtCardiovascularRisk();
        checkIsBradicardic();
        checkIsTachicardic();
    }
}
