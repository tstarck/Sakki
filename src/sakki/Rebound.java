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
    private String castling;
    private Coord enpassant;

    public Rebound() {
        castling = null;
        promotion = false;
        enpassant = null;
    }

    public void promotionAvailable() {
        promotion = true;
    }

    public void preventCastling(String str) {
        castling = str;
    }

    public void setEnpassant(Coord co) {
        enpassant = co;
    }

    public boolean canPromote() {
        return promotion;
    }

    public String preventCastling() {
        return castling;
    }

    public Coord getEnpassant() {
        return enpassant;
    }
}