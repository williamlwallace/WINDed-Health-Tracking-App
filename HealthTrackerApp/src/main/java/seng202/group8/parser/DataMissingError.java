package seng202.group8.parser;

public class DataMissingError extends Exception
{
    public DataMissingError() {}

    public DataMissingError(String message)
    {
        super(message);
    }
}
