package seng202.group8.activity_collection;

import seng202.group8.activity_collection.ActivityList;

public abstract class ActivityListCollectionObserver {

    protected ActivityList activityList;
    public abstract void update();

}
