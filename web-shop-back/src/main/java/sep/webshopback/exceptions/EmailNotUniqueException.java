package sep.webshopback.exceptions;

public class EmailNotUniqueException extends Exception{
    private static final String message = "The email you entered is not unique!";

    public EmailNotUniqueException() {
        super(message, new Throwable("email"));
    }
}
