package seng202.group8.User.User_Stats;

public class WeightRecord extends Records{
    /**
     * @return the weight held by the record
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * @param weight a double which sets the weight record's weight value
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Initial Variables
     */
    public Double weight;

    /**
     * Adds a new weight record object and calls super classes functions for adding date and adding to record list
     * @param weight the weight value as a double for the users weight entry
     */
    public WeightRecord(Double weight) {
        setWeight(weight);
        super.createDate();
    }

}
