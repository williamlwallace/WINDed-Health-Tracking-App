package seng202.group8.parser;

public class FileNotFoundError extends Exception
{
    public FileNotFoundError() {}

    public FileNotFoundError(String message)
    {
        super(message);
    }
}
