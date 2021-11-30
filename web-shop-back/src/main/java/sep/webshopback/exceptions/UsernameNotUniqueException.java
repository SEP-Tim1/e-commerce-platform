package sep.webshopback.exceptions;

public class UsernameNotUniqueException extends Exception{
    private static final String message = "The username you entered is not unique!";

    public UsernameNotUniqueException() {
        super(message, new Throwable("username"));
    }
}