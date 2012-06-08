package fi.starck.sakki.board;

/**
 * <p>List of pieces available in the Game of Chess. Also a few other
 * values useful to indicate status of a square on Chess board.</p>
 *
 * <p>Pieces are named according to the Standard Algebraic Notation
 * common amongst English-speaking players. Capital letters indicate
 * white pieces and lower case letters black pieces.</p>
 *
 * @see Chess
 *
 * @author Tuomas Starck
 */
public enum Type {
    empty(0, 0, false) {
        @Override
        public String toString() {
            return "-";
        }
    },

    movable(0, 0, false) {
        @Override
        public String toString() {
            return "o";
        }
    },

    capturable(0, 0, false) {
        @Override
        public String toString() {
            return "*";
        }
    },

    checked(0, 0, false) {
        @Override
        public String toString() {
            return "#";
        }
    },

    p(1, 1, false), P(1, 1, true),
    b(2, 3, false), B(2, 3, true),
    n(3, 3, false), N(3, 3, true),
    r(4, 5, false), R(4, 5, true),
    q(5, 9, false), Q(5, 9, true),
    k(6, 0, false), K(6, 0, true);

    private final int index;
    private final int value;
    private final boolean side;

    /**
     * @param i Arbitrary unique index.
     *
     * @param v Value of this type of piece. Values conform
     *          to old Modenese School standard.
     *
     * @param s Side of this type of piece. True or false
     *          are white or black accordingly.
     */
    Type(int i, int v, boolean s) {
        index = i;
        value = v;
        side = s;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public boolean getSide() {
        return side;
    }

    public boolean isPawn() {
        return (value == 1);
    }

    public boolean isEnemy(Type that) {
        return this.side != that.side;
    }

    /**
     * SAN move compatible name.
     *
     * While type names work well as they are with FEN strings,
     * this method provides type strings which abide SAN moves.
     *
     * @return a SAN move compatible name of this type.
     */
    public String nameToSan() {
        return this.isPawn()? "": this.name().toUpperCase();
    }
}
