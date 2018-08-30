package seng202.group8.user.user_stats;

import seng202.group8.user.StressLevelType;

public class StressLevelRecord extends Records {

    /**
     * @return the enum value for the stress level type for this record
     */
    public StressLevelType getStress() {
        return stress;
    }

    /**
     * @param stress sets the stress level for the record with the stressLevelType enum
     */
    public void setStress(StressLevelType stress) {
        this.stress = stress;
    }

    /**
     * Initial Variables
     */
    public StressLevelType stress;

    /**
     * Creates the stress level record and calls the super class functions to create a date
     * @param stress an enum value used to set the stress level type
     */
    public StressLevelRecord(StressLevelType stress) {
        super.createDate();
        setStress(stress);
    }

}
