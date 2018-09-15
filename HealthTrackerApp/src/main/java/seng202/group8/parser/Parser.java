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
    /**
     * Receives a filename and starts reading the data activity by activity
     * @param filename
     * @throws Exception
     */
    public Parser(String filename, User newUser) throws Exception {
        this.user = newUser;
        acceptedValues.add(walk);
        acceptedValues.add(hike);
        acceptedValues.add(run);
        acceptedValues.add(climb);
        acceptedValues.add(bike);
        acceptedValues.add(swim);
        acceptedValues.add(waterSports);
        //ArrayList<Data> data = new ArrayList<Data>();
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
                    //System.out.println(activityName + "\n");
                    //System.out.println(activityType + "\n");
                    if (!isCorrupt) {
                        //System.out.println(activityCoordinates.get(5));
                        Data activityToSend;
                        DataType activityEnum;
                        String newActivityName = activityName;
                        ArrayList<LocalDateTime> newActivityDateTime = activityDateTime;
                        ArrayList<CoordinateData> newActivityCoordinates = activityCoordinates;
                        ArrayList<Integer> newActivityHeartRate = activityHeartRate;
                        //System.out.println(newActivityDateTime.get(0));
                        switch (activityType) {
                            case "walk":
                                activityEnum = DataType.WALK;
                                //System.out.println(newActivityCoordinates.size());
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
                            default: //case "water sport": fixer for now
                                activityEnum = DataType.WATER_SPORTS;
                                activityToSend = new WaterSportsData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                        }
                        //System.out.println("title: " + activityToSend.getTitle());
                        try {
                            length = line.length;
                        } catch (NullPointerException e) {
                            finished = 1;
                        }
                        //System.out.println(activityToSend);
                        this.dataList.add(activityToSend);
                        //System.out.println(dataList.get(0).getCoordinatesArrayList().size());
                    }
                    //System.out.println(dataList.get(0).getCoordinatesArrayList().size());
                    isCorrupt = Boolean.FALSE;
                    //numErrors = 0;
                    //numLines = 0;
                    activityName = "";
                    activityType = "";
                    activityDateTime.clear();
                    activityHeartRate.clear();
                    //System.out.println(dataList.get(0).getCoordinatesArrayList().size());
                    activityCoordinates.clear();
                    //line = readLine(csvReader);
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                throw new FileNotFoundError("The file '" + filename + "' doesn't exist.");
            }
        } else {
            throw new NotCSVError("The file '" + filename + "' must be a .csv file");
        }
        //System.out.println(dataList.get(0).getCoordinatesArrayList().size());
        //this.dataList = data;
    }
    /**
     * Receives a activity and collates the data from it
     * @param line
     * @param csvReader
     * @throws Exception
     */
    private String[] parseActivity(String[] line, CSVReader csvReader) throws Exception {
        int numErrors = 0;
        int numLines = 0;
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
                throw new noTypeError();
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
                    //System.out.println("No more activities");
                    finished = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    //throw new WordContainsException("Corrupt line '" + numLines + "' in activity '" + activityName + "' is missing data.");
                    numErrors += 1;
                    line = nextActivity(csvReader, line);
                } catch (NumberFormatException e) {
                    //System.out.println("Corrupt line '" + numLines + "' in activity '" + activityName + "' has incorrect data");
                    numErrors += 1;
                    line = nextActivity(csvReader, line);
                } catch (DateTimeParseException e) {
                    numErrors += 1;
                    line = nextActivity(csvReader, line);
                }
            }
        } catch (NullPointerException e) {

        }
        if (numLines * 0.1 < numErrors) {
            isCorrupt = Boolean.TRUE;
            throw new DataMissingError("Activity '" + activityName + "' is corrupt.");
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
            while ((line != null) && !(line[0].equals("#start"))) {
                line = readLine(csvReader);
            }
        } catch (NullPointerException e) {
            System.out.println("No more activities");
        }
        return line;
    }

    public ArrayList<Data> getDataList() {
        //System.out.println(dataList.get(2).getCoordinatesArrayList().size());
        return dataList;
    }

    public static void main(String[] args) throws Exception {
        User userTest = new User("Sam", 20, 72.0, 167.0, Sex.MALE);
        Parser parserTest =  new Parser("seng202_2018_example_data_clean.csv", userTest);
        ArrayList<Data> data = new ArrayList<>(parserTest.getDataList());
        for (Data d : data) {
            System.out.println(d.getCoordinatesArrayList().size());
            //System.out.println(d.getTitle());
        }
    }

}
