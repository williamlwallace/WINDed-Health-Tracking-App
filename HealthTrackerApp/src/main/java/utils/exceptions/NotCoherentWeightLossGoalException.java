package utils.exceptions;

public class NotCoherentWeightLossGoalException extends Exception {

    public NotCoherentWeightLossGoalException() {
        System.out.println("Cannot set a goal with no space for improvement, goals cannot be self-achieved.");
    }

}
