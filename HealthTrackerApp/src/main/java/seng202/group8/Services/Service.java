package seng202.group8.Services;

import seng202.group8.User.User;

public class Service {

    private User user;
    private boolean currentlyInUse;

    public Service(User user) {
        this.user = user;
        this.currentlyInUse = false;
    }



}
