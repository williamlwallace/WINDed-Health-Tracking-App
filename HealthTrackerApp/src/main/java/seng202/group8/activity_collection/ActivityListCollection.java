package seng202.group8.activity_collection;

import java.util.ArrayList;
import java.util.Date;

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
     * @return a boolean value representing the success or failure of the addition of the parameter to the collection.
     */
    public boolean insertActivityList(ActivityList activityList) {
        int beforeAddingCollectionSize = activityListCollection.size();
        Date activityListDate = activityList.getCreationDate();

        for (int i = 0; i < activityListCollection.size(); i++) {
            Date selectedActivityListDate = activityListCollection.get(i).getCreationDate();
            if (activityListDate.before(selectedActivityListDate)) {
                activityListCollection.add(i, activityList);
                notifyAllObservers();
                return true;
            }
        }
        activityListCollection.add(activityList);
        int afterAddingCollectionSize = activityListCollection.size();
        notifyAllObservers();
        return beforeAddingCollectionSize == (afterAddingCollectionSize - 1);
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


    public void insertActivityInGivenList(int index, Activity activity) {
        try {
            ActivityList activityList = activityListCollection.get(index);
            activityList.insertActivity(activity);
            notifyAllObservers();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Trying to index a value that does not exist.");
        }
    }

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
        if (newTitle.length() < 25 && newTitle.length() > 5) {
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

    /* Can use or test once the Data class and subclasses will be ready
    public ArrayList<Activity> retrieveSameTypeActivities(DataType dataType, Date minDate) {
        ArrayList<Activity> retrievedData = new ArrayList<Activity>();
        for (ActivityList activityList : activityListCollection) {
            for (Activity data : activityList.getActivityList()) {
                if (!(data.getCreationDate().before(minDate)) {
                    retrievedData.add(data);
                }
            }
        }

        return retrievedData;
    }
    */

}
