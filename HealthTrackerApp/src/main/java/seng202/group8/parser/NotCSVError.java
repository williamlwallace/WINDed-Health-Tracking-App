package seng202.group8.parser;

public class NotCSVError extends Exception
{
    public NotCSVError() {}

    public NotCSVError(String message)
    {
        super(message);
    }
}
