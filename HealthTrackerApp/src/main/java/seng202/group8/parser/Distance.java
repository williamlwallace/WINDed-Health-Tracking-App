package seng202.group8.parser;

public class Distance {
    // hello
    private double latitude;
    private double longitude;
    private double altitude;

    public Distance(double temp_lat, double temp_long, double temp_alt) {
        latitude = temp_lat;
        longitude = temp_long;
        altitude = temp_alt;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
