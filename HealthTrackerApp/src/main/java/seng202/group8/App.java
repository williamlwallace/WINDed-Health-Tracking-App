package seng202.group8;

import seng202.group8.services.goals_service.GoalsService;
import seng202.group8.activity_collection.Activity;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;

/**
 * Hello world!
 * Jack's Tested
 */
public class App 
{
    public int sam = 1000;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ActivityListCollection activityListCollection = new ActivityListCollection("Ciao");
        GoalsService goalsService = new GoalsService(activityListCollection);
        Activity activity1 = new Activity("Ciao");
        Activity activity2 = new Activity("Riciao");

        ActivityList activityList = new ActivityList("CiaoCiaoCiao");
        ActivityList activityList1 = new ActivityList("SuperCiao");
        activityListCollection.insertActivityList(activityList);
        activityListCollection.insertActivityList(activityList1);
        activityListCollection.insertActivityInGivenList(0, activity1);
        activityListCollection.insertActivityInGivenList(1, activity2);
    }
}
