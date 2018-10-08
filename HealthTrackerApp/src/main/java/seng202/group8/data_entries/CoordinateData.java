package seng202.group8.data_entries;

import java.util.ArrayList;

public class CoordinateData {

    private double latitude;
    private double longitude;
    private double altitude;


    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getAltitude() {
        return altitude;
    }


    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }


    /**
     * The constructor for coordinate data. Takes the longitude and latitude coordinates,
     * and the altitude in metres, and stores them in a coordinate data object.
     * @param tempLatitude the latitude of the coordinate data.
     * @param tempLongitude the longitude of the coordinate data.
     * @param tempAltitude the altitude of the coordinate data.
     */
    public CoordinateData(double tempLatitude, double tempLongitude, double tempAltitude) {
        this.latitude = tempLatitude;
        this.longitude = tempLongitude;
        this.altitude = tempAltitude;
    }

}
