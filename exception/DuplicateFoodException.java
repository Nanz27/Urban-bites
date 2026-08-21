package exception;
/**
 * Thrown when trying to add a food item
 * whose ID already exists on the menu.
 */
public class DuplicateFoodException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateFoodException(String message) {
        super(message);
    }

}
