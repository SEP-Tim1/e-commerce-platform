package sep.webshopback.exceptions;

public class ProductNotInStockException extends Exception {

    public ProductNotInStockException(String productName) {
        super("not enough product of type - " + productName + " - in stock");
    }
}
