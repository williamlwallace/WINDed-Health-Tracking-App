package seng202.group8.parser;

/**
 * @author sgv15
 * A error to throw when the file isnt found
 */
public class FileNotFoundError extends Exception
{
    public FileNotFoundError() {}


    public FileNotFoundError(String message)
    {
        super(message);
    }

}
