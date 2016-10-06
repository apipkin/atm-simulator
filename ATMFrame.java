import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class ATMFrame extends JFrame {

    static final int WIDTH = 800,
                     HEIGHT = 600,
                     GUTTER = 50,
                     GRID_GAP = 10;

    static final String NAME = "ATM Simulator",
                        ACCT_CHECKING = "checking",
                        ACCT_SAVING = "saving";

    private JPanel gridPanel = new JPanel();
    private GridLayout gridLayout = new GridLayout(3,2, GRID_GAP, GRID_GAP);

    private JButton withdraw, deposit, transferTo, balance;

    private JRadioButton checking, savings;
    private String selectedAccount;
    private ButtonGroup group = new ButtonGroup();


    // Radio buttons for accounts should be created based on the number of accounts using the accounts name
    // use the grid flow
    // buttons should have event handlers attached and them
    // Input field should span a full row

    // TODO: Look up JOptionPane use

    public ATMFrame() {
        super(NAME);
        init();
    }

    public ATMFrame(String name) {
        super(name);
        init();
    }

    public void display() {
        setVisible(true);
    }

    private void init() {
        setFrame(WIDTH, HEIGHT);
        initPanel();
        initButtons();
        initRadios();
    }

    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initPanel() {
        gridPanel.setLayout(gridLayout);
        gridPanel.setBackground(Color.blue);
        add(gridPanel);
    }

    private void initButtons() {
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

        gridPanel.add(btn);
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

    private void initRadios() {
        addRadioButton(checking, "Checking");
        addRadioButton(savings, "Savings");
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
        gridPanel.add(btn);

        return btn;
    }

    private void handleRadioClick(ActionEvent e) {
        System.out.println("hadnleRadioClick:: " + e.getActionCommand());
        switch (e.getActionCommand()) {
            case "checking":
                handleCheckingClick();
                break;
            case "savings":
                handleSavingsClick();
                break;
        }
    }

    private void handleWithdraw() {
        System.out.println("handleWithdraw");
        // is text numeric?
        // is amount in increments of $20? (not an account concern as any amount can be withdrawn from the account, just not through the ATM)
        // which account? [checking | savings]
        // are funds available?
        // display JOptionPane to display errors or success message
    }

    private void handleDeposit() {
        System.out.println("handleDeposit");
        // is text numeric?
        // which account? [checking | savings]
        // deposit funds into selected acount
        // display JOptionPane with message. balance?
    }

    private void handleTransfer(){
        System.out.println("handleTransfer");

        // is text numeric?
        // which account? [checking | savings]
        // are funds available?
        // display JOptionPane to display errors or success message
    }

    private void handleBalance() {
        System.out.println("handleBalance");

        // which account? [checking | savings]
        // display JOptionPane with message. balance?
    }

    private void handleCheckingClick() {
        System.out.println("handleCheckingClick");
        selectedAccount = ACCT_CHECKING;
    }

    private void handleSavingsClick() {
        selectedAccount = ACCT_SAVING;
    }
}