package fi.starck.sakki.board;

import java.util.HashSet;

/**
 * Castle class provides support for castling. It has two primary
 * functions; it tracks the availability of castling and provides
 * support needed to execute castling move (which logic differs
 * greatly from other moves).
 *
 * @author Tuomas Starck
 */
class Castle {
    private final String valid = "KQkq";

    private final Coord[] kingsSquares = {
        new Coord("e1"), new Coord("e8")
    };

    private final Coord[] kingsTargets = {
        new Coord("g1"), new Coord("c1"),
        new Coord("g8"), new Coord("c8")
    };

    private final Coord[] rooksSquares = {
        new Coord("h1"), new Coord("a1"),
        new Coord("h8"), new Coord("a8")
    };

    private final Coord[] rooksTargets = {
        new Coord("f1"), new Coord("d1"),
        new Coord("f8"), new Coord("d8")
    };

    private final Coord[][] freeSquares = {
        {new Coord("f1"), new Coord("g1")},
        {new Coord("b1"), new Coord("c1"), new Coord("d1")},
        {new Coord("f8"), new Coord("g8")},
        {new Coord("b8"), new Coord("c8"), new Coord("d8")}
    };

    private final Coord[][] safeSquares = {
        {new Coord("e1"), new Coord("f1"), new Coord("g1")},
        {new Coord("c1"), new Coord("d1"), new Coord("e1")},
        {new Coord("e8"), new Coord("f8"), new Coord("g8")},
        {new Coord("c8"), new Coord("d8"), new Coord("e8")}
    };

    private HashSet<Character> castling;

    /**
     * Default constructor allows all castings.
     */
    public Castle() {
        this("KQkq");
    }

    /**
     * Constructor for custom castling configuration.
     *
     * @param str Castling choices available.
     */
    public Castle(String str) {
        castling = new HashSet<Character>();
        enable(str);
    }

    /**
     * @param c Character of castling options.
     *
     * @return True if character is valid.
     */
    private boolean isValid(Character c) {
        return (valid.indexOf(c) != -1);
    }

    /**
     * @param str String of castling options.
     */
    public final void enable(String str) {
        for (Character c : str.toCharArray()) {
            if (isValid(c)) {
                castling.add(c);
            }
        }
    }

    /**
     * @param str String of castling options to be disabled.
     */
    public void disable(String str) {
        if (str == null) return;

        for (Character c : str.toCharArray()) {
            castling.remove(c);
        }
    }

    /**
     * @param move Move object.
     *
     * @return True if castling is allowed.
     */
    public boolean isAllowed(Move move) {
        return castling.contains(valid.charAt(index(move)));
    }

    /**
     * Disable castling options based on status of the game board.
     *
     * @param status Status of the board.
     */
    void crop(Type[][] status) {
        Coord K = kingsSquares[0];
        Coord k = kingsSquares[1];

        Coord hR = rooksSquares[0];
        Coord aR = rooksSquares[1];
        Coord hr = rooksSquares[2];
        Coord ar = rooksSquares[3];

        if (status[K.rank][K.file] != Type.K) disable("KQ");
        if (status[k.rank][k.file] != Type.k) disable("kq");

        if (status[hR.rank][hR.file] != Type.R) disable("K");
        if (status[aR.rank][aR.file] != Type.R) disable("Q");
        if (status[hr.rank][hr.file] != Type.r) disable("k");
        if (status[ar.rank][ar.file] != Type.r) disable("q");
    }

    /**
     * Return correct index for the literal data arrays above.
     *
     * @param move Move object.
     *
     * @return Index number.
     */
    public int index(Move move) {
        if (move.side() == Side.w) {
            return (move.castling() == move.KINGSIDE)? 0: 1;
        }
        else /* move.side() == Side.b */ {
            return (move.castling() == move.KINGSIDE)? 2: 3;
        }
    }

    /**
     * @param move Move object.
     *
     * @return Square where king ought to be.
     */
    public Coord getKingsSqr(Move move) {
        return kingsSquares[(move.side() == Side.w)? 0: 1];
    }

    /**
     * @param move Move object.
     *
     * @return Square to which king should move when castled.
     */
    public Coord getKingsTarget(Move move) {
        return kingsTargets[index(move)];
    }

    /**
     * @param move Move object.
     *
     * @return Square where rook ought to be.
     */
    public Coord getRooksSqr(Move move) {
        return rooksSquares[index(move)];
    }

    /**
     * @param move Move object.
     *
     * @return Square to which rook moves at castling.
     */
    public Coord getRooksTarget(Move move) {
        return rooksTargets[index(move)];
    }

    /**
     * @param move Move object.
     *
     * @return Squares which need to be vacant at castling.
     */
    public Coord[] getFreeSqrs(Move move) {
        return freeSquares[index(move)];
    }

    /**
     * @param move Move object.
     *
     * @return Squares which must not be threatened at castling.
     */
    public Coord[] getSafeSqrs(Move move) {
        return safeSquares[index(move)];
    }

    /**
     * @return String representation of castling options.
     */
    @Override
    public String toString() {
        String str = "";

        for (Character c : valid.toCharArray()) {
            if (castling.contains(c)) {
                str += c;
            }
        }

        return (str.equals(""))? "-": str;
    }
}
