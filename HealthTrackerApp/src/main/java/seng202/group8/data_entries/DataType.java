package seng202.group8.data_entries;

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

    public static String[] dataTypeForChoicheBoxes() {
        String[] dataTypesStr = {"Walk", "Run", "Hike", "Climb", "Bike", "Swim", "Water Sport"};
        return dataTypesStr;
    }

}
