package sakki;

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
    empty(0, 0, null) {
        @Override
        public String toString() {
            return "-";
        }
    },

    movable(0, 0, null) {
        @Override
        public String toString() {
            return "o";
        }
    },

    capturable(0, 0, null) {
        @Override
        public String toString() {
            return "*";
        }
    },

    checked(0, 0, null) {
        @Override
        public String toString() {
            return "#";
        }
    },

    p(1, 1, Side.b), P(1, 1, Side.w),
    b(2, 3, Side.b), B(2, 3, Side.w),
    n(3, 3, Side.b), N(3, 3, Side.w),
    r(4, 5, Side.b), R(4, 5, Side.w),
    q(5, 9, Side.b), Q(5, 9, Side.w),
    k(6, 0, Side.b), K(6, 0, Side.w);

    private final int index;
    private final int value;
    private final Side side;

    /**
     * @param i Arbitrary unique index.
     * @param v Value of this type of piece.
     * @param s Side of this type of piece.
     */
    Type(int i, int v, Side s) {
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

    public Side getSide() {
        return side;
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

    public String nameToSan() {
        return (value == 1)? "": this.name().toUpperCase();
    }
}
