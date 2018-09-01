package seng202.group8.user.user_stats;

import seng202.group8.user.BMI;

public class BMITypeRecord extends Records {

    /**
     * @return the records bmi
     */
    public BMI getBmi() {
        return bmi;
    }

    /**
     * @param bmi sets the records bmi value
     */
    public void setBmi(BMI bmi) {
        this.bmi = bmi;
    }

    /**
     * Initial Variables
     */
    public BMI bmi;

    /**
     * creates the object bmirecord and calls super classes function for date
     * @param bmi the input for creating the bmi record
     */
    public BMITypeRecord(BMI bmi) {
        super.createDate();
        setBmi(bmi);
    }
}
