package seng202.group8.activity_collection;

import java.util.ArrayList;

public class ActivityListCollection {

    private ArrayList<ActivityList> activityListCollection;

    /**
     * Initialises the activityListCollection attribute to an empty ArrayList<ActivityList> object
     */
    public ActivityListCollection() {
        activityListCollection = new ArrayList<ActivityList>();
    }



    /**
     * @return the activityListCollection attribute
     */
    public ArrayList<ActivityList> getActivityListCollection() {
        return activityListCollection;
    }

    /**
     * @param activityListCollection a ArrayList<ActivityList> object
     */
    public void setActivityListCollection(ArrayList<ActivityList> activityListCollection) {
        this.activityListCollection = activityListCollection;
    }

}
