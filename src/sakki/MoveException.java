package sakki;

/**
 * Custom exception class for better error handling.
 *
 * @author Tuomas Starck
 */
class MoveException extends Exception {
    private String msg;
    private boolean dirty;

    public MoveException(String str) {
        this(str, false);
    }

    public MoveException(String str, boolean drt) {
        super(str);
        msg = str;
        dirty = drt;
    }

    public boolean isDirty() {
        return dirty;
    }

    @Override
    public String toString() {
        return msg;
    }
}
