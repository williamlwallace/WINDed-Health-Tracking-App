package seng202.group8.parser;

/**
 * @author sgv15
 * A error to throw when there is missing data.
 */
public class DataMissingError extends Exception
{
    public DataMissingError() {}


    public DataMissingError(String message)
    {
        super(message);
    }

}
