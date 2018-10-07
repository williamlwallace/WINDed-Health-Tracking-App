package seng202.group8.activity_collection;

import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author lfa69
 * The container for ActvityList objects
 */
public class ActivityListCollection {

    private String title;
    private ArrayList<ActivityList> activityListCollection;
    private ArrayList<ActivityListCollectionObserver> observers;

    /**
     * Initialises the activityListCollection attribute to an empty ArrayList<ActivityList> object
     */
    public ActivityListCollection(String title) {
        this.title = title;
        activityListCollection = new ArrayList<ActivityList>();
        this.observers = new ArrayList<ActivityListCollectionObserver>();
    }

    /**
     *
     * @param activityList, a new ActivityList object to add to the actvityListCollection parameter
     * @return a integer index of the location in the list that the activity list was added.
     */
    public int insertActivityList(ActivityList activityList) {
        int beforeAddingCollectionSize = activityListCollection.size();
        Date activityListDate = activityList.getCreationDate();
        int i = 0;
        for (; i < activityListCollection.size(); i++) {
            Date selectedActivityListDate = activityListCollection.get(i).getCreationDate();
            if (activityListDate.before(selectedActivityListDate)) {
                activityListCollection.add(i, activityList);
                notifyAllObservers();
                return i;
            }
        }
        activityListCollection.add(activityList);
        //int afterAddingCollectionSize = activityListCollection.size();
        notifyAllObservers();
        return i;
    }

    /**
     * Writen by Sam. Checks for a duplicate entry of a given title
     * @param title
     * @return a integer index of the location of the duplicate list
     */
    public int checkDuplicate(String title) {
        int toReturn = -1;
        for (int i = 0; i < activityListCollection.size(); i++) {
            String actTitle = activityListCollection.get(i).getTitle();
            if (title.toLowerCase().equals(actTitle.toLowerCase())) {
                toReturn = i;
            }
        }
        return toReturn;
    }

    public boolean deleteActivityList(int activityListIndex) {
        try {
            activityListCollection.remove(activityListIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Trying to index a value that does not exist.");
            return false;
        }
        return true;
    }


    /**
     *
     * @param index
     * @param activity the Data value to insert into the ActivityList object at the given index
     */
    public void insertActivityInGivenList(int index, Data activity) {
        try {
            ActivityList activityList = activityListCollection.get(index);
            activityList.insertActivity(activity);
            notifyAllObservers();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Trying to index a value that does not exist.");
        }
    }

    /**
     *
     * @param activityListIndex
     * @param dataValueIndex
     * @return a boolean value representing if the delete action has been accomplished or not
     */
    public boolean deleteActivityInGivenList(int activityListIndex, int dataValueIndex) {
        try {
            ActivityList activityList = activityListCollection.get(activityListIndex);
            activityList.getActivityList().remove(dataValueIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Trying to index a value that does not exist.");
            return false;
        }
        return true;
    }

    /**
     *
     * @param dataType
     * @param startDate
     * @return an ArrayList<Data> object where the Data objects are all of the same data type
     * and come from activityListCollection property
     */
    public ArrayList<Data> retrieveSameTypeActivities(DataType dataType, Date startDate) {

        ArrayList<Data> sameTypeData = new ArrayList<Data>();
        for (ActivityList activityList : activityListCollection) {
            for (Data data : activityList.getActivityList()) {
                if (!data.getCreationDate().before(startDate) && data.getDataType() == dataType) {
                    sameTypeData.add(data);
                }
            }
        }
        return sameTypeData;
    }

    /**
     *
     * @param startDate
     * @return an ArrayList<Data> object where the Data objects are all
     * the Data values in activityListCollection that have been performed before or during the given date
     */
    public ArrayList<Data> retrieveActivititesBeforeDate(Date startDate) {

        ArrayList<Data> activitiesAfterDate = new ArrayList<Data>();
        for (ActivityList activityList : activityListCollection) {
            for (Data data : activityList.getActivityList()) {
                if (!data.getCreationDate().before(startDate)) {
                    activitiesAfterDate.add(data);
                }
            }
        }
        return activitiesAfterDate;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return the list of Data values in activityListCollection property between the given date parameters
     */
    public ArrayList<Data> retrieveActivititesBtwDates(Date startDate, Date endDate) {

        ArrayList<Data> activitiesAfterDate = new ArrayList<Data>();
        for (ActivityList activityList : activityListCollection) {
            for (Data data : activityList.getActivityList()) {
                if (!(data.getCreationDate().before(startDate)) && !(data.getCreationDate().after(endDate))) {
                    activitiesAfterDate.add(data);
                }
            }
        }
        return activitiesAfterDate;
    }

    /**
     *
     * @return the Data object in activityListCollection that has been performed the latest
     */
    public Data getMostCurrentActivity() {
        Data mostCurrentData =  null;
        for (ActivityList activityList : activityListCollection) {
            for (Data data : activityList.getActivityList()) {
                if (mostCurrentData == null) {
                    mostCurrentData = data;
                } else {
                    LocalDateTime dateMostCurrentData = mostCurrentData.getAllDateTimes().get(0);
                    LocalDateTime dateData = data.getAllDateTimes().get(0);
                    if (dateData.isAfter(dateMostCurrentData)) {
                        mostCurrentData = data;
                    }
                }
            }
        }
        return mostCurrentData;
    }

    public ArrayList<Data> getAllData() {

        ArrayList<Data> activities = new ArrayList<Data>();
        for (ActivityList activityList : activityListCollection) {
            for (Data data : activityList.getActivityList()) {
                activities.add(data);
            }
        }
        return activities;
    }

    /**
     * Part of the observer pattern, notifies all the observers of happened changes.
     */
    public void notifyAllObservers() {
        for (ActivityListCollectionObserver observer : observers) {
            observer.update();
        }
    }


    public void attach(ActivityListCollectionObserver observer) {
        observers.add(observer);
    }

    /**
     *
     * @return the String parameter title, the title of the ActivityListCollection object
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param newTitle, a new String value for the ActivityListCollection object
     */
    public void setTitle(String newTitle) {
        if (newTitle.length() < 50 && newTitle.length() > 5) {
            this.title = newTitle;
        } else {
            // to limit in related View class, no point in adding an error handler here
            System.out.println("Not acceptable title size");
        }
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
