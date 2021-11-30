package sep.webshopback.exceptions;

public class ProductNotFoundException extends Exception {
    private static final String message = "Product not found!";

    public ProductNotFoundException() {
        super(message);
    }
}
