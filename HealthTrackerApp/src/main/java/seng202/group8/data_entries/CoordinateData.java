package seng202.group8.data_entries;

import java.util.ArrayList;

public class CoordinateData {

    /**
     * Added a CoordinateData class for the Data abstract class to manage
     * incoming coordinate info. It makes each set of three coordinates into
     * a coordinate object, and adds them to a list of such objects stored in
     * the data object. That list of coordinate objects will then be used for
     * distance calculations.
     */

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
