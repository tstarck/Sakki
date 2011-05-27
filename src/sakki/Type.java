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
    empty(0, null) {
        @Override
        public String toString() {
            return "-";
        }
    },

    moveable(0, null) {
        @Override
        public String toString() {
            return "o";
        }
    },

    capturable(0, null) {
        @Override
        public String toString() {
            return "*";
        }
    },

    checked(0, null) {
        @Override
        public String toString() {
            return "#";
        }
    },

    p(1, Side.b), P(1, Side.w),
    b(3, Side.b), B(3, Side.w),
    n(3, Side.b), N(3, Side.w),
    r(5, Side.b), R(5, Side.w),
    q(9, Side.b), Q(9, Side.w),
    k(0, Side.b), K(0, Side.w);

    private final int value;
    private final Side side;

    Type(int v, Side s) {
        value = v;
        side = s;
    }

    public int getValue() {
        return value;
    }

    public boolean isPawn() {
        return (value == 1);
    }

    public boolean isWhite() {
        return (side == Side.w);
    }

    public boolean isBlack() {
        return (side == Side.b);
    }

    public boolean isEnemy(Type that) {
        return ((this.isWhite() && that.isBlack()) ||
                (this.isBlack() && that.isWhite()));
    }
}