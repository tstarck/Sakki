/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Class to convey feedback information about executed move.
 *
 * @author Tuomas Starck
 */
class Rebound {
    private boolean promotion;
    private String castlings;
    private Coord enpassant;

    public Rebound() {
        promotion = false;
        castlings = "";
        enpassant = null;
    }

    public void promotionAvailable() {
        promotion = true;
    }

    public void disableCastling(String str) {
        castlings += str;
    }

    public void setEnpassant(Coord co) {
        enpassant = co;
    }

    public boolean canPromote() {
        return promotion;
    }

    public String castlingObstacle() {
        return castlings;
    }

    public Coord getEnpassant() {
        return enpassant;
    }
}