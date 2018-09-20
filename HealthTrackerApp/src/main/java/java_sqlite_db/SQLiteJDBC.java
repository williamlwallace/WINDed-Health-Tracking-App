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

public class SQLiteJDBC {



    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Connect to a sample database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:winded.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

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

    public void insertActivityList(Connection connection, String title, String datetime, Integer userId) {
        String sql = "INSERT INTO Activity_List VALUES(?,?,?)";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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

    public Date getDateFromString(String dateString) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = dateFormat.parse(dateString);
            //System.out.println(date.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public LocalDateTime getLocalDateTimeFromString(String dateString) {
        LocalDateTime localDateTime = null;
        localDateTime = LocalDateTime.parse(dateString);
        return localDateTime;
    }

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

    public void saveUser(User user, Integer userId) {
        Connection conn = connect();
        insertUser(conn, userId, user.getName(), user.getWeight(), user.getHeight(), user.getAge(), user.getSex().toString());
        updateUser(conn, userId, user.getName(), user.getWeight(), user.getHeight(), user.getAge(), user.getSex().toString());

        Integer dataId;


        ActivityListCollection activityListCollection = user.getUserActivities();
        insertActivityCollection(conn, userId, activityListCollection.getTitle());

        for (ActivityList activityList : activityListCollection.getActivityListCollection()) {
            insertActivityList(conn, activityList.getTitle(), convertToLocalDateTimeViaInstant(activityList.getCreationDate()).toString(), userId);
            for (Data activity : activityList.getActivityList()) {
                dataId = getNextDataID(conn);
                if (insertData(conn, dataId, userId, activity.getDataType().toString(), activityList.getTitle(), convertToLocalDateTimeViaInstant(activityList.getCreationDate()).toString(), activity.getTitle())) {
                    for (CoordinateData coordinate : activity.getCoordinatesArrayList()) {
                        insertCoordinate(conn, coordinate.getLatitude(), coordinate.getLongitude(), coordinate.getAltitude(), dataId);
                    }
                    for (Integer heartRate : activity.getHeartRateList()) {
                        insertHeartRate(conn, heartRate, dataId);
                    }
                    for (LocalDateTime localDateTime: activity.getAllDateTimes()) {
                        insertActivityTime(conn, dataId, localDateTime.toString());
                    }
            }

            }
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

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


            newDataBaseJDBC.saveUser(userTest, 1);
            System.out.println(userTest.getName());
            System.out.println(userTest.getAge());
            System.out.println(userTest.getWeight());
            System.out.println(userTest.getHeight());
            System.out.println(userTest.getSex());
            for (ActivityList a : userTest.getUserActivities().getActivityListCollection()) {
                System.out.println(a.getTitle());
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
            }

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
















        /*SQLiteJDBC newDataBaseJDBC = new SQLiteJDBC();
        Connection conn = connect();
        newDataBaseJDBC.deleteAllData(conn);
        newDataBaseJDBC.insertUser(conn, 1, "Jack", 77.0, 183.0, 19, "MALE");
        newDataBaseJDBC.insertUser(conn, 2, "John", 89.0, 193.0, 25, "MALE");
        newDataBaseJDBC.updateUser(conn, 2, "Jonathon", 90.0, 200.0, 26, "FEMALE");
        ResultSet allUsers2 = newDataBaseJDBC.selectAllUsers(conn);
        ResultSet oneUser2 = newDataBaseJDBC.getUser(conn, 1);
        try {
            while (allUsers2.next()) {
                System.out.println(allUsers2.getInt("user_id") +  "\t" +
                        allUsers2.getString("name") + "\t" +
                        allUsers2.getDouble("weight") + "\t" +
                        allUsers2.getDouble("height") + "\t" +
                        allUsers2.getInt("age") + "\t" +
                        allUsers2.getString("sex"));

            }
            //if (conn != null) {
            //    conn.close();
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("-----------------------------------------------------------------------");

        //now try adding data
        newDataBaseJDBC.insertActivityCollection(conn, 1, "Jack's Activity collection");
        newDataBaseJDBC.insertActivityList(conn, "Runs", "2018-09-10/11:00", 1);
        newDataBaseJDBC.insertActivityList(conn, "Walks", "2018-09-10/11:00", 1);
        newDataBaseJDBC.insertData(conn, 1,1, "RUN","Runs", "2018-09-10/11:00");
        newDataBaseJDBC.insertData(conn, 2,1, "RUN","Runs", "2018-10-10/11:00");
        newDataBaseJDBC.insertCoordinate(conn, 30.2553368, 97.83891084, 239.5, 1);
        newDataBaseJDBC.insertCoordinate(conn, 30.2553368, 197.83891084, 239.5, 2);
        newDataBaseJDBC.insertHeartRate(conn, 250, 1);
        newDataBaseJDBC.insertHeartRate(conn, 250, 2);
        newDataBaseJDBC.insertActivityTime(conn, 1,"2018-12-10/10:33");
        newDataBaseJDBC.insertActivityTime(conn, 2,"2018-12-10/10:34");


        newDataBaseJDBC.insertActivityCollection(conn, 2, "Jonathon's Activity collection");
        newDataBaseJDBC.insertActivityList(conn, "Runs", "2018-09-10/10:38", 2);
        newDataBaseJDBC.insertData(conn, 3,2, "RUN","Runs", "2018-09-10/10:38");
        newDataBaseJDBC.insertData(conn, 4,2, "RUN","Runs", "2018-10-10/10:38");
        newDataBaseJDBC.insertCoordinate(conn, 30.2553368, 97.83891084, 239.5, 3);
        newDataBaseJDBC.insertCoordinate(conn, 30.2553368, 197.83891084, 239.5, 4);
        newDataBaseJDBC.insertHeartRate(conn, 250, 3);
        newDataBaseJDBC.insertHeartRate(conn, 250, 4);
        newDataBaseJDBC.insertActivityTime(conn, 3,"2018-12-10/10:33");
        newDataBaseJDBC.insertActivityTime(conn, 4,"2018-12-10/10:34");

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

    }
}
