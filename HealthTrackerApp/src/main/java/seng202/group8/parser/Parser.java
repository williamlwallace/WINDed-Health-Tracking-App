package seng202.group8.parser;

import com.opencsv.CSVReader;
import seng202.group8.data_entries.*;
import seng202.group8.user.*;
import org.apache.commons.lang3.ObjectUtils;
import seng202.group8.user.user_stats.Sex;

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
    private User user;

    private ArrayList<Data> dataList = new ArrayList<Data>();

    private Boolean isCorrupt = Boolean.FALSE;
    private String filename;
    private int lineNum;
    /**
     * Receives a filename and creates the list of type trip phrases.
     * @param newFilename
     * @throws Exception
     */
    public Parser(String newFilename, User newUser) throws Exception {
        this.user = newUser;
        acceptedValues.add(walk);
        acceptedValues.add(hike);
        acceptedValues.add(run);
        acceptedValues.add(climb);
        acceptedValues.add(bike);
        acceptedValues.add(swim);
        acceptedValues.add(waterSports);
        filename = newFilename;
    }

    /**
     * Starts parsing the entire file sent in.
     * @throws Exception
     */
    public void parseFile() throws Exception {
        lineNum = 0;
        if (filename.substring(filename.length() - 3, filename.length()).equals("csv")) {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(filename));
                String[] line = readLine(csvReader);
                int finished = 0;
                //String[] myEntries = csvReader.readAll();
                int length = 0;
                try {
                    length = line.length;
                } catch (NullPointerException e) {
                    finished = 1;
                }
                while (line != null && (finished == 0)) {
                    line = parseActivity(line, csvReader);
                    if (!isCorrupt) {
                        Data activityToSend;
                        DataType activityEnum;
                        String newActivityName = activityName;
                        ArrayList<LocalDateTime> newActivityDateTime = activityDateTime;
                        ArrayList<CoordinateData> newActivityCoordinates = activityCoordinates;
                        ArrayList<Integer> newActivityHeartRate = activityHeartRate;
                        switch (activityType) {
                            case "walk":
                                activityEnum = DataType.WALK;
                                activityToSend = new WalkData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            case "hike":
                                activityEnum = DataType.HIKE;
                                activityToSend = new HikeData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            case "run":
                                activityEnum = DataType.RUN;
                                activityToSend = new RunData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            case "climb":
                                activityEnum = DataType.CLIMB;
                                activityToSend = new ClimbData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            case "bike":
                                activityEnum = DataType.BIKE;
                                activityToSend = new BikeData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            case "swim":
                                activityEnum = DataType.SWIM;
                                activityToSend = new SwimData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            default: //case "Walk": defult for now
                                activityEnum = DataType.WALK;
                                activityToSend = new WaterSportsData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                        }
                        try {
                            length = line.length;
                        } catch (NullPointerException e) {
                            finished = 1;
                        }
                        this.dataList.add(activityToSend);
                    }
                    isCorrupt = Boolean.FALSE;
                    activityName = "";
                    activityType = "";
                    activityDateTime.clear();
                    activityHeartRate.clear();
                    activityCoordinates.clear();
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                dataList.clear();
                throw new FileNotFoundError("The file '" + filename + "' doesn't exist.");
            }
        } else {
            dataList.clear();
            throw new NotCSVError("The file '" + filename + "' must be a .csv file");
        }
    }
    /**
     * Receives a activity and collates the data from it. Throws custom errors so the GUI can handle the information
     * @param line
     * @param csvReader
     * @throws Exception
     */
    private String[] parseActivity(String[] line, CSVReader csvReader) throws Exception {
        int numErrors = 0;
        int numLines = 0;
        String errorLines = null;
        try {
            numLines =+ 1;
            activityName = line[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            numErrors =+ 1;
            activityName = "";
        }
        activityType = "";
        if (!isCorrupt) {
            activityName = activityName.toLowerCase();
            for (int place = 0; place < acceptedValues.size(); place++) {
                for (int i = 0; i < acceptedValues.get(place).size(); i++) {
                    if (activityName.contains(acceptedValues.get(place).get(i))) {
                        activityType = acceptedValues.get(place).get(0);
                    }
                }
            }
            if (activityType.equals("")) {
                dataList.clear();
                throw new noTypeError("The activity '" + activityName+ "' doesnt match any of the activity types.");
//                Scanner scanner = new Scanner(System.in);
//                System.out.print("This activity, '" + line[1] + "', doesn't match any of our catagorys, please select the appropriate one:\n1: Walk\n2: Hike\n3: Run\n4: Climb\n5: Bike\n6: Swim\n7: Water Sports\n");
//                String selection = scanner.next();
//                while (!selection.equals("1") && !selection.equals("2") && !selection.equals("3") && !selection.equals("4") && !selection.equals("5") && !selection.equals("6") && !selection.equals("7")) {
//                    System.out.print("You must enter a number between 1 and 7\n");
//                    selection = scanner.next();
//                }
//                int activityNum = Integer.parseInt(selection) - 1;
//                System.out.print("Please enter a phrase from '" + line[1] + "' so we can recognise this activities category next time\n");
//                String phrase = scanner.next();
//                while (!activityName.contains(phrase.toLowerCase())) {
//                    System.out.print("You must enter a phrase from '" + line[1] + "'\n");
//                    phrase = scanner.next();
//                }
//                acceptedValues.get(activityNum).add(phrase.toLowerCase());
//                activityType = acceptedValues.get(activityNum).get(0);
            }
        }
        line = readLine(csvReader);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm:ss");
        LocalDateTime lineDate;
        int lineHeart;
        CoordinateData lineCoordinate;
        int finished = 0;
        try {
            while ((finished == 0) && (line.length > 0) && (!isCorrupt) && (!line[0].equals("#start"))) {
                try {
                    numLines = +1;
                    String toFormat = line[0] + ";" + line[1];
                    lineDate = LocalDateTime.parse(toFormat, timeFormat);
                    lineHeart = Integer.parseInt(line[2]);
                    lineCoordinate = new CoordinateData(Double.valueOf(line[3]), Double.valueOf(line[4]), Double.valueOf(line[5]));
                    activityDateTime.add(lineDate);
                    activityHeartRate.add(lineHeart);
                    activityCoordinates.add(lineCoordinate);
                    line = readLine(csvReader);
                } catch (NullPointerException e) {
                    finished = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (errorLines == null) {
                        errorLines = "(Incorrect number of lines) " + String.valueOf(lineNum);
                    } else {
                        errorLines = errorLines + ", (Incorrect number of lines) " + String.valueOf(lineNum);
                    }
                    numErrors += 1;
                    line = nextActivity(csvReader, line);
                } catch (NumberFormatException e) {
                    if (errorLines == null) {
                        errorLines = "(Heart-rate or Coordinates) " + String.valueOf(lineNum);
                    } else {
                        errorLines = errorLines + ", (Heart-rate or Coordinates) " + String.valueOf(lineNum);
                    }
                    numErrors += 1;
                    line = nextActivity(csvReader, line);
                } catch (DateTimeParseException e) {
                    if (errorLines == null) {
                        errorLines = "(date) " + String.valueOf(lineNum);
                    } else {
                        errorLines = errorLines + ", (date) " + String.valueOf(lineNum);
                    }
                    numErrors += 1;
                    line = nextActivity(csvReader, line);
                }
            }
        } catch (NullPointerException e) {

        }
        if (numLines * 0.1 < numErrors) {
            isCorrupt = Boolean.TRUE;
            dataList.clear();
            throw new DataMissingError("Activity '" + activityName + "' is corrupt on line/lines: " + errorLines);
        }
        return line;
    }

    /**
     * Parses the next line and returns it.
     * @param csvReader
     * @throws Exception
     */
    private String[] readLine(CSVReader csvReader) throws Exception {
        lineNum++;
        String[] line;
        line = csvReader.readNext();
        return line;
    }

    /**
     * Scrolls through the lines until the next activity is reached.
     * @param csvReader
     * @param line
     * @return line
     * @throws Exception
     */
    private String[] nextActivity(CSVReader csvReader, String[] line) throws Exception {
        try {
            while ((line != null) && !(line[0].equals("#start"))) {
                line = readLine(csvReader);
            }
        } catch (NullPointerException e) {
            System.out.println("No more activities");
        }
        return line;
    }

    /**
     * Returns a list of the data it parsed
     * @return dataList
     */
    public ArrayList<Data> getDataList() {
        return dataList;
    }

    /**
     * Parses a given file, here for testing purposes.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        User userTest = new User("Sam", 20, 72.0, 167.0, Sex.MALE);
        Parser parserTest =  new Parser("seng202_2018_example_data_clean.csv", userTest);
        parserTest.parseFile();
        ArrayList<Data> data = new ArrayList<>(parserTest.getDataList());
        for (Data d : data) {
            System.out.println(d.getTitle());
            System.out.println(d.getCoordinatesArrayList().size());
        }
    }

    /**
     * adds a key phrase into the collection of trip phrases for finding the type
     * @param keyWord
     * @param type
     */
    public void add(String keyWord, int type) {
        acceptedValues.clear();
        switch (type) {
            case 1:
                walk.add(keyWord);
                break;
            case 2:
                hike.add(keyWord);
                break;
            case 3:
                run.add(keyWord);
                break;
            case 4:
                climb.add(keyWord);
                break;
            case 5:
                bike.add(keyWord);
                break;
            case 6:
                swim.add(keyWord);
                break;
            case 7:
                waterSports.add(keyWord);
                break;
            default:
                break;
        }
        acceptedValues.add(walk);
        acceptedValues.add(hike);
        acceptedValues.add(run);
        acceptedValues.add(climb);
        acceptedValues.add(bike);
        acceptedValues.add(swim);
        acceptedValues.add(waterSports);
    }

    public User getUser() {
        return user;
    }

    public String getFilename() {
        return filename;
    }
}
