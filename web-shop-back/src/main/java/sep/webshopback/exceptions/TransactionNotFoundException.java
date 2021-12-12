package sep.webshopback.exceptions;

public class TransactionNotFoundException extends Exception {
    private static final String message = "Transaction not found!";

    public TransactionNotFoundException() {
        super(message);
    }
}
