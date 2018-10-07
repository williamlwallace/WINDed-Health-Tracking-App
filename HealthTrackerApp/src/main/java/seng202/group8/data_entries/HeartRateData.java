package seng202.group8.data_entries;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the heart rate data class definition. The purpose of this class is to create an object
 * where all the information related to the user's activity data heart rate list is stored. This includes
 * the heart rate list, along with several calculated statistics based on that list. This object makes the
 * heart rate list and its statistics easy to pass arounf between functions and classes.
 *
 * @author cmc280
 *
 */
public class HeartRateData {

    private ArrayList<Integer> heartRateList;
    private ArrayList<Integer> sortedHeartRateList;
    private int meanAverageHeartRate;
    private int medianIndex;
    private int medianHeartRate;
    private int standardDeviation;

    private int highestHeartRate;
    private int lowestHeartRate;


    public int getMeanAverageHeartRate() {

        int total = 0;
        for (int i = 0; i < this.heartRateList.size(); i += 1) {
            total += heartRateList.get(i);
        }
        total = total / heartRateList.size();
        return total;
    }


    /**
     * This is the HeartRateData class constructor. Its primary purpose is to
     * create an object in which to store the heart rate list along with information
     * about the heart rate list. From the heart rate list, various class attributes
     * are calculated.
     *
     * @param heartRateList the heart rate list that needs to be stored as a HeartRateData object.
     */
    public HeartRateData(ArrayList<Integer> heartRateList) {
        this.heartRateList = heartRateList;

        sortedHeartRateList = new ArrayList<>(this.heartRateList);
        Collections.sort(sortedHeartRateList);
        if (this.heartRateList.size() > 1) {
            /**Calculate the median heart rate and standard deviation **/
            this.meanAverageHeartRate = getMeanAverageHeartRate();
            this.medianIndex = (sortedHeartRateList.size() / 2);     /** Note this is floor division **/
            this.medianHeartRate = sortedHeartRateList.get(medianIndex);
            this.standardDeviation = sortedHeartRateList.get(medianIndex) - sortedHeartRateList.get(0);
            this.highestHeartRate = sortedHeartRateList.get(sortedHeartRateList.size() - 1);
            this.lowestHeartRate = sortedHeartRateList.get(0);

        } else if (this.heartRateList.size() == 1) {
            this.meanAverageHeartRate = getMeanAverageHeartRate();
            this.medianIndex = 0;
            this.medianHeartRate = sortedHeartRateList.get(medianIndex);
            this.standardDeviation = sortedHeartRateList.get(medianIndex) - sortedHeartRateList.get(0);
            this.highestHeartRate = sortedHeartRateList.get(0);
            this.lowestHeartRate = sortedHeartRateList.get(0);

        } else {
            this.meanAverageHeartRate = 0;
            this.medianIndex = 0;
            this.medianHeartRate = 0;
            this.standardDeviation = 0;
            this.highestHeartRate = 0;
            this.lowestHeartRate = 0;
        }
    }


    public ArrayList<Integer> getHeartRateList() {
        return heartRateList;
    }

    public void setHeartRateList(ArrayList<Integer> heartRateList) {
        this.heartRateList = heartRateList;
    }

    public void setMeanAverageHeartRate(int meanAverageHeartRate) {
        this.meanAverageHeartRate = meanAverageHeartRate;
    }

    public int getMedianIndex() {
        return medianIndex;
    }

    public void setMedianIndex(int medianIndex) {
        this.medianIndex = medianIndex;
    }

    public int getMedianHeartRate() {
        return medianHeartRate;
    }

    public void setMedianHeartRate(int medianHeartRate) {
        this.medianHeartRate = medianHeartRate;
    }

    public int getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(int standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public int getHighestHeartRate() {
        return highestHeartRate;
    }

    private void setHighestHeartRate() {
        if (this.sortedHeartRateList.size() == 0) {
            this.highestHeartRate = 0;
        } else {
            this.highestHeartRate = sortedHeartRateList.get(sortedHeartRateList.size() - 1);
        }
    }

    public int getLowestHeartRate() {
        return lowestHeartRate;
    }

    private void setLowestHeartRate() {
        if (this.sortedHeartRateList.size() == 0) {
            this.lowestHeartRate = 0;
        } else {
            this.lowestHeartRate = sortedHeartRateList.get(0);
        }
    }
}
