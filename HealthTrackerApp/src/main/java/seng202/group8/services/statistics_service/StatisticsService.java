package seng202.group8.services.statistics_service;

public class StatisticsService {

    public Integer getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(Integer averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }

    public Double getKmRunWeek() {
        return kmRunWeek;
    }

    public void setKmRunWeek(Double kmRunWeek) {
        this.kmRunWeek = kmRunWeek;
    }

    public Double getmSwamWeek() {
        return mSwamWeek;
    }

    public void setmSwamWeek(Double mSwamWeek) {
        this.mSwamWeek = mSwamWeek;
    }

    public Double getKmBikedWeek() {
        return kmBikedWeek;
    }

    public void setKmBikedWeek(Double kmBikedWeek) {
        this.kmBikedWeek = kmBikedWeek;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Double getCaloriesBurnedWeek() {
        return caloriesBurnedWeek;
    }

    public void setCaloriesBurnedWeek(Double caloriesBurnedWeek) {
        this.caloriesBurnedWeek = caloriesBurnedWeek;
    }
    public Double getWeightLossWeek() {
        return weightLossWeek;
    }

    public void setWeightLossWeek(Double weightLossWeek) {
        this.weightLossWeek = weightLossWeek;
    }


    public Integer averageHeartRate;
    public Double kmRunWeek;
    public Double mSwamWeek;
    public Double kmBikedWeek;
    public String healthStatus;
    public Double caloriesBurnedWeek;
    public Double weightLossWeek;

}
