package waddles;

/**
 * Represents an exception specific to Waddles, thrown when the user's
 * input cannot be understood or is otherwise invalid.
 */
public class WaddlesException extends Exception {

    /**
     * Creates a WaddlesException with the given error message.
     *
     * @param message Message describing what went wrong.
     */
    public WaddlesException(String message) {
        super(message);
    }
}
