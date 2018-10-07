package seng202.group8.data_entries;

import static java.lang.Math.PI;

/**
 * @author cmc280
 *
 */
public class CoordinateDataDifference {

    private double latitudeDifference;
    private double longitudeDifference;
    private double haversineDistance;
    private double altitudeDifference;
    private double distanceDifference;
    private double gradient;

    /** The angle of the slope compared to flat ground. Will always be
     * an acute angle in degrees.
     */
    private double angle;



    public double getGradient() {
        return gradient;
    }

    public void setGradient() {
        this.gradient = this.altitudeDifference / this.haversineDistance;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle() {

        this.angle = Math.asin(this.altitudeDifference / this.distanceDifference);
    }

    public double getHaversineDistance() {
        return haversineDistance;
    }

    public void setHaversineDistance(CoordinateData newPointOne, CoordinateData newPointTwo) {
        /**Calculates the semi 3 dimensional distance between the two coordinates, using
         * the 'haversine' formula. See setDistanceDifference for the 3 dimensional
         * distance.
         */
        double earthsRadius = 6371e3;   /** Mean radius of Earth **/
        double latitudeOne = newPointOne.getLatitude() * PI / 180;  /**Convert Latitude to Radians**/
        double latitudeTwo = newPointTwo.getLatitude() * PI / 180;

        this.latitudeDifference = (newPointTwo.getLatitude() - newPointOne.getLatitude()) * PI / 180;
        this.longitudeDifference = (newPointTwo.getLongitude() - newPointOne.getLongitude()) * PI / 180;

        double haversineOne = Math.sin(this.latitudeDifference / 2) * Math.sin(this.latitudeDifference / 2) +
                                Math.cos(latitudeOne) * Math.cos(latitudeTwo) *
                                Math.sin(longitudeDifference / 2) * Math.sin(longitudeDifference / 2);

        double haversineTwo = 2 * Math.atan2(Math.sqrt(haversineOne), Math.sqrt(1 - haversineOne));

        this.haversineDistance = (earthsRadius * haversineTwo);

        //(2 * 6371e3) * (Math.asin((Math.sqrt((Math.pow((Math.sin((newPointTwo.getLatitude() - newPointOne.getLatitude()) / 2.0)), 2.0))))))
        //double first = earthRadius
    }

    public double getAltitudeDifference() {
        return altitudeDifference;
    }

    public void setAltitudeDifference(CoordinateData newPointOne, CoordinateData newPointTwo) {
        /** Calculates the elevation/altitude difference between the two points.**/
        this.altitudeDifference = newPointOne.getAltitude() - newPointTwo.getAltitude();
    }

    public double getDistanceDifference() {
        return distanceDifference;
    }

    public void setDistanceDifference() {
        /** This calculation takes the change in elevation between the two points
         * into account, as well as using the haversineDistance, to more accurately
         * measure the distance the user has travelled. Example: the actual
         * distance they walked up an incline, rather than just the level ground
         * equivalent.
         */

        this.distanceDifference = Math.hypot(this.haversineDistance, this.altitudeDifference);
    }

    public CoordinateDataDifference(CoordinateData pointOne, CoordinateData pointTwo) {

        setHaversineDistance(pointOne, pointTwo);
        setAltitudeDifference(pointOne, pointTwo);
        setDistanceDifference();
        setGradient();
        setAngle();
    }
}
