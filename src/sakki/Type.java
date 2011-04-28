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

    p("w"), P("b"),
    b("w"), B("b"),
    n("w"), N("b"),
    r("w"), R("b"),
    q("w"), Q("b"),
    k("w"), K("b");

    private final String side;

    Type(String s) {
        side = s;
    }

    public boolean enemy(Type that) {
        if (this.side.equals("w") && that.side.equals("b")) {
            return true;
        }
        else if (this.side.equals("b") && that.side.equals("w")) {
            return true;
        }

        return false;
    }
}