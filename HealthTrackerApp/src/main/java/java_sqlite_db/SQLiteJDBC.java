package java_sqlite_db;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.*;
import seng202.group8.parser.Parser;
import seng202.group8.services.goals_service.GoalsService;
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.BMI;
import seng202.group8.user.BMIType;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.BMITypeRecord;
import seng202.group8.user.user_stats.Sex;
import seng202.group8.user.user_stats.UserStats;
import seng202.group8.user.user_stats.WeightRecord;

//import javax.jnlp.IntegrationService;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


/**
 * Class to manage the database, Inserting, Retrieving, Updating and Deleting from the project SQLite database file
 * @author jco165
 */
public class SQLiteJDBC {

    private static Boolean isTest = false;

    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }
    public Boolean getIsTest() {
        return isTest;
    }




    /**
     * Converts a Date object to a LocalDateTime object
     * @param dateToConvert the Date object to convert to a LocalDateTime
     * @return LocalDateTime object that is equilvalent to dateToConvert
     */
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Converts a LocalDateTime object to a string suitable for storing in the database
     * @param localDateTime LocalDateTime object to convert to a String
     * @return The string representation of the LocalDateTime formatted for the database
     */
    public String getStringFromLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateString = localDateTime.format(dateFormat);
        return  dateString;
    }


    /**
     * Converts a formatted String (retrieved from database) to a Date object
     * @param dateString the formatted string retrieved form the database
     * @return the Date object equivalent to the formatted String
     */
    public Date getDateFromString(String dateString) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = dateFormat.parse(dateString);
            //System.out.println(date.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Converts a formatted String (retrieved from database) to a LocalDateTimeObject
     * @param dateString the formatted String retrieved from the database
     * @return the LocalDatetime object equivalent to the formatted String
     */
    public LocalDateTime getLocalDateTimeFromString(String dateString) {
        LocalDateTime localDateTime = null;
        localDateTime = LocalDateTime.parse(dateString);
        return localDateTime;
    }

    /**
     * Connect to the winded.db database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite::resource:" + SQLiteJDBC.class.getResource("/resources/views/database/winded.db");
            if (isTest) {
                System.out.println("Test Database connection");
                url = "jdbc:sqlite::resource:" + SQLiteJDBC.class.getResource("/resources/views/test_resources/test.db");
            }

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            //System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * Insert a User into the database, if it already exists in the database doesn't execute the Insert
     * @param connection the Connection to the Database
     * @param id the unique user Id
     * @param name the name of the user
     * @param weight the weight of the User in kg
     * @param height the height of the User in cm
     * @param age the age of the user
     * @param sex the sex of the user as a String
     */
    public void insertUser(Connection connection, Integer id, String name, Double weight, Double height, Integer age, String sex) {

        String sql = "INSERT INTO user VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, weight);
            preparedStatement.setDouble(4, height);
            preparedStatement.setInt(5, age);
            preparedStatement.setString(6, sex);

            String find = "SELECT * from user where user_id=?";
            PreparedStatement findStatement = connection.prepareStatement(find);
            findStatement.setInt(1, id);
            ResultSet results = findStatement.executeQuery();
            if (results.next()) {
                //System.out.println("User Tuple already exists");
            } else {
                // Print out the result of the insert statement, 0 means nothing has been inserted
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ResultSet selectAllUsers(Connection connection) {
        assert null != connection;
        //System.out.println("Get all tuples");
        ResultSet resultSet = null;
        try {
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM user");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    /**
     * Perform a SQL select statement to get information form the user table
     * @param connection the connection to the database
     * @param userID the userId of the desired User
     * @return the ResultSet of the query (size of 1 row)
     */
    public ResultSet getUser(Connection connection, Integer userID) {
        assert null != connection && null != userID;
        ResultSet resultSet = null;
        //System.out.println("Get User with id: " + userID );
        String find = "SELECT * FROM user WHERE user_id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setInt(1, userID);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public void deleteUser(Integer userID) {
        String sql = "DELETE FROM User WHERE user_id=?";
        try {
            //System.out.println("Deleting User with id = " + userID);
            Connection connection = connect();
            //connection.setAutoCommit(false);

            //deleteUserRecords(connection, userID);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            //connection.commit();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteUserRecords(Connection connection, Integer userID) {
        String sql;
        PreparedStatement preparedStatement;
        String findData = "SELECT data_id FROM Data WHERE user_id=?";
        try {

            PreparedStatement preparedStatementFind = connection.prepareStatement(findData);
            preparedStatementFind.setInt(1, userID);
            ResultSet resultSet = preparedStatementFind.executeQuery();

            while (resultSet.next()) {
                Integer dataId = resultSet.getInt("data_id");

                sql = "DELETE FROM CoOrdinate WHERE data_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, dataId);
                preparedStatement.executeUpdate();
                sql = "DELETE FROM Heartrate WHERE data_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, dataId);
                preparedStatement.executeUpdate();
                sql = "DELETE FROM Activity_Time WHERE data_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, dataId);
                preparedStatement.executeUpdate();
            }


            sql = "DELETE FROM Data WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Activity_List WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Activity_Collection WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM parser_keywords WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM bmi_record WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM weight_record WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            deleteGoals(connection, userID);




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Perform an SQL update for the User with new attributes to store in the database
     * @param connection the connection to the database
     * @param userId the unique user ID to indentify the user
     * @param name the new Name for the user
     * @param weight the new Weight for the user in kg
     * @param height the new Height for the user in cm
     * @param age the new age for the user
     * @param sex the new string Sex for the user (MALE or FEMALE)
     */
    public void updateUser(Connection connection,  Integer userId, String name, Double weight, Double height, Integer age, String sex) {
        assert null != connection && null != userId && null != name && null != weight && null != height && null != age && null != sex;

        String update = "UPDATE user SET name = ? ,"
                + "weight = ? ,"
                + "height = ? ,"
                + "age = ? ,"
                + "sex = ? "
                + "WHERE user_id = ?";

        try {
            //System.out.println("Updating User with id: " + userId);
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, weight);
            preparedStatement.setDouble(3,height);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, sex);
            preparedStatement.setInt(6, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * Perfoms an SQL Insert to put a coordinate into the table for a given activity
     * @param connection the connection to the database
     * @param latitude the latitude of the coordinate
     * @param longitude the longitude of the coordinate
     * @param elevation the elevation of the coordinate
     * @param dataId the data Id identifying what activity the coordinate relates to
     */
    public void insertCoordinate(Connection connection, Double latitude, Double longitude, Double elevation, Integer dataId){
        String sql = "INSERT INTO CoOrdinate VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            preparedStatement.setDouble(3, elevation);
            preparedStatement.setInt(4, dataId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perfoms an SQL Insert to put a heartrate into the table for a given activity
     * @param connection the connection to the database
     * @param HeartRate the heartrate to be stored in BPM
     * @param dataId the data Id identifying what activity the heartrate relates to
     */
    public void insertHeartRate(Connection connection, Integer HeartRate, Integer dataId) {
        String sql = "INSERT INTO Heartrate VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, HeartRate);
            preparedStatement.setInt(2, dataId);

            System.out.println("Rows added to Heartrate: " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perfoms an SQL Insert to put an ActivityTime into the table for a given activity
     * @param connection the connection to the database
     * @param dataId the data Id identifying what activity the heart rate relates to
     * @param dateTime the date and time formatted string to be inserted
     */
    public void insertActivityTime(Connection connection, Integer dataId, String dateTime) {
        String sql = "INSERT INTO Activity_Time VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dataId);
            preparedStatement.setString(2, dateTime);

            System.out.println("Rows added to Activity_Time: " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    /**
     * Perform an SQL Insert to put an ActivityList into the database, if it already exists in the database doesn't execute the Insert
     * @param title the title of the ActivityList to insert
     * @param date the creation date of the ActivityList as a date object
     * @param userId the unique user Id to identify which User the ActivityList belongs
     */
    public void insertActivityList(String title, Date date, Integer userId) {
        String sql = "INSERT INTO Activity_List VALUES(?,?,?)";
        try {
            String datetime = getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(date));
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, datetime);
            preparedStatement.setInt(3, userId);

            String find = "SELECT * from Activity_List where title=? AND date=?";
            PreparedStatement findStatement = connection.prepareStatement(find);
            findStatement.setString(1, title);
            findStatement.setString(2, datetime);
            ResultSet results = findStatement.executeQuery();
            if (results.next()) {
                System.out.println("Activity_List Tuple already exists");
            } else {
                // Print out the result of the insert statement, 0 means nothing has been inserted
                System.out.println("Rows added to Activity List:" + preparedStatement.executeUpdate());
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Perform an SQL Insert to put an activity collection into the Database, if it already exists in the database doesn't execute the Insert
     * @param connection the connection to the database
     * @param userId the unique user Id that identifies which user the Activity Collection belongs to
     * @param title the title for the activity collection
     */
    public void insertActivityCollection(Connection connection, Integer userId, String title) {
        String sql = "INSERT INTO Activity_Collection VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, title);

            String find = "SELECT * from Activity_Collection where user_id=?";
            PreparedStatement findStatement = connection.prepareStatement(find);
            findStatement.setInt(1, userId);
            ResultSet results = findStatement.executeQuery();
            if (results.next()) {
                System.out.println("Activity_Collection Tuple already exists");
            } else {
                // Print out the result of the insert statement, 0 means nothing has been inserted
                System.out.println("Rows added to Activity Collection:" + preparedStatement.executeUpdate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * written by sam, adds a Parser key word to the database.
     * @param userId
     * @param phrase
     * @param type
     */
    public void addParserKeyword(Integer userId, String phrase, int type) {
        String sql = "INSERT INTO parser_keywords VALUES(?,?,?)";
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2,phrase);
            preparedStatement.setInt(3,type);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * written by sam, checks for a duplicate of the sent in key word
     * @param userId
     * @param phrase
     * @return
     */
    public boolean checkDuplicateKeyword(Integer userId, String phrase) {
        String sql = "Select count(*) from parser_keywords where user_id = ? and keyword = ?";
        Connection connection = connect();
        ResultSet resultSet = null;
        boolean to_return = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2,phrase);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.getInt("count(*)") != 0) {
                to_return = true;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return to_return;
    }

    /**
     * Written by Sam, deletes a parser key word from the database
     * @param userId
     * @param phrase
     */
    public void deleteParserKeyword(Integer userId, String phrase) {
        String sql = "DELETE FROM parser_keywords WHERE user_id = ? and keyword = ?";
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2,phrase);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * written by sam, receives the next user id for the new user
     * @return
     */
    public int getUserID() {
        Connection connection = connect();
        assert null != connection;
        int userID = 0;
        ResultSet resultSet = null;
        String find = "SELECT MAX(user_id) FROM user";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            resultSet = preparedStatement.executeQuery();
            userID = resultSet.getInt("user_id") + 1;
            connection.close();
        } catch (SQLException e) {
            userID = 1;
        }
        return userID;
    }

    /**
     * Written by Sam, gets all of the key words and adds them to the parsers collection
     * @param userId
     * @param parser
     */
    public void getKeyWords(Integer userId, Parser parser) {
        Connection connection = connect();
        assert null != connection && null != userId;
        String phrase = null;
        ResultSet resultSet = null;
        int type = 0;
        String find = "SELECT keyword, activityType FROM parser_keywords WHERE user_id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                phrase = resultSet.getString("keyword");
                type = resultSet.getInt("activityType");
                parser.add(phrase, type, false);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWeightRecords(Connection connection, ArrayList<WeightRecord> weightRecordArrayList, Integer userId) {
        String sql = "INSERT INTO Weight_record VALUES(?,?,?)";
        Double weight;
        LocalDateTime dateTime;
        for (WeightRecord weightRecord: weightRecordArrayList) {
            try {
                weight = weightRecord.getWeight();
                dateTime = weightRecord.getDate();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, dateTime.toString());
                preparedStatement.setInt(2, userId);
                preparedStatement.setDouble(3, weight);

                String find = "SELECT * from Weight_record where user_id=? AND record_datetime=?";
                PreparedStatement findStatement = connection.prepareStatement(find);
                findStatement.setInt(1, userId);
                findStatement.setString(2, dateTime.toString());
                ResultSet results = findStatement.executeQuery();
                if (results.next()) {
                    System.out.println("WeightRecord Tuple already exists");
                } else {
                    // Print out the result of the insert statement, 0 means nothing has been inserted
                    System.out.println("Rows added to WeightRecord:" + preparedStatement.executeUpdate());
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void insertBMIRecords(Connection connection, ArrayList<BMITypeRecord> bmiTypeRecordArrayList, Integer userId) {
        String sql = "INSERT INTO BMI_Record VALUES(?,?,?)";
        Double bmi;
        LocalDateTime dateTime;
        for (BMITypeRecord bmiTypeRecord: bmiTypeRecordArrayList) {
            bmi = bmiTypeRecord.getBmi().getBMIValue();
            dateTime = bmiTypeRecord.getDate();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, dateTime.toString());
                preparedStatement.setInt(2, userId);
                preparedStatement.setDouble(3, bmi);

                String find = "SELECT * from BMI_Record where user_id=? AND record_datetime=?";
                PreparedStatement findStatement = connection.prepareStatement(find);
                findStatement.setInt(1, userId);
                findStatement.setString(2, dateTime.toString());
                ResultSet results = findStatement.executeQuery();
                if (results.next()) {
                    System.out.println("BMIRecord Tuple already exists");
                } else {
                    // Print out the result of the insert statement, 0 means nothing has been inserted
                    System.out.println("Rows added to BMIRecord:" + preparedStatement.executeUpdate());
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public ArrayList<WeightRecord> getWeightRecords(Connection connection, Integer userId) {
        String find = "SELECT record_datetime, weight FROM Weight_record WHERE user_id=?";
        ResultSet resultSet = null;
        WeightRecord weightRecord;
        LocalDateTime recordDatetime;
        Double recordWeight;
        ArrayList<WeightRecord> weightRecordArrayList = new ArrayList<WeightRecord>();

        try {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                recordDatetime = getLocalDateTimeFromString(resultSet.getString("record_datetime"));
                recordWeight = resultSet.getDouble("weight");
                weightRecord = new WeightRecord(recordWeight);
                weightRecord.setDate(recordDatetime);
                weightRecordArrayList.add(weightRecord);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  weightRecordArrayList;
    }

    public ArrayList<BMITypeRecord> getBMIRecords(Connection connection, Integer userId) {
        String find = "SELECT record_datetime, bmi FROM BMI_Record WHERE user_id=?";
        ResultSet resultSet = null;
        BMITypeRecord bmiRecord;
        LocalDateTime recordDatetime;
        Double recordBMI;
        BMI bmi;
        ArrayList<BMITypeRecord> bmiRecordArrayList = new ArrayList<BMITypeRecord>();

        try {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                recordDatetime = getLocalDateTimeFromString(resultSet.getString("record_datetime"));
                recordBMI = resultSet.getDouble("bmi");
                bmi = new BMI(recordBMI);
                bmiRecord = new BMITypeRecord(bmi);
                bmiRecord.setDate(recordDatetime);
                bmiRecordArrayList.add(bmiRecord);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  bmiRecordArrayList;
    }

    public void insertActivityGoal(Connection connection, ActivityGoal activityGoal, Integer userId) {
        String sql = "INSERT INTO Activity_Goal VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, activityGoal.getDistanceCurrentlyCovered());
            preparedStatement.setDouble(2, activityGoal.getTarget());
            preparedStatement.setString(3, activityGoal.getTargetDate().toString());
            preparedStatement.setString(4, getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(activityGoal.getStartDate())));
            preparedStatement.setString(5, activityGoal.getDataType().toString());
            preparedStatement.setString(6, activityGoal.getDescription());
            preparedStatement.setInt(7, userId);
            System.out.println("Rows added to Activity_Goal: " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWeightGoal(Connection connection, WeightLossGoal weightLossGoal, Integer userId) {
        String sql = "INSERT INTO Weight_Goal VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(6, weightLossGoal.getStartWeight());
            preparedStatement.setDouble(5, weightLossGoal.getTarget());
            preparedStatement.setString(4, weightLossGoal.getTargetDate().toString());
            preparedStatement.setString(3, getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(weightLossGoal.getStartDate())));
            preparedStatement.setString(2, weightLossGoal.getDescription());
            preparedStatement.setInt(1, userId);
            System.out.println("Rows added to Weight_Goal: " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertFrequencyGoal(Connection connection, FrequencyGoal frequencyGoal, Integer userId) {
        String sql = "INSERT INTO Frequency_Goal VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(7, frequencyGoal.getTimesCurrentlyPerformedActivity());
            preparedStatement.setInt(6, frequencyGoal.getTarget().intValue());
            preparedStatement.setString(5, frequencyGoal.getTargetDate().toString());
            preparedStatement.setString(4, getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(frequencyGoal.getStartDate())));
            preparedStatement.setString(3, frequencyGoal.getDataType().toString());
            preparedStatement.setString(2, frequencyGoal.getDescription());
            preparedStatement.setInt(1, userId);
            System.out.println("Rows added to Frequency_Goal: " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertGoals(Connection connection, User user, Integer userId) {
        ArrayList<Goal> activityGoalsList = user.getGoalsService().getCurrentActivityGoals();
        activityGoalsList.addAll(user.getGoalsService().getPreviousActivityGoals());
        for (Goal goal: activityGoalsList) {
            if (goal.getGoalType().toString().equals("WeightLossGoal")) {
                WeightLossGoal weightLossGoal = (WeightLossGoal) goal;
                insertWeightGoal(connection, weightLossGoal, userId);
            } else if (goal.getGoalType().toString().equals("ActivityGoal")) {
                ActivityGoal activityGoal = (ActivityGoal) goal;
                insertActivityGoal(connection, activityGoal, userId);
            } else if (goal.getGoalType().toString().equals("TimePerformedGoal")) {
                FrequencyGoal frequencyGoal = (FrequencyGoal) goal;
                insertFrequencyGoal(connection, frequencyGoal, userId);
            } else {
                System.out.println("No matching goal type");
            }
        }

    }

    public void deleteGoals(Connection connection, Integer userId) {

        try {
            String sql = "DELETE FROM Activity_Goal WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Weight_Goal WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Frequency_Goal WHERE user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void getGoals(Connection connection, User user, Integer userId) {
        String activitysql = "SELECT distance_covered, distance_to_cover, target_date, start_date, type, description FROM Activity_Goal WHERE user_id=?";
        String weightsql = "SELECT description, start_date, target_date, target_weight, start_weight FROM Weight_Goal WHERE user_id=?";
        String frequencysql = "SELECT description, type, start_date, target_date, times_to_perform, times_performed FROM Frequency_Goal WHERE user_id=?";;

        try {
            PreparedStatement activityStatement = connection.prepareStatement(activitysql);
            activityStatement.setInt(1, userId);
            ResultSet resultSet = activityStatement.executeQuery();
            while(resultSet.next()) {
                ActivityGoal activityGoal = new ActivityGoal(user, resultSet.getString("description"),
                        GoalType.ActivityGoal,
                        DataType.fromStringToEnum(resultSet.getString("type").toUpperCase()),
                        resultSet.getDouble("distance_to_cover"),
                        getLocalDateTimeFromString(resultSet.getString("target_date")));
                activityGoal.setCurrent(resultSet.getDouble("distance_covered"));
                activityGoal.setStartDate(getDateFromString(resultSet.getString("start_date")));
                user.getGoalsService().getCurrentActivityGoals().add(activityGoal);
            }

            PreparedStatement weightStatement = connection.prepareStatement(weightsql);
            weightStatement.setInt(1, userId);
            resultSet = weightStatement.executeQuery();
            while(resultSet.next()) {
                WeightLossGoal weightLossGoal = new WeightLossGoal(user, resultSet.getString("description"),
                        GoalType.ActivityGoal,
                        resultSet.getDouble("target_weight"),
                        getLocalDateTimeFromString(resultSet.getString("target_date")));
                weightLossGoal.setStartWeight(resultSet.getDouble("start_weight"));
                weightLossGoal.setStartDate(getDateFromString(resultSet.getString("start_date")));
                user.getGoalsService().getCurrentWeightLossGoals().add(weightLossGoal);
            }

            PreparedStatement frequencyStatement = connection.prepareStatement(frequencysql);
            weightStatement.setInt(1, userId);
            resultSet = weightStatement.executeQuery();
            while(resultSet.next()) {
                FrequencyGoal frequencyGoal = new FrequencyGoal(user, resultSet.getString("description"),
                        GoalType.ActivityGoal,
                        DataType.fromStringToEnum(resultSet.getString("type").toUpperCase()),
                        resultSet.getInt("times_to_perform"),
                        getLocalDateTimeFromString(resultSet.getString("target_date")));
                frequencyGoal.setTimesCurrentlyPerformedActivity(resultSet.getInt("times_performed"));
                frequencyGoal.setStartDate(getDateFromString(resultSet.getString("start_date")));
                user.getGoalsService().getCurrentWeightLossGoals().add(frequencyGoal);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }


    /**
     * Performs an SQL Select to get the title of the Users activity list collection from the Database
     * @param connection the connection to the database
     * @param userId the unique Id of the user identifying which ActivityCollection to retrieve
     * @return the title String of the activity collection
     */
    public String getUsersActivityListCollectionTitle(Connection connection, Integer userId) {
        assert null != connection && null != userId;
        String collectionTitle = null;
        ResultSet resultSet = null;
        System.out.println("Get Users ActivityListCollection with id: " + userId );
        String find = "SELECT title FROM Activity_Collection WHERE user_id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                collectionTitle = resultSet.getString("title");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return collectionTitle;
    }


    /**
     * Perform an SQL select to get all the Users activity lists
     * @param connection the connection to the database
     * @param userId the unique Id of the user identifying which lists to retrieve
     * @param user the User whose lists need to be retrieved
     * @return an Array List of activity lists populated with the data from the database
     */
    public ArrayList<ActivityList> getUsersActivityLists(Connection connection, Integer userId, User user) {
        assert null != connection && null != userId;
        ArrayList<ActivityList> listOfActivities= new ArrayList<ActivityList>();
        ActivityList activityList = null;
        String title = null;
        String date = null;

        System.out.println("Get Users Activities with id: " + userId );
        String find = "SELECT title, date FROM Activity_List WHERE user_id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(find);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                title = resultSet.getString("title");
                date = resultSet.getString("date");
                activityList = new ActivityList(title);
                activityList.setCreationDate(getDateFromString(date));
                ArrayList<Data> dataArrayList = getActivityListData(connection, title, date, user);
                for (Data d: dataArrayList) {
                    System.out.println(d.getTitle());
                    activityList.insertActivity(d);
                }
                listOfActivities.add(activityList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listOfActivities;
    }

    /**
     * Perform an SQL select to get Data objects for an Activity List
     * @param connection the connection to the database
     * @param activityTitle the title of the activity list (part of foreign key needed)
     * @param activityDate the String creation date of the activity list (part of foreign key needed)
     * @param user the User whose data needs to be retrieved
     * @return an Array List of data objects fully populated
     */
    public ArrayList<Data> getActivityListData(Connection connection, String activityTitle, String activityDate, User user) {
        assert null != connection && null != activityTitle && null != activityDate;
        ArrayList<Data> activityListData = new ArrayList<Data>();

        Integer dataID;
        Integer userID;
        String dataType;
        DataType dataTypeEnum;
        Data data = null;
        ArrayList<LocalDateTime> newDateTimes;
        ArrayList<CoordinateData> newCoordinatesList;
        ArrayList<Integer> newHeartRateList;
        String title;

        String find = "SELECT data_id, user_id, data_type, activity_title FROM Data WHERE title=? AND date=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, activityTitle);
            preparedStatement.setString(2, activityDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dataID = resultSet.getInt("data_id");
                userID = resultSet.getInt("user_id");
                dataType = resultSet.getString("data_type");
                title = resultSet.getString("activity_title");
                newDateTimes = getActivityDateTimes(connection, dataID);
                newCoordinatesList = getActivityCoordinates(connection, dataID);
                newHeartRateList = getActivityHeartRates(connection, dataID);
                System.out.println("Data created" +  "DataID:" + dataID);
                switch (dataType) {
                    case "WALK":
                        dataTypeEnum = DataType.WALK;
                        data = new WalkData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                    case "HIKE":
                        dataTypeEnum = DataType.HIKE;
                        data = new HikeData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                    case "RUN":
                        dataTypeEnum = DataType.RUN;
                        data = new RunData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                    case "CLIMB":
                        dataTypeEnum = DataType.CLIMB;
                        data = new ClimbData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                    case "BIKE":
                        dataTypeEnum = DataType.BIKE;
                        data = new BikeData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                    case "SWIM":
                        dataTypeEnum = DataType.SWIM;
                        data = new SwimData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                    case "WATER_SPORTS":
                        dataTypeEnum = DataType.WATER_SPORTS;
                        data = new WaterSportsData(title, dataTypeEnum, newDateTimes, newCoordinatesList, newHeartRateList, user);
                        break;
                }
                if (data!=null) {
                    data.setDataId(dataID);
                    activityListData.add(data);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return activityListData;

    }

    /**
     * Perform an SQL select to get an activities heart rate list
     * @param connection the connection to the database
     * @param dataId the unique data Id identifying which activity the heart rates are needed for
     * @return an Array List of integers representing heart rate in BPM
     */
    public ArrayList<Integer> getActivityHeartRates(Connection connection, Integer dataId) {
        assert null != connection && null != dataId;
        ArrayList<Integer> heartRateData = new ArrayList<Integer>();
        Integer heartRate;
        String find = "SELECT HeartRateBPM FROM Heartrate WHERE data_id=?";

        try {
            PreparedStatement preparedStatement  = connection.prepareStatement(find);
            preparedStatement.setInt(1, dataId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                heartRate = resultSet.getInt("HeartRateBPM");
                heartRateData.add(heartRate);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return heartRateData;

    }

    /**
     * Perform an SQL select to get an activities times list
     * @param connection the connection to the database
     * @param dataId the unique data Id identifying which activity the times are needed for
     * @return an Array List of LocalDateTime objects representing the times for the Data
     */
    public ArrayList<LocalDateTime> getActivityDateTimes(Connection connection, Integer dataId) {
        assert null != connection && null != dataId;
        ArrayList<LocalDateTime> dateData = new ArrayList<LocalDateTime>();
        LocalDateTime dateTime;
        String find = "SELECT DateTime FROM Activity_Time WHERE data_id=?";

        try {
            PreparedStatement preparedStatement  = connection.prepareStatement(find);
            preparedStatement.setInt(1, dataId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dateTime = getLocalDateTimeFromString(resultSet.getString("DateTime"));
                dateData.add(dateTime);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dateData;

    }

    /**
     * Perform an SQL select to get an activities coordinates list
     * @param connection the connection to the database
     * @param dataId the unique data Id identifying which activity the coordinates are needed for
     * @return an Array List of CoordinateData objects representing the times for the Data
     */
    public ArrayList<CoordinateData> getActivityCoordinates(Connection connection, Integer dataId) {
        assert null != connection && null != dataId;
        ArrayList<CoordinateData> coordinateData = new ArrayList<CoordinateData>();
        Double latitude;
        Double longitude;
        Double elevation;
        CoordinateData coordinate;
        String find = "SELECT latitude, longitude, elevation FROM CoOrdinate WHERE data_id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setInt(1, dataId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                latitude = resultSet.getDouble("latitude");
                longitude = resultSet.getDouble("longitude");
                elevation = resultSet.getDouble("elevation");
                coordinate = new CoordinateData(latitude, longitude, elevation);
                coordinateData.add(coordinate);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return coordinateData;
    }


    /**
     * Performs SQL Deletes to delete all data in the database
     */
    public void deleteAllData() {
        try {
            Connection connection = connect();
            String sql = "DELETE FROM user";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM Activity_Collection";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM Activity_List";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM Data";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM CoOrdinate";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM Heartrate";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM Activity_Time";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM parser_keywords";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM bmi_record";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "DELETE FROM weight_record";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO implement updateActivity for edits

    public void updateActivity(Data data, ActivityList newActivityList) {
        String update = "UPDATE data SET "
                + "data_type = ?, "
                + "activity_title = ?, "
                + "title = ?, "
                + "date = ? "
                + "WHERE data_id = ?";

        String parentListDateTimeString = getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(newActivityList.getCreationDate()));

        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, data.getDataType().toString());
            preparedStatement.setString(2, data.getTitle());
            preparedStatement.setString(3, newActivityList.getTitle());
            preparedStatement.setString(4, parentListDateTimeString);
            preparedStatement.setInt(5, data.getDataId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void deleteActivity(Integer dataId) {
        try{
            Connection connection = connect();

            String sqlCoordinate = "DELETE FROM CoOrdinate WHERE data_id=?";
            PreparedStatement preparedStatementCoordinate = connection.prepareStatement(sqlCoordinate);
            preparedStatementCoordinate.setInt(1, dataId);
            preparedStatementCoordinate.executeUpdate();
            String sqlHeartRate = "DELETE FROM Heartrate WHERE data_id=?";
            PreparedStatement preparedStatementHeartRate = connection.prepareStatement(sqlHeartRate);
            preparedStatementHeartRate.setInt(1, dataId);
            preparedStatementHeartRate.executeUpdate();
            String sqlActivityTime = "DELETE FROM Activity_Time WHERE data_id=?";
            PreparedStatement preparedStatementTime = connection.prepareStatement(sqlActivityTime);
            preparedStatementTime.setInt(1, dataId);
            preparedStatementTime.executeUpdate();
            String sqlData = "DELETE FROM Data WHERE data_id=?";
            PreparedStatement preparedStatementData = connection.prepareStatement(sqlData);
            preparedStatementData.setInt(1, dataId);
            preparedStatementData.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO: are you sure this works properly?
    public void deleteActivityList(String parentActivityListTitle, Date parentActivityListDate) {
        String parentActivityListDateString = getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(parentActivityListDate));
        String find = "SELECT data_id FROM Data WHERE title=? AND date=?";
        System.out.println("Deletion from activity List: " + parentActivityListTitle +  "   " + parentActivityListDate);
        try {
            Connection connection = connect();
            PreparedStatement preparedStatementFind = connection.prepareStatement(find);
            preparedStatementFind.setString(1, parentActivityListTitle);
            preparedStatementFind.setString(2, parentActivityListDateString);
            ResultSet resultSet = preparedStatementFind.executeQuery();
            while (resultSet.next()) {
                Integer dataID = resultSet.getInt("data_id");
                System.out.println("Data deleted" +  "DataID:" + dataID);

                String sqlCoordinate = "DELETE FROM CoOrdinate WHERE data_id=?";
                PreparedStatement preparedStatementCoordinate = connection.prepareStatement(sqlCoordinate);
                preparedStatementCoordinate.setInt(1, dataID);
                preparedStatementCoordinate.executeUpdate();
                String sqlHeartRate = "DELETE FROM Heartrate WHERE data_id=?";
                PreparedStatement preparedStatementHeartRate = connection.prepareStatement(sqlHeartRate);
                preparedStatementHeartRate.setInt(1, dataID);
                preparedStatementHeartRate.executeUpdate();
                String sqlActivityTime = "DELETE FROM Activity_Time WHERE data_id=?";
                PreparedStatement preparedStatementTime = connection.prepareStatement(sqlActivityTime);
                preparedStatementTime.setInt(1, dataID);
                preparedStatementTime.executeUpdate();
                String sqlData = "DELETE FROM Data WHERE title=? AND date=?";
                PreparedStatement preparedStatementData = connection.prepareStatement(sqlData);
                preparedStatementData.setString(1, parentActivityListTitle);
                preparedStatementData.setString(2, parentActivityListDateString);
                preparedStatementData.executeUpdate();
            }
            String activityDelete = "DELETE FROM Activity_List WHERE title=? AND date=?";
            PreparedStatement preparedStatementActivityList = connection.prepareStatement(activityDelete);
            preparedStatementActivityList.setString(1, parentActivityListTitle);
            preparedStatementActivityList.setString(2, parentActivityListDateString);
            preparedStatementActivityList.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Performs SQL inserts as a single transaction to add a list of data to the database
     * @param dataList the Array List of new Data objects to be added
     * @param parentListTitle the title of the activity list the data is for
     * @param parentListDatetime the creation date of the activity list the data is for
     * @param userId the unique userId to relate the date to a user
     */
    public void updateWithListOfData(ArrayList<Data> dataList, String parentListTitle, Date parentListDatetime, Integer userId) {

        String dataUpdate = "INSERT INTO Data VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;


        try {
            Connection connection = connect();
            connection.setAutoCommit(false);
            String parentListDateTimeString = getStringFromLocalDateTime(convertToLocalDateTimeViaInstant(parentListDatetime));

            for (Data activity : dataList) {
                Integer newDataId = activity.getDataId();
                preparedStatement = connection.prepareStatement(dataUpdate);
                preparedStatement.setInt(1, newDataId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setString(3, activity.getDataType().toString());
                preparedStatement.setString(4, parentListTitle);
                preparedStatement.setString(5, parentListDateTimeString);
                preparedStatement.setString(6, activity.getTitle());
                preparedStatement.executeUpdate();
                for (CoordinateData coordinate : activity.getCoordinatesArrayList()) {
                    insertCoordinate(connection, coordinate.getLatitude(), coordinate.getLongitude(), coordinate.getAltitude(), newDataId);
                }
                for (Integer heartRate : activity.getHeartRateList()) {
                    insertHeartRate(connection, heartRate, newDataId);
                }
                for (LocalDateTime localDateTime: activity.getAllDateTimes()) {
                    insertActivityTime(connection, newDataId, localDateTime.toString());
                }
            }

            connection.commit();
            preparedStatement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void updateActivityListCollection(Connection connection, Integer userId, String newTitle) {
        String sql = "UPDATE Activity_Collection SET title = ? WHERE user_id=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Performs the necessary database functions to save a User into the database new or existing
     * @param user the User to be stored
     * @param userId the id of the user to be stored which uniquely identifies them
     */
    public void saveUser(User user, Integer userId) {
        Connection conn = connect();
        insertUser(conn, userId, user.getName(), user.getWeight(), user.getHeight(), user.getAge(), user.getSex().toString());
        updateUser(conn, userId, user.getName(), user.getWeight(), user.getHeight(), user.getAge(), user.getSex().toString());


        ActivityListCollection activityListCollection = user.getUserActivities();

        insertActivityCollection(conn, userId, activityListCollection.getTitle());
        updateActivityListCollection(conn, userId, activityListCollection.getTitle());
        insertWeightRecords(conn, user.getUserStats().getUserWeightRecords(), userId);
        insertBMIRecords(conn, user.getUserStats().getUserBMITypeRecords(), userId);

        deleteGoals(conn, userId);
        insertGoals(conn, user, userId); //TODO uncomment when goals is in GUI

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Gets the next data Id to assign to a new data object to be stored
     * @return the next DataId available in the database
     */
    public Integer getNextDataID() {
        String find = "SELECT MAX(data_id) FROM Data";
        Integer newId = 0;
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement  = connection.prepareStatement(find);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                newId = resultSet.getInt("MAX(data_id)");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return newId + 1;


    }



    /**
     * Performs the necessary database function to retrieve a user along with all its data from the database
     * @param userId the unique Id of the User to be retrieved
     * @return a new User object representing the User asked for
     */
    public User retrieveUser(Integer userId) {
        Connection conn = connect();
        ResultSet result = getUser(conn, userId);
        try {
            String name = result.getString("name");
            Double weight = result.getDouble("weight");
            Double height = result.getDouble("height");
            Integer age = result.getInt("age");
            String sex = result.getString("sex");
            User user = new User(name, age, weight, height, Sex.valueOf(sex));

            String title = getUsersActivityListCollectionTitle(conn, userId);
            ActivityListCollection userCollection = new ActivityListCollection(title);
            user.setUserActivities(userCollection);
            ArrayList<ActivityList> activityList = getUsersActivityLists(conn, userId, user);
            user.getUserActivities().setActivityListCollection(activityList);

            //Weight and BMI records
            UserStats userStats = new UserStats();
            userStats.setUserWeightRecords(getWeightRecords(conn, userId));
            userStats.setUserBMITypeRecords(getBMIRecords(conn, userId));
            user.setUserStats(userStats);

            getGoals(conn, user, userId); //TODO uncomment this call when goals finished in database

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return null;

    }

    /*
    public boolean checkDuplicateKeyword(Integer userId, String phrase) {
        String sql = "Select count(*) from parser_keywords where user_id = ? and keyword = ?";
        Connection connection = connect();
        ResultSet resultSet = null;
        boolean to_return = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2,phrase);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.getInt("count(*)") != 0) {
                to_return = true;
            }public Integer getNextUserId() {
        String find = "SELECT MAX(user_id) FROM user";
        Integer newId = 0;
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement  = connection.prepareStatement(find);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                newId = resultSet.getInt("MAX(user_id)");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return newId + 1;
    }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return to_return;
    }
    */
    /**
     * Performs the necessary database function to retrieve a user along with all its data from the database
     * @return a new User object representing the User asked for
     */
    public ArrayList<User> retrieveAllUsers() {
        ArrayList<User> to_return = new ArrayList<User>();
        int userId = 0;
        Connection conn = connect();
        ResultSet result = null;
        ResultSet result2 = null;
        String sql = "Select user_id from user";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                userId = result.getInt("user_id");
                result2 = getUser(conn, userId);
                String name = result2.getString("name");
                Double weight = result2.getDouble("weight");
                Double height = result2.getDouble("height");
                Integer age = result2.getInt("age");
                String sex = result2.getString("sex");
                User user = new User(name, age, weight, height, Sex.valueOf(sex), userId);

                String title = getUsersActivityListCollectionTitle(conn, userId);
                ActivityListCollection userCollection = new ActivityListCollection(title);
                user.setUserActivities(userCollection);
                ArrayList<ActivityList> activityList = getUsersActivityLists(conn, userId, user);
                user.getUserActivities().setActivityListCollection(activityList);

                //Weight and BMI records
                UserStats userStats = new UserStats();
                userStats.setUserWeightRecords(getWeightRecords(conn, userId));
                userStats.setUserBMITypeRecords(getBMIRecords(conn, userId));
                user.setUserStats(userStats);

                getGoals(conn, user, userId); //TODO uncomment this call when goals finished in database

                to_return.add(user);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return to_return;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public static void main(String[] args) {
        SQLiteJDBC newDataBaseJDBC = new SQLiteJDBC();
        newDataBaseJDBC.deleteAllData();

    }
}
