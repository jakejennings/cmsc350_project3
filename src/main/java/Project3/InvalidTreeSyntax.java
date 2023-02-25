/**********************************************************************************************************************
 *
 * Name: Jacob Jennings
 * Date: February 20, 2023
 * Class: CMSC 350
 * Project: Project 3
 * Professor: Dr. Romerl Elizes
 *
 * Class Description - InvalidTreeSyntax catches errors with tree syntax from user input.
 *
 *
 *********************************************************************************************************************/
package Project3;

public class InvalidTreeSyntax extends RuntimeException {
    public InvalidTreeSyntax(String message) { super(message); }
}
