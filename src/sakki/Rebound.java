package sakki;

/**
 * Class to convey feedback about executed move.
 *
 * @author Tuomas Starck
 */
class Rebound {
    private boolean checked;
    private boolean promotion;
    private String castlings;
    private Coord enpassant;

    Rebound() {
        checked = false;
        promotion = false;
        castlings = "";
        enpassant = null;
    }

    void kingChecked(boolean bool) {
        checked = bool;
    }

    void promotionAvailable() {
        promotion = true;
    }

    void disableCastling(String str) {
        castlings += str;
    }

    void setEnpassant(Coord co) {
        enpassant = co;
    }

    boolean isKingChecked() {
        return checked;
    }

    boolean canPromote() {
        return promotion;
    }

    String castlingObstacle() {
        return castlings;
    }

    Coord getEnpassant() {
        return enpassant;
    }
}
