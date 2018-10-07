package seng202.group8.services;

import seng202.group8.user.User;

/**
 * @author lfa69
 * Parent class for all three GoalsService, HealthService and StatisticsService.
 *
 */
public class Service {


    private User user;
    private boolean currentlyInUse;


    public Service(User user) {
        this.user = user;
        this.currentlyInUse = false;
    }


    /**
     *
     * @return the user property
     */
    public User getUser() {
        return user;
    }


    /**
     *
     * @param user a new User object for the user porperty
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     *
     * @return the currentlyInUse property
     */
    public boolean isCurrentlyInUse() {
        return currentlyInUse;
    }


    /**
     *
     * @param currentlyInUse a new boolean value for the currentlyInUse property
     */
    public void setCurrentlyInUse(boolean currentlyInUse) {
        this.currentlyInUse = currentlyInUse;
    }

}
