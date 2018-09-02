package seng202.group8.user;

public enum BMIType {
    OBESE(30.0), OVERWEIGHT(25.0), NORMAL(18.5), LIGHT(16.0), ANOREXIC(1.0);

    private final Double BMILowerLimit;


    BMIType(final Double BMILimit) {
        this.BMILowerLimit = BMILimit;
    }

    /**
     * @param userBMI the double value of the BMI to put into a BMI category (BMI = weight / height**2)
     * @return the BMIType value showing the category that the user with given BMI falls in. If the BMI is not in any categories
     * range then null is returned.
     */
    public static BMIType parseBMI(Double userBMI) {
        BMIType userBMIType = null;
        for (BMIType type: values()) {
            if (userBMI >= type.BMILowerLimit) {
                userBMIType = type;
                break;
            }
        }
        return userBMIType;
    }

}


