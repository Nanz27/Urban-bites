package exception;

/**
 * Thrown when trying to place an empty order.
 */
public class EmptyOrderException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyOrderException(String message) {
        super(message);
    }

}