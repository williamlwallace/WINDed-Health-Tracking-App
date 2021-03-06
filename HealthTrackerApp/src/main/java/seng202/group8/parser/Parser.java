package seng202.group8.parser;

import com.opencsv.CSVReader;
import java_sqlite_db.SQLiteJDBC;
import seng202.group8.activity_collection.ActivityList;
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

/**
 * @author sgv15
 *
 */
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
    private ArrayList<ActivityList> oldData;
    private SQLiteJDBC database = new SQLiteJDBC();


    /**
     * Receives a filename and creates the list of type trip phrases. Gets these phrases from the database and includes a few default ones in the parser class.
     * @param newFilename the file name of the CSV file
     * @param newUser the user to add the data to.
     * @throws Exception
     */
    public Parser(String newFilename, User newUser) throws Exception {
        this.user = newUser;
        this.acceptedValues.add(this.walk);
        this.acceptedValues.add(this.hike);
        this.acceptedValues.add(this.run);
        this.acceptedValues.add(this.climb);
        this.acceptedValues.add(this.bike);
        this.acceptedValues.add(this.swim);
        this.acceptedValues.add(this.waterSports);
        //System.out.println("Parser: "+user.getUserID());
        this.database.getKeyWords(user.getUserID(), this);
        this.filename = newFilename;
        try {
            this.oldData = this.user.getUserActivities().getActivityListCollection();
            //System.out.println("DUPE: "+oldData);
            //System.out.println("DUPE: "+oldData.get(0).getTitle());
        } catch(NullPointerException e) {
            this.oldData = null;
        } catch(IndexOutOfBoundsException e) {
            this.oldData = null;
        }
    }


    /**
     * Starts parsing the entire file sent in. Does this by splitting the file into activities and adding them to a data list one by one.
     * @throws Exception
     */
    public void parseFile() throws Exception {
        lineNum = 0;
        if (this.filename.substring(this.filename.length() - 3, this.filename.length()).equals("csv")) {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(this.filename));
                String[] line = readLine(csvReader);
                int finished = 0;
                int length = 0;
                try {
                    length = line.length;
                } catch (NullPointerException e) {
                    finished = 1;
                }
                Integer dataId = this.database.getNextDataID();
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
                            case "water sport":
                                activityEnum = DataType.WATER_SPORTS;
                                activityToSend = new WaterSportsData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                            default://case "walk": default
                                activityEnum = DataType.WALK;
                                activityToSend = new WalkData(newActivityName, activityEnum, newActivityDateTime, newActivityCoordinates, newActivityHeartRate, user);
                                break;
                        }
                        try {
                            length = line.length;
                        } catch (NullPointerException e) {
                            finished = 1;
                        }
                        boolean duplicate = false;
                        for (int d = 0; d < dataList.size(); d++) {
                            if (dataList.get(d).equalsNewData(activityToSend)) {
                                duplicate = true;
                            }
                        }
                        if (!duplicate && !this.oldData.isEmpty()) {
                            for (int i = 0; i < this.oldData.size(); i++) {
                                ArrayList<Data> activityList = this.oldData.get(i).getActivityList();
                                for (int j = 0; j < activityList.size(); j++) {
                                    Data data = activityList.get(j);
                                    if (activityToSend.equalsNewData(data)) {
                                        duplicate = true;
                                    }
                                }
                            }
                        }
                        if (!duplicate) {
                            activityToSend.setDataId(dataId);
                            dataId += 1;
                            this.dataList.add(activityToSend);
                        }
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
     * @param line The information help on the current line as a list of strings.
     * @param csvReader The reader which keeps track of which line the class is currently on.
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
            for (int place = 0; place < this.acceptedValues.size(); place++) {
                for (int i = 0; i < this.acceptedValues.get(place).size(); i++) {
                    if (activityName.contains(this.acceptedValues.get(place).get(i))) {
                        activityType = this.acceptedValues.get(place).get(0);
                    }
                }
            }
            if (activityType.equals("")) {
                dataList.clear();
                throw new noTypeError("The activity '" + activityName+ "' doesn't match any of the activity types.");
            }
        }
        line = readLine(csvReader);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy;HH:mm:ss");
        LocalDateTime lineDate;
        int lineHeart;
        CoordinateData lineCoordinate;
        int finished = 0;
        try {
            while ((finished == 0) && (line.length > 0) && (!line[0].equals("#start"))) {
                try {
                    numLines++;
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
                    line = readLine(csvReader);
                } catch (NumberFormatException e) {
                    if (errorLines == null) {
                        errorLines = "(Heart-rate or Coordinates) " + String.valueOf(lineNum);
                    } else {
                        errorLines = errorLines + ", (Heart-rate or Coordinates) " + String.valueOf(lineNum);
                    }
                    numErrors += 1;
                    line = readLine(csvReader);
                } catch (DateTimeParseException e) {
                    if (errorLines == null) {
                        errorLines = "(date) " + String.valueOf(lineNum);
                    } else {
                        errorLines = errorLines + ", (date) " + String.valueOf(lineNum);
                    }
                    numErrors += 1;
                    line = readLine(csvReader);
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
     * Receives the next line from the csv file.
     * @param csvReader the csvReader which keeps track of the location in the file.
     * @throws Exception
     */
    private String[] readLine(CSVReader csvReader) throws Exception {
        lineNum++;
        String[] line;
        line = csvReader.readNext();
        return line;
    }


    /**
     * Returns a list of the data it parsed
     * @return dataList A list of all of the activities from the csv file
     */
    public ArrayList<Data> getDataList() {
        return dataList;
    }


    /**
     * Adds a key phrase into the collection of trip phrases for finding the type of an activity. Also has the option to add this phrase to the database.
     * @param keyWord The phrase to add to the list
     * @param type The type this phrase corresponds to
     * @param addToDataBase A boolean to say whether the phrase should be added to the database or not.
     * @return to_return returns a boolean to let the application know if the phrase was added to the database or not.
     */
    public boolean add(String keyWord, int type, boolean addToDataBase) {
        this.acceptedValues.clear();
        boolean to_return = true;
        if (addToDataBase) {
            to_return = this.database.addParserKeyword(user.getUserID(), keyWord, type);
        }
        if (to_return) {
            switch (type) {
                case 1:
                    this.walk.add(keyWord);
                    break;
                case 2:
                    this.hike.add(keyWord);
                    break;
                case 3:
                    this.run.add(keyWord);
                    break;
                case 4:
                    this.climb.add(keyWord);
                    break;
                case 5:
                    this.bike.add(keyWord);
                    break;
                case 6:
                    this.swim.add(keyWord);
                    break;
                case 7:
                    this.waterSports.add(keyWord);
                    break;
                default:
                    break;
            }
        }
        this.acceptedValues.add(this.walk);
        this.acceptedValues.add(this.hike);
        this.acceptedValues.add(this.run);
        this.acceptedValues.add(this.climb);
        this.acceptedValues.add(this.bike);
        this.acceptedValues.add(this.swim);
        this.acceptedValues.add(this.waterSports);
        return to_return;
    }


    /**
     * Gets all of the key words added by the user.
     * @return
     */
    public ArrayList<String> getRemoveableWords() {
        ArrayList<String> baseWalk = new ArrayList<String>(Arrays.asList("walk"));
        ArrayList<String> baseHike = new ArrayList<String>(Arrays.asList("hike", "hiking"));
        ArrayList<String> baseRun = new ArrayList<String>(Arrays.asList("run"));
        ArrayList<String> baseClimb = new ArrayList<String>(Arrays.asList("climb"));
        ArrayList<String> baseBike = new ArrayList<String>(Arrays.asList("bike", "biking"));
        ArrayList<String> baseSwim = new ArrayList<String>(Arrays.asList("swim", "scuba", "diving"));
        ArrayList<String> baseWaterSports = new ArrayList<String>(Arrays.asList("water sport","kayak", "canoe", "raft", "surf"));
        ArrayList<String> toReturn  = new ArrayList<String>();
        for (int place = 0; place < acceptedValues.size(); place++) {
            for (int i = 0; i < acceptedValues.get(place).size(); i++) {
                //System.out.println("before:" + acceptedValues.get(place).get(i));
                if (!baseWalk.contains(acceptedValues.get(place).get(i)) && !baseHike.contains(acceptedValues.get(place).get(i)) && !baseRun.contains(acceptedValues.get(place).get(i))
                        && !baseClimb.contains(acceptedValues.get(place).get(i)) && !baseBike.contains(acceptedValues.get(place).get(i))
                        && !baseSwim.contains(acceptedValues.get(place).get(i)) && !baseWaterSports.contains(acceptedValues.get(place).get(i))) {
                    //System.out.println("After:" + acceptedValues.get(place).get(i));
                    toReturn.add(acceptedValues.get(place).get(i));
                }
            }
        }
        return toReturn;
    }


    public ArrayList<ArrayList<String>> getAcceptedValues() {
        return this.acceptedValues;
    }


    public User getUser() {
        return user;
    }


    public String getFilename() {
        return filename;
    }

}
