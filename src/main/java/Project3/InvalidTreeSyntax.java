/**********************************************************************************************************************
 *
 * Name: Jacob Jennings
 * Date: February 25, 2023
 * Class: CMSC 350
 * Project: Project 2
 * Professor: Dr. Romerl Elizes
 *
 * Class Description - Main creates the GUI for the Polynomial application. It collects the input from users and
 * displays the results of the Polynomial logic such as syntax, Strong Order, and Weak Order.
 *
 * The logic for computing the polynomial weak order is also included.
 *
 *********************************************************************************************************************/
package Project3;

public class InvalidTreeSyntax extends RuntimeException {
    public InvalidTreeSyntax(String message) { super(message); }
}
