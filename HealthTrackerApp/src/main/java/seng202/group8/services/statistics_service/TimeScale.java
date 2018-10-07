package seng202.group8.services.statistics_service;

import java.util.ArrayList;

public class TimeScale {

    private ArrayList<Double> times;
    private String scale;


    public TimeScale(ArrayList<Double> newTimes, String newScale) {
        this.times = new ArrayList<>(newTimes);
        this.scale = newScale;

    }


    public ArrayList<Double> getTimes() {
        return times;
    }


    public void setTimes(ArrayList<Double> times) {
        this.times = times;
    }


    public String getScale() {
        return scale;
    }


    public void setScale(String scale) {
        this.scale = scale;
    }

}
