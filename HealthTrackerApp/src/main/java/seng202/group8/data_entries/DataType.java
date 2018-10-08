package seng202.group8.data_entries;

/**
 * This Enumerator specifies the types of activities the user can perform.
 * One example of its use is the summary statistics on the home page, which
 * tell the user how much they have run, walked, or biked since the earliest
 * date of their first data upload.
 */
public enum DataType {
    WALK,
    RUN,
    HIKE,
    CLIMB,
    BIKE,
    WATER_SPORTS,
    SWIM,
    GENERAL,
    ASSISTED_SPORTS_DATA,
    NOT_ASSISTED_SPORTS_DATA,
    ALL;


    /**
     * This function returns the enumerator value that matches the input string.
     * @param dataTypeStr The string to be converted to an enumerator.
     * @return the enumerator that corrresponds to the input string.
     *
     */
    public static DataType fromStringToEnum(String dataTypeStr) {
        switch (dataTypeStr.toLowerCase()) {
            case "walk":
                return WALK;
            case "run":
                return RUN;
            case "hike":
                return HIKE;
            case "climb":
                return CLIMB;
            case "bike":
                return BIKE;
            case "swim":
                return SWIM;
            default:
                return WATER_SPORTS;
        }
    }


    /**
     * This function returns the string that matches the input enumerator value.
     * @param dataType the enumerator to be replaced with a string.
     * @return the string that matches that enumerator.
     */
    public static String fromEnumToString(DataType dataType) {
        switch (dataType) {
            case WALK:
                return "Walk";
            case  RUN:
                return "Run";
            case HIKE:
                return "Hike";
            case  CLIMB:
                return "Climb";
            case BIKE:
                return "Bike";
            case SWIM:
                return "Swim";
            default:
                return "Water Sport";
        }
    }


    /**
     * This function returns an array of the different data types that are available.
     * This is so that they can be used for GUI choice boxes.
     * @return An array of the different data types that can be used for GUI choice boxes.
     */
    public static String[] dataTypeForChoicheBoxes() {
        String[] dataTypesStr = {"Walk", "Run", "Hike", "Climb", "Bike", "Swim", "Water Sport"};
        return dataTypesStr;
    }

}
