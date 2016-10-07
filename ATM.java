/* File: ATM.java
 * Author: Anthony Pipkin
 * Date: 2016-10-06
 * Instructor: Stephen Grady
 * Class: CMIS 242
 * Project: Project 2 - ATM machine
 * Purpose: Writing a program that implements an ATM machine
 */

/**
 * Creates the ATM Simulator app and displays it
 * @author Anthony Pipkin
 * @version 0.1.0
 */
public class ATM {
    public static void main(String[] arg) {
        ATMSimulator app = new ATMSimulator("ATM Machine");
        app.display();
    }
}