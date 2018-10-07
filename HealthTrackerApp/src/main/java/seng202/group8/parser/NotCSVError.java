package seng202.group8.parser;

/**
 * @author sgv15
 * A error to throw when the file isnt a .csv file
 */
public class NotCSVError extends Exception
{
    public NotCSVError() {}

    public NotCSVError(String message)
    {
        super(message);
    }
}
