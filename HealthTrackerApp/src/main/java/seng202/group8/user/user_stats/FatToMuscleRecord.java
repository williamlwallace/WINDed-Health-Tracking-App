package seng202.group8.user.user_stats;

public class FatToMuscleRecord extends Records {

    /**
     * @return the users fat to muscle value as a double
     */
    public Double getFatToMuscle() {
        return fatToMuscle;
    }

    /**
     * @param fatToMuscle the users double entry for fat to muscle and sets the value in the record
     */
    public void setFatToMuscle(Double fatToMuscle) {
        this.fatToMuscle = fatToMuscle;
    }

    /**
     * Initial Variables
     */
    public Double fatToMuscle;

    /**
     * creates the fat to muscle record and uses the super methods to create the date
     * @param fatToMuscle a double for the fat to muscle entry
     */
    public FatToMuscleRecord(Double fatToMuscle) {
        super.createDate();
        setFatToMuscle(fatToMuscle);
    }
}
