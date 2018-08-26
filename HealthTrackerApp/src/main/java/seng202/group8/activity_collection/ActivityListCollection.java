package seng202.group8.activity_collection;

import java.util.ArrayList;
import java.util.Date;

public class ActivityListCollection {

    private String title;
    private ArrayList<ActivityList> activityListCollection;

    /**
     * Initialises the activityListCollection attribute to an empty ArrayList<ActivityList> object
     */
    public ActivityListCollection(String title) {
        this.title = title;
        activityListCollection = new ArrayList<ActivityList>();
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
                return true;
            }
        }
        activityListCollection.add(activityList);
        int afterAddingCollectionSize = activityListCollection.size();
        return beforeAddingCollectionSize == (afterAddingCollectionSize - 1);
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

}
