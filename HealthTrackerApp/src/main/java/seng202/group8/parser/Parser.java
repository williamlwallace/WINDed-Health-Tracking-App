package seng202.group8.parser;

import com.opencsv.CSVReader;
import seng202.group8.data_entries.*;
import seng202.group8.user.*;
import org.apache.commons.lang3.ObjectUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalTime;


public class Parser {

    private ArrayList<ArrayList<String>> acceptedValues = new ArrayList<ArrayList<String>>();
    private ArrayList<String> walk = new ArrayList<String>(Arrays.asList("walk"));
    private ArrayList<String> hike = new ArrayList<String>(Arrays.asList("hike", "hiking"));
    private ArrayList<String> run = new ArrayList<String>(Arrays.asList("run"));
    private ArrayList<String> climb = new ArrayList<String>(Arrays.asList("climb"));
    private ArrayList<String> bike = new ArrayList<String>(Arrays.asList("bike", "biking"));
    private ArrayList<String> swim = new ArrayList<String>(Arrays.asList("swim", "scuba", "diving"));
    private ArrayList<String> waterSports = new ArrayList<String>(Arrays.asList("water sport","kayak", "canoe", "raft", "surf"));

    private String activityName = "";
    private String activityType = "";
    private ArrayList<LocalDateTime> activityDateTime = new ArrayList<LocalDateTime>();
    private ArrayList<Integer> activityHeartRate = new ArrayList<Integer>();
    private ArrayList<CoordinateData> activityCoordinates = new ArrayList<CoordinateData>();

    private Boolean isCorrupt = Boolean.FALSE;

    /**
     * Receives a filename and starts reading the data activity by activity
     * @param filename
     * @throws Exception
     */
    public Parser(String filename) throws Exception {
        acceptedValues.add(walk);
        acceptedValues.add(hike);
        acceptedValues.add(run);
        acceptedValues.add(climb);
        acceptedValues.add(bike);
        acceptedValues.add(swim);
        acceptedValues.add(waterSports);
        if (filename.substring(filename.length() - 3, filename.length()).equals("csv")) {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(filename));
                String[] line = readLine(csvReader);
                //String[] myEntries = csvReader.readAll();
                while (line != null) {
                    line = parseActivity(line, csvReader);
                    System.out.println(activityName + "\n");
                    System.out.println(activityType + "\n");
                    if (!isCorrupt) {
                        Data activityToSend;
                        DataType activityEnum;
                        switch (activityType) {
                            case "walk":
                                activityEnum = DataType.WALK;
                                activityToSend = new WalkData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                            case "hike":
                                activityEnum = DataType.HIKE;
                                activityToSend = new HikeData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                            case "run":
                                activityEnum = DataType.RUN;
                                activityToSend = new RunData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                            case "climb":
                                activityEnum = DataType.CLIMB;
                                activityToSend = new ClimbData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                            case "bike":
                                activityEnum = DataType.BIKE;
                                activityToSend = new BikeData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                            case "swim":
                                activityEnum = DataType.SWIM;
                                activityToSend = new SwimData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                            case "water sport":
                                activityEnum = DataType.WATER_SPORTS;
                                activityToSend = new WaterSportsData(activityName, activityEnum, activityDateTime, activityCoordinates,activityHeartRate);
                                break;
                        }

                    }
                    isCorrupt = Boolean.FALSE;
                    activityName = "";
                    activityType = "";
                    activityDateTime.clear();
                    activityHeartRate.clear();
                    activityCoordinates.clear();
                    //line = readLine(csvReader);
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("The file '" + filename + "' doesn't exist.");
            }
        } else {
            System.out.println("The file '" + filename + "' must be a .csv file");
        }
    }
    /**
     * Receives a activity and collates the data from it
     * @param line
     * @param csvReader
     * @throws Exception
     */
    private String[] parseActivity(String[] line, CSVReader csvReader) throws Exception {
        //System.out.print(line[0]);
        try {
            activityName = line[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The file is incorrect or formatted incorrectly");
            isCorrupt = Boolean.TRUE;
        }
        activityType = "";
        // Check if it is a walk
        if (activityName.equals("") && (!isCorrupt)) {
            System.out.println("Will ask for a title and activity type");
        } else if (!isCorrupt) {
            activityName = activityName.toLowerCase();
            for (int place = 0; place < acceptedValues.size(); place++) {
                for (int i = 0; i < acceptedValues.get(place).size(); i++) {
                    if (activityName.contains(acceptedValues.get(place).get(i))) {
                        activityType = acceptedValues.get(place).get(0);
                    }
                }
            }
            if (activityType.equals("")) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("This activity, '" + line[1] + "', doesn't match any of our catagorys, please select the appropriate one:\n1: Walk\n2: Hike\n3: Run\n4: Climb\n5: Bike\n6: Swim\n7: Water Sports\n");
                String selection = scanner.next();
                while (!selection.equals("1") && !selection.equals("2") && !selection.equals("3") && !selection.equals("4") && !selection.equals("5") && !selection.equals("6") && !selection.equals("7")) {
                    System.out.print("You must enter a number between 1 and 7\n");
                    selection = scanner.next();
                }
                int activityNum = Integer.parseInt(selection) - 1;
                System.out.print("Please enter a phrase from '" + line[1] + "' so we can recognise this activities category next time\n");
                String phrase = scanner.next();
                while (!activityName.contains(phrase.toLowerCase())) {
                    System.out.print("You must enter a phrase from '" + line[1] + "'\n");
                    phrase = scanner.next();
                }
                acceptedValues.get(activityNum).add(phrase.toLowerCase());
                activityType = acceptedValues.get(activityNum).get(0);
            }
        }
        //System.out.println(activityType);
        line = readLine(csvReader);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm:ss");
        LocalDateTime lineDate;
        int lineHeart;
        CoordinateData lineCoordinate;
        try {
            while (!(line[0].equals("#start")) && (line != null) && (!isCorrupt)) {
                //System.out.println(line[1]);
                String toFormat = line[0] + ";" + line[1];
                lineDate = LocalDateTime.parse(toFormat, timeFormat);
                lineHeart = Integer.parseInt(line[2]);
                lineCoordinate = new CoordinateData(Double.valueOf(line[3]), Double.valueOf(line[4]), Double.valueOf(line[5]));
                //System.out.println("hi: " + lineDistance.getAltitude() + "\n");
                activityDateTime.add(lineDate);
                activityHeartRate.add(lineHeart);
                activityCoordinates.add(lineCoordinate);
                line = readLine(csvReader);
            }
        } catch (NullPointerException e) {
            System.out.println("No more activities");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Corrupt activity '" + activityName + "' is missing data.");
            isCorrupt = Boolean.TRUE;
            line = nextActivity(csvReader, line);
        } catch (NumberFormatException e) {
            System.out.println("Corrupt activity '" + activityName + "' has incorrect data");
            isCorrupt = Boolean.TRUE;
            line = nextActivity(csvReader, line);
        } catch (DateTimeParseException e) {
            System.out.println("Corrupt activity '" + activityName + "' has an incorrectly stored date");
            isCorrupt = Boolean.TRUE;
            line = nextActivity(csvReader, line);
        }
        return line;
    }

    /**
     * Receives a single line and returns it.
     * @param csvReader
     * @throws Exception
     */
    private String[] readLine(CSVReader csvReader) throws Exception {
        //ArrayList<String> list = new ArrayList<String>();
        String[] line;
        line = csvReader.readNext();
        return line;
    }

    private String[] nextActivity(CSVReader csvReader, String[] line) throws Exception {
        //ArrayList<String> list = new ArrayList<String>();
        try {
            while (!(line[0].equals("#start")) && (line != null)) {
                line = readLine(csvReader);
            }
        } catch (NullPointerException e) {
            System.out.println("No more activities");
        }
        return line;
    }

    public static void main(String[] args) throws Exception {
        Parser parserTest =  new Parser("seng202_2018_example_data.csv");
    }

}
