package sakki;

import java.util.ArrayList;

/**
 * Abstraction and common operations for various Chess pieces.
 *
 * @author Tuomas Starck
 */
abstract class Piece {
    protected Type type;
    protected Coord loc;
    protected Type[][] view;
    protected Side checked;
    protected String castlingEffect;
    protected ArrayList<String> moves;

    /**
     * Create a new piece. This constructor is usually
     * called from specific subclass.
     *
     * @param t Type of piece.
     * @param birthplace Location of piece.
     *
     * @throws IllegalArgumentException Most likely
     * a result of invalid user given FEN string.
     */
    public Piece(Type t, Coord birthplace) {
        if (t == null || birthplace == null) {
            throw new IllegalArgumentException();
        }

        type = t;
        loc = birthplace;
        view = new Type[8][8];
        castlingEffect = null;

        reset();
    }

    /**
     * Reset before update.
     */
    protected final void reset() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }

        checked = null;
        view[loc.rank][loc.file] = type;
        moves = new ArrayList<String>();
    }

    /**
     * Change piece's location. This method effectively
     * moves the piece to the target location.
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
     * Execute a move with side effects.
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
     * This method MUST ALWAYS be implemented in subclass.
     *
     * @param status Board status.
     * @param enpassant En passant target.
     */
    public void update(Type[][] status, Coord enpassant) {
        throw new UnsupportedOperationException("Missing update()");
    }

    /**
     * If piece can move to target square, mark it to the piece's view.
     *
     * @param co Target square.
     * @param status Status of the board.
     *
     * @return True if target was movable.
     */
    protected boolean markIfMovable(Coord co, Type[][] status) {
        if (co == null) return false;

        if (status[co.rank][co.file] == Type.empty) {
            view[co.rank][co.file] = Type.movable;
            moves.add(type.nameToSan() + co.toString());
            return true;
        }

        return false;
    }

    /**
     * If piece can capture the target square, make a mark to piece's view.
     *
     * Also if capturable piece happens to be opponents king, make a note
     * of it too.
     *
     * @param co Target square.
     * @param status Status of the board.
     */
    protected void markIfCapturable(Coord co, Type[][] status) {
        if (co == null) return;

        Type target = status[co.rank][co.file];

        if (type.isEnemy(target)) {
            if (target == Type.K) {
                checked = Side.w;
                view[co.rank][co.file] = Type.checked;
            }
            else if (target == Type.k) {
                checked = Side.b;
                view[co.rank][co.file] = Type.checked;
            }
            else {
                view[co.rank][co.file] = Type.capturable;
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
     * @return Type of piece.
     */
    Type getType() {
        return type;
    }

    /**
     * @return Location of piece.
     */
    Coord getLocation() {
        return loc;
    }

    /**
     * @return Side that is being checked.
     */
    Side isChecking() {
        return checked;
    }

    /**
     * @return Piece's effect on castling.
     */
    String castlingEffect() {
        return castlingEffect;
    }

    /**
     * @return Piece's view on the given square.
     */
    Type viewAt(Coord co) {
        return view[co.rank][co.file];
    }

    /**
     * @return The side the piece is playing.
     */
    Side getSide() {
        return type.getSide();
    }

    /**
     * @return All the moves available.
     */
    ArrayList<String> getMoves() {
        return moves;
    }

    /**
     * Pretty print piece's view of the board.
     *
     * @return A pretty string.
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
