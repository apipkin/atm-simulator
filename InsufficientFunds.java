public class InsufficientFunds extends Exception {
    private static final String MESSAGE = "Insufficient funds to complete the transaction.";
    public InsufficientFunds() {
        super(MESSAGE);
    }

    public InsufficientFunds(String message) {
        super(message);
    }
}