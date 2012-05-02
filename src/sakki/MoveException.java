package sakki;

/**
 * Custom exception class for better error handling.
 *
 * @author Tuomas Starck
 */
public class MoveException extends Exception {
    private String msg;
    private boolean dirty;

    /**
     * @param str User-friendly error message.
     */
    public MoveException(String str) {
        this(str, false);
    }

    /**
     * @param str User-friendly error message.
     * @param drt True if this exception left game
     * in an inconsistent state.
     */
    public MoveException(String str, boolean drt) {
        super(str);
        msg = str;
        dirty = drt;
    }

    /**
     * @return True if game is in an inconsistent state.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * @return Error message.
     */
    @Override
    public String toString() {
        return msg;
    }
}
