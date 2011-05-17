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
    private Coord enpassant;

    public Rebound() {
        castling = null;
        enpassant = null;
    }

    public void preventCastling(String str) {
        castling = str;
    }

    public void setEnpassant(Coord co) {
        enpassant = co;
    }

    public String preventCastling() {
        return castling;
    }

    public Coord getEnpassant() {
        return enpassant;
    }
}