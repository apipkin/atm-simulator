/* File: InsufficientFunds.java
 * Author: Anthony Pipkin
 * Date: 2016-10-06
 * Instructor: Stephen Grady
 * Class: CMIS 242
 * Project: Project 2 - ATM machine
 * Purpose: Writing a program that implements an ATM machine
 */

/**
 * A custom error to throw when the account has insufficient funds to complete a transaction.
 * @author Anthony Pipkin
 * @version 0.1.0
 */
public class InsufficientFunds extends Exception {
    private static final String MESSAGE = "Insufficient funds to complete the transaction.";

    public InsufficientFunds() {
        super(MESSAGE);
    }
}