package seng202.group8.activity_collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ActivityListCollectionTest {

    private ActivityListCollection activityListCollection;
    private ActivityList activityListJog;
    private ActivityList activityListFastWalks;
    private Activity jog;
    private Activity fastWalk;

    @Before
    public void setUp() throws Exception {
        activityListCollection = new ActivityListCollection("Great Collection");
        activityListJog = new ActivityList("Jog with friends");
        activityListFastWalks = new ActivityList("Fast Walks with friends");
        activityListJog.setTitle("Intense runs");
        jog = new Activity("Morning jog");
        fastWalk = new Activity("Fast Walk");

        activityListFastWalks.insertActivity(fastWalk);
        activityListJog.insertActivity(jog);
    }

    @After
    public void tearDown() throws Exception {
        activityListCollection = null;
        activityListJog = null;
        activityListFastWalks = null;
        jog = null;
        fastWalk = null;
    }

    @Test
    public void setTitleAcceptedBehaviour() {
        String newTitle = "I am long enough";
        activityListCollection.setTitle(newTitle);
        assertEquals(activityListCollection.getTitle(), "I am long enough");
    }

    @Test
    public void setTitleRejectedBehaviour() {
        String newTitle = "No";
        activityListCollection.setTitle(newTitle);
        assertEquals(activityListCollection.getTitle(), "I am long enough");
    }

    @Test
    public void insertActivityListTest() {
        Date dateInFuture = activityListFastWalks.getCreationDate();
        activityListJog.setCreationDate(new Date(dateInFuture.getTime() * 2));
        activityListCollection.insertActivityList(activityListJog);
        activityListCollection.insertActivityList(activityListFastWalks);

        String titleZerothPosActivityList = activityListCollection.getActivityListCollection().get(0).getTitle();
        assertEquals(titleZerothPosActivityList, "Fast Walks with friends");
    }

}