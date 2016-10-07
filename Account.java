/* File: Account.java
 * Author: Anthony Pipkin
 * Date: 2016-10-06
 * Instructor: Stephen Grady
 * Class: CMIS 242
 * Project: Project 2 - ATM machine
 * Purpose: Writing a program that implements an ATM machine
 */

import java.text.NumberFormat;
import java.util.Locale;

/**
 * An account to be named and whose balance can be manipulated and received.
 * @author Anthony Pipkin
 * @version 0.1.0
 */
public class Account {
    private double balance;
    private String name;

    private static int withdrawCount = 0;
    private static final double SERVICE_CHARGE = 1.5;

    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    /**
     * Initializes the account with a name and a zero balance.
     * @param name Account name
     */
    public Account(String name) {
        this.balance = 0.0;
        this.name = name;
    }

    /**
     * Initializes the account with a name and a given amount.
     * @param name Account name
     * @param amount Initial acount balance
     */
    public Account(String name, double amount) {
        this.balance = amount;
        this.name = name;
    }

    /**
     * Subracts the given amount from the account balance
     * @param amount Amount to withdraw
     * @return New balance
     * @throws InsufficientFunds
     */
    public double withdraw(double amount) throws InsufficientFunds {
        if (amount > balance) {
            throw new InsufficientFunds();
        }

        if (withdrawCount > 4) {
            if (amount + SERVICE_CHARGE > balance) { 
                throw new InsufficientFunds();
            }

            balance -= SERVICE_CHARGE;
        }
         
        balance -= amount;
        withdrawCount++;
        
        return balance;
    }

    /**
     * Adds the given amount to the current balance
     * @param amount Amount to deposit
     * @return New balance
     */
    public double deposit(double amount) {
        balance = balance + amount;

        return balance;
    }

    /**
     * Withdraws the given amount from this account and deposits it into the provied account
     * @param account Accont into which the funds should be deposited
     * @param amount Ammount to transfer
     * @return New balance
     * @throws InsufficientFunds
     */
    public double transferTo(Account account, double amount) throws InsufficientFunds {
        withdraw(amount);
        account.deposit(amount);

        return balance;
    }

    /**
     * Name getter
     * @return name
     */
    public String getName () {
        return name;
    }

    /**
     * Balance getter
     * @return balance
     */
    public double getBalance(){ 
        return balance;
    }

    /**
     * Formats the current balance to locale of en-US
     * @return Formatted balance
     */
    public String getBalanceCurrency() {
        return currencyFormatter.format(balance);
    }

    /**
     * Returns the name of the account with the formatted balance
     * @return Account status
     */
    public String toString() {
        return name + " Balance: " + getBalanceCurrency();
    }
}