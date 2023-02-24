package Garforth;

/**
 * Jake Garforth
 * 2/8/23
 * CMSC 350 6381
 * This is the InvalidTreeSyntaxException. It is a custom exception that is raised whenever the user
 * attempts to make a tree that is invalid.
 */

public class InvalidTreeSyntaxException extends Exception{
    public InvalidTreeSyntaxException(String exceptionReason)
    {
        super(exceptionReason);
    }
}
