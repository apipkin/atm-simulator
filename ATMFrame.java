import javax.swing.JFrame;

public class ATMFrame extends JFrame {

    // Radio buttons for accounts should be created based on the number of accounts using the accounts name
    // use the grid flow
    // buttons should have event handlers attached and them
    // Input field should span a full row

    // TODO: Look up JOptionPane use

    public void handleWithdraw() {
        // is text numeric?
        // is amount in increments of $20? (not an account concern as any amount can be withdrawn from the account, just not through the ATM)
        // which account? [checking | savings]
        // are funds available?
        // display JOptionPane to display errors or success message
    }

    public void handleDeposit() {
        // is text numeric?
        // which account? [checking | savings]
        // deposit funds into selected acount
        // display JOptionPane with message. balance?
    }

    public void handleTransfer(){ 
        // is text numeric?
        // which account? [checking | savings]
        // are funds available?
        // display JOptionPane to display errors or success message
    }

    public void handleBalance() {
        // which account? [checking | savings]
        // display JOptionPane with message. balance?
    }
}