import java.util.ArrayList;

package seng202.group8.dataEntries;

public abstract class Data
{

    private String ActivityType = "Test";

    private ArrayList<Tuple<Double, Double, Double>> coordinatesList = new ArrayList<Tuple<Double, Double, Double>>;

    private ArrayList<Integer> heartRateList = new ArrayList<Integer>;

    private ArrayList<CSVDataLine> inputData;

    private abstract double consumedCalories();

    public static void main() {

        inputData = getCSVActivities();

        heartRateList = getHeartRateList();

        coordinatesList = getCoordinatesList();
    }

}