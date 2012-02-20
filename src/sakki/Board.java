package sakki;

import java.util.ArrayList;

/**
 * Model of a chess board and pieces on it.
 *
 * This program uses standard chess board with 64 squares
 * in eight files and ranks.
 *
 * {@link http://en.wikipedia.org/wiki/Chessboard}
 *
 * @author Tuomas Starck
 */
class Board {
    private int[] material;
    private boolean[] checked;
    private Type[][] state;
    private ArrayList<Piece> board;

    /**
     * Constructs the initial position. Pieces are placed to their
     * standard start-of-game positions.
     */
    public Board() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", null);
    }

    /**
     * Constructs a game board with piece positions parsed from given FEN.
     *
     * @param fen First part of FEN string containing board outlook.
     * @param enpassant En passant target square (or null if none).
     */
    public Board(String fen, Coord enpassant) {
        int file = 0;
        int rank = 0;

        material = new int[2];
        checked = new boolean[2];
        state = new Type[8][8];
        board = new ArrayList<Piece>();

        /* FEN parsing here is permissive and uses best-effort
         * approach. This loop may produce nonsensical results
         * with bogus input.
         */
        for (char chr : fen.toCharArray()) {
            Coord co = null;

            try {
                co = new Coord(file, rank);
            }
            catch (IllegalArgumentException pass) {}

            if (chr == '/') {
                file = 0;
                rank++;
            }
            else if (Character.isDigit(chr)) {
                file += Character.digit(chr, 10);
            }
            else if (Character.isLetter(chr)) {
                board.add(createByName(chr, co));
                file++;
            }
            else {
                throw new IllegalArgumentException();
            }
        }

        /* If given input produced nonsensical results, it will
         * be caught by update and handled here.
         */
        try {
            update(enpassant);
        }
        catch (NullPointerException npe) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Create a new piece.
     *
     * @param type Type of the piece.
     * @param co Location of the piece.
     *
     * @return New piece created or null at failure.
     */
    private Piece createByType(Type type, Coord co) {
        return createByName(type.name().charAt(0), co);
    }

    /**
     * Create a new piece.
     *
     * @param chr SAN character of the piece.
     * @param co Location of the piece.
     *
     * @return New piece created or null at failure.
     */
    private Piece createByName(char chr, Coord co) {
        switch (chr) {
            case 'P': return new WhitePawn(co);
            case 'p': return new BlackPawn(co);
            case 'B': return new WhiteBishop(co);
            case 'b': return new BlackBishop(co);
            case 'N': return new WhiteKnight(co);
            case 'n': return new BlackKnight(co);
            case 'R': return new WhiteRook(co);
            case 'r': return new BlackRook(co);
            case 'Q': return new WhiteQueen(co);
            case 'q': return new BlackQueen(co);
            case 'K': return new WhiteKing(co);
            case 'k': return new BlackKing(co);
        }

        return null;
    }

    /**
     * Update the board status and the view of each piece.
     *
     * @param enpassant En passant target square (or null if none).
     */
    private void update(Coord enpassant) {
        material = new int[2];
        checked = new boolean[2];

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                state[i][j] = Type.empty;
            }
        }

        for (Piece piece : board) {
            if (piece == null) throw new NullPointerException();

            Coord loc = piece.location();

            state[loc.rank][loc.file] = piece.type();
        }

        for (Piece piece : board) {
            piece.update(state, enpassant);

            Type type = piece.type();
            Side side = type.getSide();
            Side target = piece.isChecking();

            if (target != null) {
                checked[target.index] = true;
            }

            material[side.index] += type.getValue();
        }
    }

    /**
     * Select a piece based on its location.
     *
     * @param target Location of piece.
     *
     * @return Piece or null.
     */
    private Piece pieceAt(Coord target) {
        for (Piece piece : board) {
            if (piece.location().equals(target)) {
                return piece;
            }
        }

        return null;
    }

    /**
     * Select only pieces which match given information.
     *
     * @param array List of pieces.
     * @param from Possible hint detailing wanted piece.
     *
     * @return List of pieces matching the hint. Hopefully just one!
     */
    private ArrayList<Piece> crop(ArrayList<Piece> array, String from) {
        if (from.isEmpty()) return array;

        ArrayList<Piece> cropd = new ArrayList<Piece>();

        for (Piece piece : array) {
            if (piece.location().toString().indexOf(from) != -1) {
                cropd.add(piece);
            }
        }

        return cropd;
    }

    /**
     * Choose a piece, which ought to be moved.
     *
     * @param move Move object.
     *
     * @return Piece to be moved.
     *
     * @throws MoveException If given information does not
     * explicitly and exclusively define a single piece.
     */
    private Piece whichPiece(Move move) throws MoveException {
        ArrayList<Piece> options = new ArrayList<Piece>();

        for (Piece piece : board) {
            if (piece.type() == move.piece()) {
                if (move.isCapturing()) {
                    if (piece.viewAt(move.to()) == Type.capturable) {
                        options.add(piece);
                    }
                }
                else {
                    if (piece.viewAt(move.to()) == Type.movable) {
                        options.add(piece);
                    }
                }
            }
        }

        options = crop(options, move.from());

        if (options.isEmpty()) {
            /* No piece matches given information */
            throw new MoveException("No such move available");
        }

        if (options.size() != 1) {
            /* Too many pieces match given information */
            throw new MoveException("Ambiguous move");
        }

        return options.get(0);
    }

    /**
     * Capture a opponents piece if required.
     *
     * @param target Target square.
     * @param capturable True if capture was claimed.
     *
     * @return Capture of some pieces affect the availability
     * of castling. Such information must be returned.
     *
     * @throws MoveException If capture cannot be executed.
     */
    private String capture(Coord target, boolean capturable) throws MoveException {
        String effect = "";
        Piece piece = pieceAt(target);

        if (piece != null) {
            if (capturable) {
                effect = piece.castlingEffect;
                board.remove(piece);
            }
            else {
                throw new MoveException("Unclaimed capture");
            }
        }
        else if (capturable) {
            throw new MoveException("Capture claimed in vain");
        }

        return effect;
    }

    /**
     * Check if king on either side is checked.
     *
     * @param move Move object.
     *
     * @throws MoveException If illegal move is requested or game
     * status does not correlate with input.
     */
    private void checkCheck(Move move) throws MoveException {
        int side = move.side().index;
        int otherside = (side == 0)? 1: 0;

        /* After a move, ones king may not be checked */
        if (checked[side]) {
            throw new MoveException("Illegal move", true);
        }

        if (checked[otherside]) {
            if (!move.isChecking()) {
                /* Check should not go undetected */
                throw new MoveException("Check without notice", true);
            }
        }
        else {
            if (move.isChecking()) {
                /* Also check may not be claimed if there are none */
                throw new MoveException("Lame check claim", true);
            }
        }
    }

    /**
     * Execute requested move if conditions allow it.
     *
     * @param move Move object.
     * @param enpassant En passant condition.
     *
     * @return Feedback of the new game status.
     *
     * @throws MoveException If move cannot be executed.
     */
    public Rebound move(Move move, Coord enpassant) throws MoveException {
        Rebound rebound;
        String castling = "";
        Side turn = move.side();

        Piece piece = whichPiece(move);

        /* En passant moves require additional logic,
         * so check if this is such a move.
         */
        if (move.piece().isPawn() && move.to().equals(enpassant)) {
            if (turn == Side.w) {
                capture(enpassant.south(1), move.isCapturing());
            }
            else /* turn == Side.b */ {
                capture(enpassant.north(1), move.isCapturing());
            }
        }
        else {
            castling = capture(move.to(), move.isCapturing());
        }

        rebound = piece.move(move);

        rebound.disableCastling(castling);

        if (rebound.canPromote()) {
            Type officer = move.promotion();

            if (officer != null) {
                board.remove(piece);
                board.add(createByType(officer, move.to()));
            }
        }

        update(rebound.getEnpassant());

        checkCheck(move);

        return rebound;
    }

    /**
     * Handle everything required when castling is requested.
     *
     * @param move Move object.
     * @param castling Castling availability.
     *
     * @return Feedback of the new game status.
     *
     * @throws MoveException If castling cannot be executed.
     *
     * @fixme Castling rules require that king must have safe
     * passage (i.e. all three squares must not be threatened).
     * Current implementation lacks checking of this condition.
     * See Castle#safeSquares.
     */
    public Rebound castling(Move move, Castle castling) throws MoveException {
        Rebound rebound;

        if (!castling.isAllowed(move)) {
            throw new MoveException("Castling not possible");
        }

        for (Coord co : castling.getFreeSqrs(move)) {
            if (state[co.rank][co.file] != Type.empty) {
                throw new MoveException("Castling requires vacant squares");
            }
        }

        Piece king = pieceAt(castling.getKingsSqr(move));
        Piece rook = pieceAt(castling.getRooksSqr(move));

        if (king == null || rook == null) {
            throw new MoveException("Unable to castle");
        }

        rebound = king.move(castling.getKingsTarget(move));
        rook.move(castling.getRooksTarget(move));

        update(null);

        checkCheck(move);

        return rebound;
    }

    /**
     * @param rank Array of Type object.
     *
     * @return FEN string of one rank.
     */
    private String packRank(Type[] rank) {
        int empties = 0;
        String str = "";

        for (Type file : rank) {
            if (file == Type.empty) {
                empties++;
            }
            else if (empties != 0) {
                str += String.valueOf(empties) + file;
                empties = 0;
            }
            else {
                str += file;
            }
        }

        if (empties != 0) {
            str += String.valueOf(empties);
        }

        return "/" + str;
    }

    /**
     * @return Game board state.
     */
    public Type[][] getState() {
        return state;
    }

    /**
     * @return Material of game board.
     */
    public int[] getMaterial() {
        return material;
    }

    /**
     * @return FEN string of the game board.
     */
    @Override
    public String toString() {
        String fen = "";

        for (Type[] rank : state) {
            fen += packRank(rank);
        }

        return fen.substring(1);
    }
}
