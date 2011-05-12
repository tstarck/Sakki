/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * List of pieces available in the game of chess.
 *
 * Pieces are named according to the common convention amongst English-speaking players. Capital letters indicate white pieces and vice versa (in compliance with Forsyth–Edwards Notation).
 *
 * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth–Edwards Notation</a>
 *
 * @author Tuomas Starck
 */
public enum Type {
    /**
     * This value marks the absence of a piece.
     */
    empty(0, '.') {
        @Override
        public String toString() {
            return ".";
        }
    },

    moveable(0, '.') {
        @Override
        public String toString() {
            return "o";
        }
    },

    capturable(0, '.') {
        @Override
        public String toString() {
            return "x";
        }
    },

    p(1, 'b'), P(1, 'w'),
    b(3, 'b'), B(3, 'w'),
    n(3, 'b'), N(3, 'w'),
    r(5, 'b'), R(5, 'w'),
    q(9, 'b'), Q(9, 'w'),
    k(0, 'b'), K(0, 'w');

    private final int value;
    private final char side;

    Type(int v, char s) {
        value = v;
        side = s;
    }

    public int getValue() {
        return value;
    }

    public boolean isWhite() {
        if (side == 'w') return true;
        return false;
    }

    public boolean isBlack() {
        if (side == 'b') return true;
        return false;
    }

    public boolean isEnemy(Type that) {
        if (this.isWhite() && that.isBlack()) return true;
        if (this.isBlack() && that.isWhite()) return true;
        return false;
    }

    public boolean isPawn() {
        if (this == Type.P || this == Type.p) return true;
        return false;
    }
}