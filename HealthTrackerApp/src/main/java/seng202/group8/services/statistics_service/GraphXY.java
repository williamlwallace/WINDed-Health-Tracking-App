package seng202.group8.services.statistics_service;

import java.util.ArrayList;

public class GraphXY {

    /**
     * X and Y axis arrayList variables
     */
    public ArrayList<String> XAxis;
    public ArrayList<String> YAxis;


    /**
     * Grab the x and y arrayLists
     * @return the arrayList full of strings
     */
    public ArrayList<String> getXAxis() {
        return XAxis;
    }

    public ArrayList<String> getYAxis() {
        return YAxis;
    }

    /**
     * Sets the x axis arrayList
     * @param XAxis and arrayList full of Strings to be used as the xAxis
     */
    public void setXAxis(ArrayList<String> XAxis) {
        this.XAxis = XAxis;
    }

    /**
     * Sets they axis arrayList
     * @param YAxis and arrayList full of Strings to be used as the yAxis
     */
    public void setYAxis(ArrayList<String> YAxis) {
        this.YAxis = YAxis;
    }

    /**
     * Adds a string to the X Axis arrayList
     * @param data the data being added to the arrayList
     */
    public void addXAxis(String data) {
        XAxis.add(data);
    }

    /**
     * Adds a string to the Y axis arrayList
     * @param data the data being added to the arrayList
     */
    public void addYAxis(String data) {
        YAxis.add(data);
    }
}
