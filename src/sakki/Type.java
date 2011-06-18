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
enum Type {
    /**
     * This value marks the absence of a piece.
     *
     * @fixme Might be smart (performance wise) to
     * replace this with null.
     */
    empty(0, null) {
        @Override
        public String toString() {
            return "-";
        }
    },

    movable(0, null) {
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

    /**
     * @param v Value of this type of piece.
     * @param s Side of this type of piece.
     */
    Type(int v, Side s) {
        value = v;
        side = s;
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
}
