package projetoecommerce.exception;

public class EcommerceException extends Exception {
    public EcommerceException(String message) {
        super(message);
    }

    public EcommerceException(String message, Throwable cause) {
        super(message, cause);
    }
}