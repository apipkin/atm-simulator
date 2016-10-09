/* File: ATMSimulator.java
 * Author: Anthony Pipkin
 * Date: 2016-10-06
 * Instructor: Stephen Grady
 * Class: CMIS 242
 * Project: Project 2 - ATM machine
 * Purpose: Writing a program that implements an ATM machine
 */

import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.text.NumberFormat;
import java.lang.NumberFormatException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

/**
 * An ATM Simulator that creates a GUI with buttons to add, remove, and transfer funds with two accounts.
 * @author Anthony Pipkin
 * @version 0.1.0
 */
public class ATMSimulator extends JFrame {

    static final int WIDTH = 300,
                     HEIGHT = 200;

    static final String NAME = "ATM Simulator",
                        ACCT_CHECKING = "checking",
                        ACCT_SAVINGS = "savings";

    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    private Account checkingAccount, savingsAccount;
    private Hashtable<String, Account> accounts = new Hashtable<String, Account>();

    private JPanel gridPanel = new JPanel();
    private GridBagLayout gridLayout = new GridBagLayout();
    private GridBagConstraints gridConstraints = new GridBagConstraints();
    private Insets gridPadding = new Insets(5, 3, 5, 3);

    private JRadioButton checking, savings;
    private String selectedAccount = ACCT_CHECKING;
    private ButtonGroup group = new ButtonGroup();

    private JTextField input;

    /**
     * Creates the application with a default name.
     */
    public ATMSimulator() {
        super(NAME);
        init();
    }

    /**
     * Creates the application with the given name
     * @param Application name
     */
    public ATMSimulator(String name) {
        super(name);
        init();
    }

    ///////////
    // PUBLIC
    ///////////

    /**
     * Shows the application window
     */
    public void display() {
        setVisible(true);
    }

    ///////////
    // INITIALIZE
    ///////////

    /**
     * Initializes the application accounts and renders the components in the proper places
     */
    private void init() {
        initAccounts();
        render();
    }

    /**
     * Creates the accounts necessary with the proper names
     */
    private void initAccounts() {
        checkingAccount = new Account("Checking");
        accounts.put(ACCT_CHECKING, checkingAccount);

        savingsAccount = new Account("Savings");
        accounts.put(ACCT_SAVINGS, savingsAccount);
    }

    ///////////
    // RENDER
    ///////////

    /**
     * Creates the window and adds components necessary
     */
    private void render() {
        setFrame(WIDTH, HEIGHT);
        renderLayout();
        renderPanel();
        renderButtons();
        renderRadios();
        renderInput();
    }

    /**
     * Adjusts the frame to the desired size, centers, and enables the app to close
     * @param width Window width
     * @param height Window height
     */
    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets up layout settings for UI flow
     */
    private void renderLayout() {
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
    }

    /**
     * Create a panel for components to be added to
     */
    private void renderPanel() {
        gridPanel.setLayout(gridLayout);
        add(gridPanel);
    }

    /**
     * Adds four action buttons to the panel
     */
    private void renderButtons() {
        addButton("Withdraw");
        addButton("Deposit");
        addButton("Transfer To");
        addButton("Balance");
    }

    /**
     * Creates a button with the given label, attaches the click handler and then adds it to the frame
     * @param label Button label
     */
    private void addButton(String label) {
        JButton btn = new JButton(label);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(e, label.toLowerCase());
            }
        });

        addCompontent(btn);
    }

    /**
     * Takes the given button type and calls the proper handler based on the button clicked
     * @param e Button click event
     * @param type Button label
     */
    private void handleButtonClick(ActionEvent e, String type) {
        switch (type) {
            case "withdraw":
                handleWithdraw();
                break;
            case "deposit":
                handleDeposit();
                break;
            case "transfer to":
                handleTransfer();
                break;
            case "balance":
                handleBalance();
                break;
        }
    }

    /**
     * Renders the two account radio button and sets the initial checkbox selected
     */
    private void renderRadios() {
        checking = addRadioButton("Checking");
        savings = addRadioButton( "Savings");

        switch (selectedAccount) {
            case ACCT_CHECKING:
                checking.setSelected(true);
                group.setSelected(checking.getModel(), true);
                break;
            case ACCT_SAVINGS:
                savings.setSelected(true);
                group.setSelected(savings.getModel(), true);
                break;
        }
    }

    /**
     * Creates a radio button and adds it to the panel. The radio button is fixed with a click handler
     * @param btn Radio button
     * @param label Radio button label
     * @return created button
     */
    private JRadioButton addRadioButton(String label) {
        JRadioButton btn = new JRadioButton(label);
        btn.setActionCommand(label.toLowerCase());
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRadioClick(e);
            }
        });

        group.add(btn);
        addCompontent(btn);

        return btn;
    }

    /**
     * Handles the radio button click event and calls the appropriate handler for the selected radio button
     * @param e Radio button click event
     */
    private void handleRadioClick(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "checking":
                handleCheckingClick();
                break;
            case "savings":
                handleSavingsClick();
                break;
        }
    }

    /**
     * Creates and adds the input field to the panel
     */
    private void renderInput() {
        input = new JTextField();
        addCompontent(input, 2);
    }

    /**
     * Helper method to begin the grid flow on a new row.
     */
    private void gridNewRow() {
        gridConstraints.gridx = 0;
        gridConstraints.gridy += 1;
    }

    /**
     * Helper method to increment the grid flow to the next column
     */
    private void gridNewCol() {
        gridConstraints.gridx += 1;
    }

    /**
     * Adds the component to the panel with a default grid width
     * @see addComponent(JComponent, int)
     * @param comp Component to be added
     */
    private void addCompontent(JComponent comp) {
        addCompontent(comp, 1);
    }

    /**
     * Adds a comopnent to the panel with a given grid width. Pads the grid cell and managages the location of the
     * next cell.
     * @param comp Component to be added
     * @param gridWidth Width of grid cell
     */
    private void addCompontent(JComponent comp, int gridWidth) {

        if (gridWidth > 1) {
            gridConstraints.gridwidth = gridWidth;
        }
        gridConstraints.insets = gridPadding;

        gridPanel.add(comp, gridConstraints);

        gridNewCol();

        if (gridConstraints.gridx > 1) {
            gridNewRow();
        }
    }

     ///////////
     // HANDLERS
     ///////////

    /**
     * Withdraws the amount in the input field from the selected account. Displays messages based on success or type
     * of failure
     */
    private void handleWithdraw() {
        try {
            String val = input.getText();
            double amount = Double.parseDouble(val);

            if (amount % 20 != 0.0) {
                displayError("Incorrect Value", "The withdrawal amount must be in multiples of $20.");
                return;
            }

            Account acct = accounts.get(selectedAccount);
            acct.withdraw(amount);

            displayBalance(acct, acct.getName() + " Withdraw: " + currencyFormatter.format(amount));
        }
        catch (NumberFormatException e) {
            displayIncorrectValue();
        }
        catch (InsufficientFunds e) {
            displayInsufficientFunds(e.getMessage());
        }
    }

    /**
     * Deposits the amount in the input field to the selected account. Displays messages based on success or type
     * of failure
     */
    private void handleDeposit() {
        try {
            String val = input.getText();
            double amount = Double.parseDouble(val);

            Account acct = accounts.get(selectedAccount);

            acct.deposit(amount);
            displayBalance(acct, acct.getName() + " Deposit: " + currencyFormatter.format(amount));
        }
        catch (NumberFormatException e) {
            displayIncorrectValue();
        }
    }

    /**
     * Transfers the amount in the input field from the unselected account to the selected account. Displays messages
     * based on the success or type of failure
     */
    private void handleTransfer(){
        try {
            String val = input.getText();
            double amount = Double.parseDouble(val);

            Account toAccount = accounts.get(selectedAccount);
            Account fromAccount = accounts.get((selectedAccount == ACCT_CHECKING) ? ACCT_SAVINGS : ACCT_CHECKING);

            fromAccount.transferTo(toAccount, amount);

            String msg = String.format("Transferred: %s\nFrom: %s\nTo: %s", currencyFormatter.format(amount), fromAccount.getName(), toAccount.getName());
            displayBalances(msg);
        }
        catch (NumberFormatException e) {
            displayIncorrectValue();
        }
        catch (InsufficientFunds e) {
            displayInsufficientFunds(e.getMessage());
        }
    }

    /**
     * Displays the balance of the selected account
     */
    private void handleBalance() {
        displayBalance(accounts.get(selectedAccount));
    }

    /**
     * Sets the checking account to the selected state
     */
    private void handleCheckingClick() {
        selectedAccount = ACCT_CHECKING;
    }

    /**
     * Sets the saving account to the selected state
     */
    private void handleSavingsClick() {
        selectedAccount = ACCT_SAVINGS;
    }

     ///////////
     // MESSAGES
     ///////////

    /**
     * Displays the balance of the provided account
     * @param acct Account of which to display balance
     */
    private void displayBalance(Account acct) {
        displayBalance(acct, "");
    }

    /**
     * Displays the balance of the provided account with a prepended message if provided
     * @param acct Account of which to display balance
     * @param msg Prepended message
     */
    private void displayBalance(Account acct, String msg) {

        if (msg != null && !msg.trim().isEmpty()) {
            msg += "\n\n------------------------------\n";
        }

        msg += acct;

        JOptionPane.showMessageDialog(this, msg, acct.getName() + " Balance", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays all account balances
     */
    private void displayBalances() {
        displayBalances("");
    }

    /**
     * Display all account balances with a prepended message if provided
     * @param msg Prepended message
     */
    private void displayBalances(String msg) {

        if (msg != null && !msg.trim().isEmpty()) {
            msg += "\n\n------------------------------";
        }

        Enumeration accts = accounts.keys();
        String key;

        while(accts.hasMoreElements()) {
            key = (String) accts.nextElement();
            msg += "\n" + accounts.get(key);
        }

        JOptionPane.showMessageDialog(this, msg, "Account Balances", JOptionPane.PLAIN_MESSAGE);
    }


    /**
     * Display the provided message as an error
     * @param title Message title
     * @param msg Message
     */
    private void displayError(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a generic incorrect value message when the input field cannot be parsed
     */
    private void displayIncorrectValue() {
        displayError("Incorrect Value", "Please enter a currency value using only numbers and a decimal.");
    }

    /**
     * Displays an insufficient funds message
     * @param msg Message
     */
    private void displayInsufficientFunds(String msg) {
        displayError("Insufficient Funds", msg);
    }
}