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

    public WeightRecord(Double weight) {
        setWeight(weight);
        super.createDate();
    }

}
