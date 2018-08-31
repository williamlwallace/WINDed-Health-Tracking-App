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

//    private String activityType;
//    private DataType superDataType;
//    private DataType subDataType;
//    private DataType superData;


//    DataType(final String activityType) {
//        this.activityType = activityType;
//        this.subDataType = getSubDataType();
//        this.superDataType = getSuperDataType(this.subDataType);
////        this.type = values();
//
//    }
//
//    public DataType getSubDataType() {
//        return parseSubDataType(this.activityType);
//    }
//
//    public void setSubDataType(String activity) {
//        this.subDataType = parseSubDataType(activity);
//    }
//
//    public String getActivityType() {
//        return this.activityType;
//    }
//    public void setActivityType(String activity) {
//        this.activityType = activity;
//    }
//
//    /**
//     *
//     * @param activityType is the activity type given by the parser as a string
//     * @return activityDataType returns the enum type of the activity type that is passed in. If the passed in activity
//     * type does not fit the existing ones, it currently returns null. This may be changed to instead add the
//     * activity type to the enum, or return general. Tests are currently being written, parts of the function may be
//     * unnecessary as I have no experience with string enums prior to this one. Will try remove unnecessary parts after
//     * testing.
//     */
//    public static DataType parseSubDataType(String activityType) {
//        DataType type = null;
//        for (DataType enumType: type.values()) {
//            if (activityType.equals("walk") && enumType == WALK) {
//                type.subDataType = enumType;
//            } else if (activityType.equals("run") && enumType == RUN) {
//                type.subDataType = enumType;
//            } else if (activityType.equals("hike") && enumType == HIKE) {
//                type.subDataType = enumType;
//            } else if (activityType.equals("climb") && enumType == CLIMB) {
//                type.subDataType = enumType;
//            } else if (activityType.equals("water sports") && enumType == WATER_SPORTS) {
//                type.subDataType = enumType;
//            } else if (activityType.equals("swim") && enumType == SWIM) {
//                type.subDataType = enumType;
//            } else {
//                type.subDataType = enumType;
//            }
////            System.out.println(activityType);
//            type = enumType;
//        }
////        if (type == null) {
////            type = GENERAL;
////        }
//        return type;
//    }
//
//    public static DataType getSuperDataType(DataType subType) {
//
////        DataType superData = null;
////        superData.activityType = subType.getActivityType();
//        switch (subType.subDataType) {
//            case WALK:
////                superData.subDataType = WALK;
//                subType.superDataType = NOT_ASSISTED_SPORTS_DATA;
//                return subType;
//
//            case RUN:
////                superData.subDataType = WALK;
//                subType.superDataType = NOT_ASSISTED_SPORTS_DATA;
//                return subType;
//
//            case HIKE:
////                superData.subDataType = HIKE;
//                subType.superDataType = NOT_ASSISTED_SPORTS_DATA;
//                return subType;
//
//            case CLIMB:
////                superData.subDataType = CLIMB;
//                subType.superDataType = NOT_ASSISTED_SPORTS_DATA;
//                return subType;
//
//            case BIKE:
////                superData.subDataType = BIKE;
//                subType.superDataType = ASSISTED_SPORTS_DATA;
//                return subType;
//
//            case WATER_SPORTS:
////                superData.subDataType = WATER_SPORTS;
//                subType.superDataType = ASSISTED_SPORTS_DATA;
//                return subType;
//
//            case SWIM:
////                superData.subDataType = SWIM;
//                subType.superDataType = ASSISTED_SPORTS_DATA;
//                return subType;
//
//            default:
//                //String defaultDataType = "general";
//                subType.superDataType = NOT_ASSISTED_SPORTS_DATA;
//                return subType;
//
//        }
//    }
//    @Override
//    public String toString() {
//        return activityType;
//    }
}
