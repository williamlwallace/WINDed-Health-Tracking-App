package seng202.group8.parser;

/**
 * @author sgv15
 * A error to throw when an activity can't be matched to a type.
 */
public class noTypeError extends Exception
{
    public noTypeError() {}

    public noTypeError(String message)
    {
        super(message);
    }

}