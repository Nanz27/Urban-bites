package exception;

/**
 * Thrown when a food item cannot be found.
 */
public class FoodNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public FoodNotFoundException(String message) {
        super(message);
    }

}