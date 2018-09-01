package seng202.group8.user;

public class BMI {

    private BMIType BMICategory;
    private Double BMIValue;

    /**
     * Construct a new BMI object to hold the category and value of the BMI
     * @param BMIValue the BMI value to be stored (kg/m**2)
     */
    public BMI(Double BMIValue) {
        this.BMIValue = BMIValue;
        this.BMICategory = BMIType.parseBMI(BMIValue);
    }

    /**
     * @return the category the BMI object belongs to according to its value
     */
    public BMIType getBMICategory() {
        return BMICategory;
    }

    /**
     * @return the Double value of the BMI in kg/m**2
     */
    public Double getBMIValue() {
        return BMIValue;
    }

    /**
     * Sets the BMI to a new double value in kg/m**2, and updates the category
     * @param newBMIValue the new Double of the BMI value
     */
    public void setBMI(Double newBMIValue) {
        BMIValue = newBMIValue;
        BMICategory = BMIType.parseBMI(BMIValue);
    }
}
