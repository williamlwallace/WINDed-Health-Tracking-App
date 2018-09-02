package seng202.group8.activity_collection;

import seng202.group8.data_entries.Data;

import java.util.ArrayList;
import java.util.Date;

public class ActivityList {


    private String title;
    private Date creationDate;
    private ArrayList<Data> activityList;

    /**
     * Structure containing Activity object, offers storage, sort and retrieval of this objects.
     */
    public ActivityList(String title) {
        this.title = title;
        creationDate = new Date();
        activityList = new ArrayList<Data>();
    }

    /**
     * @param index index of Activity object to retrieve
     * @return Activity object at specified index
     */
    public Data getActivity(int index) {
        return activityList.get(index);
    }

    /**
     * @param activity a new Activity object to add to the activityList attribute
     * @return a boolean value
     */
    public boolean insertActivity(Data activity) {
        int initialSize = activityList.size();
        for (int i = 0; i < activityList.size(); i++) {
            if (activity.getCreationDate().before(activityList.get(i).getCreationDate())) {
                activityList.add(i, activity);
                return true;
            }
        }
        activityList.add(activity);
        int finalSize = activityList.size();

        return (finalSize == (initialSize + 1));
    }


    /**
     *
     * @return the title attribute
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param newTitle a String object
     */
    public void setTitle(String newTitle) {
        if (newTitle.length() < 25 && newTitle.length() > 5) {
            this.title = newTitle;
        } else {
            // to limit in related View class, no point in adding an error handler here
            System.out.println("Not acceptable title size");
        }
    }

    /**
     * @return the creationDate attribute
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param newCreationDate a Date greater than the current date (if any present)
     */
    public void setCreationDate(Date newCreationDate) {
        Date currentDate = new Date();
        if (newCreationDate.before(currentDate)) {
            this.creationDate = newCreationDate;
        } else {
            System.out.println("Invalid date");
        }
    }

    /**
     * @return the ArrayList<Activity> attribute
     */
    public ArrayList<Data> getActivityList() {
        return activityList;
    }

    /**
     * @param activityList an ArrayList<Activity> object
     */
    public void setActivityList(ArrayList<Data> activityList) {
        this.activityList = activityList;
    }

}
