package seng202.group8.User.User_Stats;

import seng202.group8.User.BMIType;

public class BMITypeRecord extends Records {

    /**
     * @return the records bmi
     */
    public BMIType getBmi() {
        return bmi;
    }

    /**
     * @param bmi sets the records bmi value
     */
    public void setBmi(BMIType bmi) {
        this.bmi = bmi;
    }

    /**
     * Initial Variables
     */
    public BMIType bmi;

    /**
     * creates the object bmirecord and calls super classes function for date
     * @param bmi the input for creating the bmi record
     */
    public BMITypeRecord(BMIType bmi) {
        super.createDate();
        setBmi(bmi);
    }
}
