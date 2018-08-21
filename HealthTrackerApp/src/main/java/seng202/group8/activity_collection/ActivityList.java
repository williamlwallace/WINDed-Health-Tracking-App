package seng202.group8.activity_collection;

import java.util.ArrayList;
import java.util.Date;

public class ActivityList {


    private String title;
    private Date creationDate;
    private ArrayList<Activity> activityList;

    /**
     * Structure containing Activity object, offers storage, sort and retrieval of this objects.
     */
    public ActivityList(String title) {
        activityList= new ArrayList<Activity>();
        this.title = title;
    }


    /**
     * @param index index of Activity object to retrieve
     * @return Activity object at specified index
     */
    public Activity getActivity(int index) {
        return activityList.get(index);
    }

    /**
     * @param activity a new Activity object to add to the activityList attribute
     * @return a boolean value
     */
    public boolean insertActivity(Activity activity) {
        int initialSize = activityList.size();
        for (int i = 0; i < activityList.size(); i++) {
            if (activity.mockVal < activityList.get(i).mockVal) {
                activityList.add(i, activity);
            }
        }
        activityList.add(activity);
        int finalSize = activityList.size();

        return (finalSize == (initialSize + 1));
    }

    /**
     * @return the ArrayList<Activity> attribute
     */
    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    /**
     * @param activityList an ArrayList<Activity> object
     */
    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }

}
