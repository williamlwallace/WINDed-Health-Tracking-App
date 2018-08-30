package seng202.group8.parser;

public class Distance {
    // hello
    private int latitude;
    private int longitude;
    private int altitude;

    public Distance(int temp_lat, int temp_long, int temp_alt) {
        latitude = temp_lat;
        longitude = temp_long;
        altitude = temp_alt;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
