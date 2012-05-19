package sakki;

/**
 * Abstraction and common operations for various Chess pieces.
 *
 * @author Tuomas Starck
 */
abstract class Piece {
    protected Type me;
    protected Coord loc;
    protected Type[][] view;
    protected Side checked;
    protected String castlingEffect;

    /**
     * Create a new piece. This constructor is usually
     * called from specific subclass.
     *
     * @param type Type of piece.
     * @param birthplace Location of piece.
     */
    public Piece(Type type, Coord birthplace) {
        if (type == null || birthplace == null) {
            throw new IllegalArgumentException();
        }

        me = type;
        loc = birthplace;
        view = new Type[8][8];
        checked = null;
        castlingEffect = null;

        reset();
    }

    /**
     * Reset status for update.
     */
    protected final void reset() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }

        view[loc.rank][loc.file] = me;

        checked = null;
    }

    /**
     * @return Type of piece.
     */
    public Type type() {
        return me;
    }

    /**
     * @return Location of piece.
     */
    public Coord location() {
        return loc;
    }

    /**
     * @return Side which is being checked.
     */
    public Side isChecking() {
        return checked;
    }

    /**
     * @return Pieces effect on castling.
     */
    public String castlingEffect() {
        return castlingEffect;
    }

    /**
     * @param co Square on board.
     *
     * @return Pieces view on given square.
     */
    public Type viewAt(Coord co) {
        return view[co.rank][co.file];
    }

    /**
     * This method must always be implemented in subclass.
     *
     * @param status Board status.
     * @param enpassant En passant target.
     */
    public void update(Type[][] status, Coord enpassant) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Execute move by changing pieces location.
     *
     * @param target Target location.
     *
     * @return null
     */
    public Rebound move(Coord target) {
        loc = target;
        return null;
    }

    /**
     * Execute move by changing pieces location.
     *
     * @param move Move object.
     *
     * @return Feedback about effects of the move.
     */
    public Rebound move(Move move) {
        Rebound rebound = new Rebound();
        rebound.disableCastling(castlingEffect);
        move(move.to());
        return rebound;
    }

    /**
     * If piece can move to target square, mark it to the pieces view.
     *
     * @param sqr Target square.
     * @param status Status of the board.
     *
     * @return True if target was movable.
     */
    protected boolean markIfMovable(Coord sqr, Type[][] status) {
        if (sqr == null) return false;

        if (status[sqr.rank][sqr.file] == Type.empty) {
            view[sqr.rank][sqr.file] = Type.movable;
            return true;
        }

        return false;
    }

    /**
     * If piece can capture at target square, mark is to pieces view.
     *
     * Also if capturable piece happens to be opponents king, make
     * a note of it.
     *
     * @param sqr Target square.
     * @param status Status of the board.
     */
    protected void markIfCapturable(Coord sqr, Type[][] status) {
        if (sqr == null) return;

        Type target = status[sqr.rank][sqr.file];

        if (me.isEnemy(target)) {
            if (target == Type.K) {
                checked = Side.w;
                view[sqr.rank][sqr.file] = Type.checked;
            }
            else if (target == Type.k) {
                checked = Side.b;
                view[sqr.rank][sqr.file] = Type.checked;
            }
            else {
                view[sqr.rank][sqr.file] = Type.capturable;
            }
        }
    }

    /**
     * Kings options. Go through all squares next to current
     * location and see if they can be moved to.
     *
     * @param status Status of the board.
     */
    protected void markAdjacent(Type[][] status) {
        if (!markIfMovable(loc.north(1), status)) {
            markIfCapturable(loc.north(1), status);
        }

        if (!markIfMovable(loc.northeast(1), status)) {
            markIfCapturable(loc.northeast(1), status);
        }

        if (!markIfMovable(loc.east(1), status)) {
            markIfCapturable(loc.east(1), status);
        }

        if (!markIfMovable(loc.southeast(1), status)) {
            markIfCapturable(loc.southeast(1), status);
        }

        if (!markIfMovable(loc.south(1), status)) {
            markIfCapturable(loc.south(1), status);
        }

        if (!markIfMovable(loc.southwest(1), status)) {
            markIfCapturable(loc.southwest(1), status);
        }

        if (!markIfMovable(loc.west(1), status)) {
            markIfCapturable(loc.west(1), status);
        }

        if (!markIfMovable(loc.northwest(1), status)) {
            markIfCapturable(loc.northwest(1), status);
        }
    }

    /**
     * See if squares on same file or rank can be moved to.
     * This is used by rook and queen.
     *
     * @param status Status of the board.
     */
    protected void markStraight(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.north(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.north(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.east(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.east(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.south(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.south(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.west(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.west(i), status);
    }

    /**
     * See if squares on each diagonal path can be moved to.
     * This is used by bishop and queen.
     *
     * @param status Status of the board.
     */
    protected void markDiagonal(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.northeast(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.northeast(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.southeast(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.southeast(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.southwest(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.southwest(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMovable(loc.northwest(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.northwest(i), status);
    }

    /**
     * See if the piece is threatening certain squares.
     *
     * @param shouldBeSafe List of coordinates.
     *
     * @return True if the piece threatens any of the given
     * squares. False otherwise.
     */
    public boolean isThreatening(Type piece, Coord[] shouldBeSafe) {
        if (piece.isEnemy(me)) {
            for (Coord co : shouldBeSafe) {
                if (view[co.rank][co.file] != Type.empty) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Pretty print pieces view of the board.
     *
     * @return Pieces view of board.
     */
    @Override
    public String toString() {
        String str = "";

        for (Type[] rank : view) {
            str += "\n";
            for (Type file : rank) {
                str += " " + file;
            }
        }

        return str;
    }
}
