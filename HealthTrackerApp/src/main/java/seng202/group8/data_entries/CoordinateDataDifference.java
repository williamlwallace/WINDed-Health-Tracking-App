package seng202.group8.data_entries;

import static java.lang.Math.PI;

/**
 *
 * This is the CoordinateDataDifference class.
 * It is used to calculate and store the various differences between two coordinates.
 * These individual coordinate data differences are then used in arraylist summation methods
 * in the data class in order to calculate the total distance travelled, and total calories
 * burned. They are also used as the data points for plotting statistics in the graph
 * visualisations section.
 *
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


    /**
     *Calculates the angle between the two coordinate points, using trigonometry.
     */
    public void setAngle() {
        this.angle = Math.asin(this.altitudeDifference / this.distanceDifference);
    }


    public double getHaversineDistance() {
        return haversineDistance;
    }


    /**Calculates the semi 3 dimensional distance between the two coordinates, using
     * the 'haversine' formula. This formula takes the curvature into the earth into account
     * when calculating the distance between coordinate points.
     *
     * @param newPointOne The first set of coordinates
     * @param newPointTwo The second set of coordinates to which the first is compared. The resulting difference between the two is the haversine distance.
     */
    public void setHaversineDistance(CoordinateData newPointOne, CoordinateData newPointTwo) {
        double earthsRadius = 6371e3;   // Mean radius of Earth
        double latitudeOne = newPointOne.getLatitude() * PI / 180;  //Convert Latitude to Radians
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


    /** Calculates the elevation/altitude difference between the two points.**/
    public void setAltitudeDifference(CoordinateData newPointOne, CoordinateData newPointTwo) {
        this.altitudeDifference = newPointOne.getAltitude() - newPointTwo.getAltitude();
    }


    public double getDistanceDifference() {
        return distanceDifference;
    }


    /** This calculation takes the change in elevation between the two points
     * into account, as well as using the haversine distance, to more accurately
     * measure the distance the user has travelled. Example: the actual
     * distance they walked up an incline, rather than just the level ground
     * equivalent for a curved earth. This formula will take into account both elevation
     * changes and the earths curvature due to its combination of altitude and haversine
     * calculations.
     */
    public void setDistanceDifference() {

        this.distanceDifference = Math.hypot(this.haversineDistance, this.altitudeDifference);
    }


    /**
     * This is the constructor for the CoordinateDataDifference class. It takes two CoordinateData objects as
     * parameters, and calculates the various differences between them which are then stored in the
     * CoordinateDataDifference object. The distance difference between two coordinateData sets is calculated from
     * a combination of the haversine distance between the points, and the altitude difference between the points.
     * Those two distances are used for the short sides of a right angle triangle, with the longest side being the
     * final distance, which is found with pythagoras.
     *
     * @param pointOne  A coordinate data object holding latitude, longitude, and altitude.
     * @param pointTwo  The second coordinate data object to compare with the first one.
     */
    public CoordinateDataDifference(CoordinateData pointOne, CoordinateData pointTwo) {
        setHaversineDistance(pointOne, pointTwo);
        setAltitudeDifference(pointOne, pointTwo);
        setDistanceDifference();
        setGradient();
        setAngle();
    }
}
