package seng202.group8.activity_collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.group8.data_entries.*;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ActivityListCollectionTest {

    private ActivityListCollection activityListCollection;
    private ActivityList activityListJog;
    private ActivityList activityListFastWalks;
    private RunData jog;
    private WalkData fastWalk;

    @Before
    public void setUp() throws Exception {
        User user = new User("Lorenzo", 22, 83.0, 183.0, Sex.MALE);
        activityListCollection = new ActivityListCollection("Great Collection");
        activityListJog = new ActivityList("Jog with friends");
        activityListFastWalks = new ActivityList("Fast Walks with friends");
        activityListJog.setTitle("Intense runs");

        ArrayList<LocalDateTime> localTimes = new ArrayList<LocalDateTime>();
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 10));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 15));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 25));

        ArrayList<Integer> heartRates = new ArrayList<Integer>();
        heartRates.add(100);
        heartRates.add(115);
        heartRates.add(120);

        ArrayList<CoordinateData> coordinateList = new ArrayList<CoordinateData>();
        coordinateList.add(new CoordinateData(30.26881985,-97.83246599,204.4));
        coordinateList.add(new CoordinateData(30.26868423,-97.83252265,202));
        coordinateList.add(new CoordinateData(30.26863712,-97.83267747,201.5));

        jog = new RunData("Morning jog", DataType.NOT_ASSISTED_SPORTS_DATA, localTimes, coordinateList, heartRates, user);
        fastWalk = new WalkData("Morning jog", DataType.NOT_ASSISTED_SPORTS_DATA, localTimes, coordinateList, heartRates, user);

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
        assertEquals(activityListCollection.getTitle(), "Great Collection");
    }

    @Test
    public void insertActivityListTest() {

        activityListJog.setCreationDate(new Date(1234));
        activityListFastWalks.setCreationDate(new Date(2345));

        activityListCollection.insertActivityList(activityListFastWalks);
        activityListCollection.insertActivityList(activityListJog);

        String titleZerothPosActivityList = activityListCollection.getActivityListCollection().get(0).getTitle();
        assertEquals(titleZerothPosActivityList, "Intense runs");

    }

    @Test
    public void deleteActivityListFailureTest() {
        activityListCollection.insertActivityList(activityListFastWalks);
        activityListCollection.insertActivityList(activityListJog);
        boolean failureInDeleting = activityListCollection.deleteActivityList(4);
        assertFalse(failureInDeleting);
    }

    @Test
    public void deleteActivityListSuccessTest() {
        activityListCollection.insertActivityList(activityListFastWalks);
        activityListCollection.insertActivityList(activityListJog);
        boolean successedInDeleting = activityListCollection.deleteActivityList(0);
        assertTrue(successedInDeleting);
    }

    @Test
    public void deleteDataInActivityListSuccessTest() {
        activityListCollection.insertActivityList(activityListFastWalks);
        activityListCollection.insertActivityList(activityListJog);
        boolean deleted = activityListCollection.deleteActivityInGivenList(0, 0);
        assertTrue(deleted);
    }

    @Test
    public void deleteDataInActivityListFailureTest() {
        activityListCollection.insertActivityList(activityListFastWalks);
        activityListCollection.insertActivityList(activityListJog);
        boolean deleted = activityListCollection.deleteActivityInGivenList(0, 7);
        assertFalse(deleted);
    }




}