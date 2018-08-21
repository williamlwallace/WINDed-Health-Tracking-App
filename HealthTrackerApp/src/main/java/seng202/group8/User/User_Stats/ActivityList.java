package seng202.group8.User.User_Stats;
import java.util.ArrayList;

public class ActivityList {
    public String title;
    public ArrayList ActivityList = new ArrayList();

    public ArrayList getActivityList() {
        return ActivityList;
    }

    public void addActivity(ArrayList activity) {
        ActivityList.add(activity);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
