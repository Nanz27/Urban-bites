package exception;

/**
 * Custom exception thrown when a customer
 * with the same ID already exists.
 */
public class DuplicateCustomerException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateCustomerException(String message) {
        super(message);
    }

}