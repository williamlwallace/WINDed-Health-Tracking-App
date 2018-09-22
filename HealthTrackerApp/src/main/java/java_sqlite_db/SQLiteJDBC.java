package java_sqlite_db;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.*;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import javax.jnlp.IntegrationService;
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
            String url = "jdbc:sqlite:winded.db";

            //Lorenzo's trial, I created a folder where I copied the database, all your database instances are still there
            // Also I noticed that the database gets created just outside the .jar file, maybe this can help??
//            String url = "/resources/views/database/jdbc:sqlite:winded.db";

            ///


            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

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
                System.out.println("User Tuple already exists");
            } else {
                // Print out the result of the insert statement, 0 means nothing has been inserted
                System.out.println("Rows added to user: " + preparedStatement.executeUpdate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ResultSet selectAllUsers(Connection connection) {
        assert null != connection;
        System.out.println("Get all tuples");
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
        System.out.println("Get User with id: " + userID );
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

    public void deleteUser(Connection connection, Integer userID) {
        String sql = "DELETE FROM user WHERE user_id=?";
        try {
            System.out.println("Deleting User with id = " + userID);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
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
            System.out.println("Updating User with id: " + userId);
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

            System.out.println("Rows added to Co-Ordinate: " + preparedStatement.executeUpdate());
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

    public Boolean insertData(Connection connection, Integer dataId, Integer userId, String dataType, String parentListTitle, String parentListDatetime, String activityTitle) {
        String sql = "INSERT INTO Data VALUES(?,?,?,?,?,?)";
        Boolean isInserted = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dataId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, dataType);
            preparedStatement.setString(4, parentListTitle);
            preparedStatement.setString(5, parentListDatetime);
            preparedStatement.setString(6, activityTitle);



            String find = "SELECT * from Data where data_id=?";
            PreparedStatement findStatement = connection.prepareStatement(find);
            findStatement.setInt(1, dataId);
            ResultSet results = findStatement.executeQuery();
            if (results.next()) {
                System.out.println("Data Tuple already exists");
                isInserted = false;
            } else {
                System.out.println("Rows added to Data:" + preparedStatement.executeUpdate());
                isInserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isInserted;
        }
    }


    /**
     * Perform an SQL Insert to put an ActivityList into the database, if it already exists in the database doesn't execute the Insert
     * @param title the title of the ActivityList to insert
     * @param datetime the creation date of the ActivityList as a formatted string
     * @param userId the unique user Id to identify which User the ActivityList belongs
     */
    public void insertActivityList(String title, String datetime, Integer userId) {
        String sql = "INSERT INTO Activity_List VALUES(?,?,?)";
        try {
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
                }
                if (data!=null) {
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
     * @param connection the connection to the database
     */
    public void deleteAllData(Connection connection) {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteListData(Connection connection, String parentActivityListTitle, String parentActivityListDate) {
        System.out.println(getNextDataID(connection));
        String find = "SELECT data_id FROM Data WHERE title=? AND date=?";
        System.out.println("Deletion from activity List: " + parentActivityListTitle +  "   " + parentActivityListDate);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, parentActivityListTitle);
            preparedStatement.setString(2, parentActivityListDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer dataID = resultSet.getInt("data_id");
                System.out.println("Data deleted" +  "DataID:" + dataID);

                String sql2 = "DELETE FROM CoOrdinate WHERE data_id=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
                preparedStatement1.setInt(1, dataID);
                preparedStatement1.executeUpdate();
                String sql3 = "DELETE FROM Heartrate WHERE data_id=?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql3);
                preparedStatement2.setInt(1, dataID);
                preparedStatement2.executeUpdate();
                String sql4 = "DELETE FROM Activity_Time WHERE data_id=?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(sql4);
                preparedStatement3.setInt(1, dataID);
                preparedStatement3.executeUpdate();
                String sql1 = "DELETE FROM Data WHERE title=? AND date=?";
                PreparedStatement preparedStatement4 = connection.prepareStatement(sql1);
                preparedStatement4.setString(1, parentActivityListTitle);
                preparedStatement4.setString(2, parentActivityListDate);
                preparedStatement4.executeUpdate();
            }
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
                Integer newDataId = getNextDataID(connection);
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
     * @param connection the connection to the database
     * @return the next DataId available in the database
     */
    public Integer getNextDataID(Connection connection) {
        String find = "SELECT MAX(data_id) FROM Data";
        Integer newId = 0;
        try {
            PreparedStatement preparedStatement  = connection.prepareStatement(find);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                newId = resultSet.getInt("MAX(data_id)");
            }
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
            //Code to get ActivityCollection and Data of user (select queries);
            User user = new User(name, age, weight, height, Sex.valueOf(sex));

            String title = getUsersActivityListCollectionTitle(conn, userId);
            ActivityListCollection userCollection = new ActivityListCollection(title);
            user.setUserActivities(userCollection);
            ArrayList<ActivityList> activityList = getUsersActivityLists(conn, userId, user);
            user.getUserActivities().setActivityListCollection(activityList);

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



    public static void main(String[] args) {
        SQLiteJDBC newDataBaseJDBC = new SQLiteJDBC();
        Connection conn = connect();
        newDataBaseJDBC.deleteAllData(conn);
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Integer numOfCoords;
        Integer numofHeartRates;
        Integer numofTimes;

        try {
            User userTest = new User("Sam", 20, 72.0, 167.0, Sex.MALE);
            ActivityList activityList = new ActivityList("Test activity List");
            Parser parserTest =  new Parser("seng202_2018_example_data_clean.csv", userTest);
            parserTest.parseFile();
            ArrayList<Data> data = new ArrayList<>(parserTest.getDataList());
            for (Data d : data) {
                System.out.println(d.getTitle());
                activityList.insertActivity(d);
            }
            userTest.getUserActivities().insertActivityList(activityList);
            newDataBaseJDBC.insertActivityList(activityList.getTitle(), newDataBaseJDBC.getStringFromLocalDateTime(newDataBaseJDBC.convertToLocalDateTimeViaInstant(activityList.getCreationDate())), 1);
            newDataBaseJDBC.saveUser(userTest, 1);
            newDataBaseJDBC.updateWithListOfData(data, activityList.getTitle(), activityList.getCreationDate(), 1);


            /*System.out.println(userTest.getName());
            System.out.println(userTest.getAge());
            System.out.println(userTest.getWeight());
            System.out.println(userTest.getHeight());
            System.out.println(userTest.getSex());
            for (ActivityList a : userTest.getUserActivities().getActivityListCollection()) {
                System.out.println(a.getTitle());
                System.out.println(a.getCreationDate());
                for (Data d : a.getActivityList()) {
                    numOfCoords = d.getCoordinatesArrayList().size();
                    numofHeartRates = d.getHeartRateList().size();
                    numofTimes = d.getAllDateTimes().size();
                    System.out.println();
                    System.out.println("*******************************************");
                    System.out.println(d.getTitle());
                    System.out.println("*******************************************");
                    System.out.println("-----------------First Items---------------");
                    System.out.println("Latitude " + d.getCoordinatesArrayList().get(0).getLatitude());
                    System.out.println("Heart rate " + d.getHeartRateList().get(0));
                    System.out.println("Time " + d.getAllDateTimes().get(0));
                    System.out.println("-----------------Last Items---------------");
                    System.out.println("Latitude " + d.getCoordinatesArrayList().get(numOfCoords-1).getLatitude());
                    System.out.println("Heart rate " + d.getHeartRateList().get(numofHeartRates-1));
                    System.out.println("Time " + d.getAllDateTimes().get(numofTimes-1));
                    System.out.println();
                }
            }*/

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("_____________________________________________________________________");
        System.out.println("_____________________________________________________________________");
        System.out.println("Now Retrieving");
        System.out.println("_____________________________________________________________________");
        System.out.println("_____________________________________________________________________");




        User userTestRetrieved = newDataBaseJDBC.retrieveUser(1);
        System.out.println(userTestRetrieved.getName());
        System.out.println(userTestRetrieved.getAge());
        System.out.println(userTestRetrieved.getWeight());
        System.out.println(userTestRetrieved.getHeight());
        System.out.println(userTestRetrieved.getSex());
        for (ActivityList a : userTestRetrieved.getUserActivities().getActivityListCollection()) {
            System.out.println(a.getTitle());
            System.out.println(a.getCreationDate());
            for (Data d : a.getActivityList()) {
                numOfCoords = d.getCoordinatesArrayList().size();
                numofHeartRates = d.getHeartRateList().size();
                numofTimes = d.getAllDateTimes().size();
                numofTimes = d.getAllDateTimes().size();
                System.out.println();
                System.out.println("*******************************************");
                System.out.println(d.getTitle());
                System.out.println("*******************************************");
                System.out.println("-----------------First Items---------------");
                System.out.println("Latitude " + d.getCoordinatesArrayList().get(0).getLatitude());
                System.out.println("Heart rate " + d.getHeartRateList().get(0));
                System.out.println("Time " + d.getAllDateTimes().get(0));
                System.out.println("-----------------Last Items---------------");
                System.out.println("Latitude " + d.getCoordinatesArrayList().get(numOfCoords-1).getLatitude());
                System.out.println("Heart rate " + d.getHeartRateList().get(numofHeartRates-1));
                System.out.println("Time " + d.getAllDateTimes().get(numofTimes-1));
                System.out.println();
            }
        }

        System.out.println("_____________________________________________________");
        System.out.println("Now Saving Again");
        System.out.println("_____________________________________________________");


        Connection connection = connect();
        newDataBaseJDBC.deleteAllData(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //newDataBaseJDBC.saveUser(userTestRetrieved, 1);
    }
    }
