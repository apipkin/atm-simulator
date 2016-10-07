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




public class ATMSimulator extends JFrame {

    static final int WIDTH = 300,
                     HEIGHT = 200,
                     GUTTER = 50,
                     GRID_GAP = 10;

    static final String NAME = "ATM Simulator",
                        ACCT_CHECKING = "checking",
                        ACCT_SAVING = "saving";

    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    private Account checkingAccount, savingAccount;
    private Hashtable<String, Account> accounts = new Hashtable<String, Account>();

    private JPanel gridPanel = new JPanel();
    private GridBagLayout gridLayout = new GridBagLayout();
    private GridBagConstraints gridConstraints = new GridBagConstraints();
    private Insets gridPadding = new Insets(5, 3, 5, 3);

    private JButton withdraw, deposit, transferTo, balance;

    private JRadioButton checking, savings;
    private String selectedAccount = ACCT_CHECKING;
    private ButtonGroup group = new ButtonGroup();

    private JTextField input;

    public ATMSimulator() {
        super(NAME);
        init();
    }

    public ATMSimulator(String name) {
        super(name);
        init();
    }

    /****
     *
     *        PUBLIC
     *
     ****/

    public void display() {
        setVisible(true);
    }

    /****
     *
     *        INITIALIZE
     *
     ****/

    private void init() {
        initAccounts();
        render();
    }

    private void initAccounts() {
        checkingAccount = new Account("Checking");
        accounts.put(ACCT_CHECKING, checkingAccount);

        savingAccount = new Account("Saving");
        accounts.put(ACCT_SAVING, savingAccount);
    }

    /****
     *
     *        RENDER
     *
     ****/

    private void render() {
        setFrame(WIDTH, HEIGHT);
        renderLayout();
        renderPanel();
        renderButtons();
        renderRadios();
        renderInput();
    }

    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void renderLayout() {
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
    }

    private void renderPanel() {
        gridPanel.setLayout(gridLayout);
        add(gridPanel);
    }

    private void renderButtons() {
        addButton(withdraw, "Withdraw");
        addButton(deposit, "Deposit");
        addButton(transferTo, "Transfer To");
        addButton(balance, "Balance");
    }

    private void addButton(JButton btn, String label) {
        btn = new JButton(label);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(e, label.toLowerCase());
            }
        });

        addCompontent(btn);
    }

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

    private void renderRadios() {
        checking = addRadioButton(checking, "Checking");
        savings = addRadioButton(savings, "Savings");

        switch (selectedAccount) {
            case ACCT_CHECKING:
                checking.setSelected(true);
                group.setSelected(checking.getModel(), true);
                break;
            case ACCT_SAVING:
                savings.setSelected(true);
                group.setSelected(savings.getModel(), true);
                break;
        }
    }

    private JRadioButton addRadioButton(JRadioButton btn, String label) {
        btn = new JRadioButton(label);
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

    private void renderInput() {
        input = new JTextField();
        addCompontent(input, 2);
    }

    private void gridNewRow() {
        gridConstraints.gridx = 0;
        gridConstraints.gridy += 1;
    }

    private void gridNewCol() {
        gridConstraints.gridx += 1;
    }

    private void addCompontent(JComponent comp) {
        addCompontent(comp, 1);
    }

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

    /****
     *
     *        HANDLERS
     *
     ****/

    private void handleWithdraw() {
        try {
            String val = input.getText();
            double amount = Double.parseDouble(val);

            if (amount % 20 != 0.0) {
                displayError("Incorrect Value", "The withrawal amount must be in multiples of $20.");
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
            displayInsuffientFunds("Your accout does not have the funds necessary to make this withdrawal.");
        }
    }

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

    private void handleTransfer(){
        try {
            String val = input.getText();
            double amount = Double.parseDouble(val);

            Account toAccount = accounts.get(selectedAccount);
            Account fromAccount = accounts.get((selectedAccount == ACCT_CHECKING) ? ACCT_SAVING : ACCT_CHECKING);

            fromAccount.transferTo(toAccount, amount);

            String msg = String.format("Transfered: %s\nFrom: %s\nTo: %s", currencyFormatter.format(amount), fromAccount.getName(), toAccount.getName());
            displayBalances(msg);
        }
        catch (NumberFormatException e) {
            displayIncorrectValue();
        }
        catch (InsufficientFunds e) {
            displayInsuffientFunds("Your accout does not have the funds necessary to make this withdrawal.");
        }
    }

    private void handleBalance() {
        displayBalance(accounts.get(selectedAccount));
    }

    private void handleCheckingClick() {
        selectedAccount = ACCT_CHECKING;
    }

    private void handleSavingsClick() {
        selectedAccount = ACCT_SAVING;
    }

    /****
     *
     *        ACTIONS
     *
     ****/

    private void displayBalance(Account acct) {
        displayBalance(acct, "");
    }

    private void displayBalance(Account acct, String msg) {

        if (msg != null && !msg.trim().isEmpty()) {
            msg += "\n\n------------------------------\n";
        }

        msg += acct;

        JOptionPane.showMessageDialog(this, msg, acct.getName() + " Balance", JOptionPane.PLAIN_MESSAGE);
    }

    private void displayBalances() {
        displayBalances("");
    }

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


    private void displayError(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    private void displayIncorrectValue() {
        displayError("Incorrect Value", "Please enter a currency value using only numbers and a decimal.");
    }

    private void displayInsuffientFunds(String msg) {
        displayError("Insufficient Funds", msg);
    }
}