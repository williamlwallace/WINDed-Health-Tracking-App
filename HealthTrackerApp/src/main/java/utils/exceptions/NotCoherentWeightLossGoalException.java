package utils.exceptions;

/**
 * Custom error to catch users inserting a value in the weight goal that is lower or equal to their current weight.
 */
public class NotCoherentWeightLossGoalException extends Exception {

    public NotCoherentWeightLossGoalException() {
        System.out.println("Cannot set a goal with no space for improvement, goals cannot be self-achieved.");
    }

}
