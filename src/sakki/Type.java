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
    empty("") {
        @Override
        public String toString() {
            return ".";
        }
    },

    moveable("") {
        @Override
        public String toString() {
            return "o";
        }
    },

    capturable("") {
        @Override
        public String toString() {
            return "x";
        }
    },

    p("b"), P("w"),
    b("b"), B("w"),
    n("b"), N("w"),
    r("b"), R("w"),
    q("b"), Q("w"),
    k("b"), K("w");

    private final String side;

    Type(String s) {
        side = s;
    }

    public boolean isEnemy(Type that) {
        if (this.side.equals("w") && that.side.equals("b")) return true;

        if (this.side.equals("b") && that.side.equals("w")) return true;

        return false;
    }

    public boolean isPawn() {
        if (this == Type.P || this == Type.p) return true;

        return false;
    }
}