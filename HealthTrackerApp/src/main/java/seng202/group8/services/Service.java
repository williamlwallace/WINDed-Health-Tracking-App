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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCurrentlyInUse() {
        return currentlyInUse;
    }

    public void setCurrentlyInUse(boolean currentlyInUse) {
        this.currentlyInUse = currentlyInUse;
    }
}
