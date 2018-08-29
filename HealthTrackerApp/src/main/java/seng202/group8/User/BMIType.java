package seng202.group8.User;

public enum BMIType {
    OBESE(30), OVERWEIGHT(25), NORMAL(18.5), LIGHT(16), ANOREXIC(1);

    private final double BMILowerLimit;

    BMIType(final double BMILimit) {
        this.BMILowerLimit = BMILimit;
    }

    /**
     * @param userBMI the double value of the BMI to put into a BMI category (BMI = weight / height**2)
     * @return the BMIType value showing the category that the User with given BMI falls in. If the BMI is not in any categories
     * range then null is returned.
     */
    public static BMIType parseBMI(double userBMI) {
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


