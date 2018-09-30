package seng202.group8.services.statistics_service;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GraphXY {

    /**
     * X and Y axis arrayList variables
     */
    public ArrayList<Double> XAxis;
    public ArrayList<Double> YAxis;
    public String XAxisScale;


    /**
     * Grab the x and y arrayLists
     * @return the arrayList full of strings
     */
    public ArrayList<Double> getXAxis() {
        return this.XAxis;
    }

    public ArrayList<Double> getYAxis() {
        return this.YAxis;
    }

    /**
     * Grab the units of the time scale of the x-axis.
     * @return the units of the time scale of the x-axis (hours, minutes, or seconds) as a string.
     */
    public String getXAxisScale() {
        return XAxisScale;
    }

    /**
     * Sets the x axis arrayList
     * @param XAxis and arrayList full of Strings to be used as the xAxis
     */
    public void setXAxis(ArrayList<Double> XAxis) {
        this.XAxis = XAxis;
    }

    /**
     * Sets they axis arrayList
     * @param YAxis and arrayList full of Strings to be used as the yAxis
     */
    public void setYAxis(ArrayList<Double> YAxis) {
        this.YAxis = YAxis;
    }

    /**
     * Sets the scale of the XAxis values.
     * @param XAxisScale a string containing the units desired for the values on the x-axis (which are always time
     *                   values).
     */
    public void setXAxisScale(String XAxisScale) {
        this.XAxisScale = XAxisScale;
    }

    /**
     * Adds a string to the X Axis arrayList
     * @param value the data being added to the arrayList
     */
    public void addXAxis(Double value) {
        this.XAxis.add(value);
    }

    /**
     * Adds a string to the Y axis arrayList
     * @param value the data being added to the arrayList
     */
    public void addYAxis(Double value) {
        this.YAxis.add(value);
    }


    public GraphXY() {
        this.YAxis = new ArrayList<>();
        this.XAxis = new ArrayList<>();
        // Default XAxisScale value.
        this.XAxisScale = "(seconds)";
    }
}
