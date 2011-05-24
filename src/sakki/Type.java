/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * List of pieces available in the game of chess.
 *
 * Pieces are named according to the common convention amongst English-speaking
 * players. Capital letters indicate white pieces and vice versa (in compliance
 * with Forsyth–Edwards Notation).
 *
 * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">
 * Forsyth–Edwards Notation</a>
 *
 * @author Tuomas Starck
 */
enum Type {
    /**
     * This value marks the absence of a piece.
     */
    empty(0, '.') {
        @Override
        public String toString() {
            return "-";
        }
    },

    moveable(0, '.') {
        @Override
        public String toString() {
            return "*";
        }
    },

    capturable(0, '.') {
        @Override
        public String toString() {
            return "x";
        }
    },

    checked(0, '.') {
        @Override
        public String toString() {
            return "#";
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
        return (side == 'w');
    }

    public boolean isBlack() {
        return (side == 'b');
    }

    public boolean isEnemy(Type that) {
        return ((this.isWhite() && that.isBlack()) ||
                (this.isBlack() && that.isWhite()));
    }
}