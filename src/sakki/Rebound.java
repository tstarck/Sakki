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
public class Rebound {
    private String castling;
    private boolean promotion;
    private Coord enpassant;

    public Rebound() {
        castling = null;
        promotion = false;
        enpassant = null;
    }

    public void preventCastling(String str) {
        castling = str;
    }

    public void promotionAvailable() {
        promotion = true;
    }

    public void setEnpassant(Coord co) {
        enpassant = co;
    }

    public String preventCastling() {
        return castling;
    }

    public boolean canPromote() {
        return promotion;
    }

    public Coord getEnpassant() {
        return enpassant;
    }
}