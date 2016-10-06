import java.text.NumberFormat;
import java.util.Locale;

public class Account {
    private double balance;
    private String name;

    private static int withdrawCount = 0;
    private static final double SERVICE_CHARGE = 1.5;

    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    public Account(String name) {
        this.balance = 0.0;
        this.name = name;
    }

    public Account(String name, double amount) {
        this.balance = amount;
        this.name = name;
    }

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

    public double deposit(double amount) {
        balance = balance + amount;
        return balance;
    }

    public double transferTo(Account account, double amount) throws InsufficientFunds {
        withdraw(amount);
        account.deposit(amount);

        return balance;
    }

    public double getBalance(){ 
        return balance;
    }

    public String getBalanceCurrency() {
        return currencyFormatter.format(balance);
    }

    public String toString() {
        return name + ": " + getBalanceCurrency();
    }
}