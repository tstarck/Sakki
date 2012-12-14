package fi.starck.sakki.board;

/**
 * <p>An implementation of The Game of Chess.</p>
 *
 * <p><b>SAN</b> a.k.a. <i>Standard Algebraic Notation</i> a.k.a. <i>Algebraic
 * chess notation</i> is used to describe the moves in a game of chess. Besides
 * the moves, this program uses SAN coordinate system thus game board has
 * <b>ranks</b> (1-8) and <b>files</b> (a-h).</p>
 *
 * <p>{@link http://en.wikipedia.org/wiki/Algebraic_chess_notation}</p>
 *
 * <p><b>FEN</b> a.k.a. <i>Forsyth-Edwards Notation</i> is a standard notation
 * for describing any particular position of a chess game. This program
 * utilises FEN to communicate status on the board and to initialize a game
 * with arbitrary position.</p>
 *
 * <p>
 * FEN has six space separated parts:
 * <ol>
 * <li>Piece placement from <b>8</b>th rank to <b>1</b>st and from file
 *     <b>a</b> to <b>h</b> with slash (<b>/</b>) separating ranks. Pieces
 *     are marked in accordance with SAN and thus white pieces are capitalized
 *     and black pieces are written in lower case. Digits 1 to 8 mark the
 *     number of consecutive empty squares.</li>
 * <li>Letter <b>w</b> (white) or <b>b</b> (black) depending on which side
 *     moves next.</li>
 * <li>Letters <b>K</b>, <b>Q</b>, <b>k</b> and <b>q</b> mark castling options
 *     for white and black player to kingside or queenside. Dash (<b>-</b>)
 *     marks the absence of any castling possibility.</li>
 * <li><i>En passant</i> target square or a dash if en passant is not
 *     available.</li>
 * <li>Halfmove counter for compliance with fifty-move rule.</li>
 * <li>Fullmove counter.</li>
 * </ol>
 * </p>
 *
 * <p>{@link http://en.wikipedia.org/wiki/Forsyth-Edwards_Notation}</p>
 * <p>{@link http://en.wikipedia.org/wiki/Fifty-move_rule}</p>
 *
 * @see Board
 * @see Move
 *
 * @author Tuomas Starck
 */
public class Chess {
    protected Board board;
    private boolean turn;
    private Castle castling;
    private Coord enpassant;
    private int halfmove;
    private int fullmove;
    private boolean checked;

    /**
     * Constructs the initial position. Pieces and game settings
     * are created and set to their standard start-of-game values.
     *
     * @see #Chess(String)
     */
    public Chess() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Constructs a new game by parsing given string as FEN.
     *
     * Any useless input (null, empty string, bogus data) will lead
     * to using default values.
     *
     * @see #Chess(String[])
     *
     * @param fenString FEN to be used to initialize a game.
     */
    public Chess(String fenString) {
        this(fenString == null? new String[]{}: fenString.split(" "));
    }

    /**
     * Constructs a new game by parsing given array of string as
     * an array of FEN primitives.
     *
     * FEN parsing is permissive and uses best-effort approach
     * i.e. anything, that is salvaged of given input, is used
     * as best as possible, and for the rest, reasonable default
     * values are assumed.
     *
     * @param fenArray An array of FEN primitives used to initialize
     * a game. Only the six first array elements are used and the
     * are discarded if provided.
     */
    public Chess(String[] fenArray) {
        turn = true;
        enpassant = null;
        halfmove = 0;
        fullmove = 1;

        if (fenArray.length >= 4) {
            try {
                enpassant = new Coord(fenArray[3]);
            }
            catch (Exception pass) {}
        }

        if (fenArray.length >= 1) {
            board = new Board(fenArray[0], enpassant);
        }
        else {
            board = new Board();
        }

        if (fenArray.length >= 2) {
            if (fenArray[1].equals("b")) {
                turn = false;
            }
        }

        if (fenArray.length >= 3) {
            castling = new Castle(fenArray[2]);
        }
        else {
            castling = new Castle();
        }

        castling.crop(board.getState());

        if (fenArray.length >= 6) {
            try {
                halfmove = Integer.parseInt(fenArray[4]);
                fullmove = Integer.parseInt(fenArray[5]);
            }
            catch (NumberFormatException pass) {}
        }
    }

    /**
     * Make a move.
     *
     * @param algebraic Move in Algebraic chess notation.
     *
     * @throws MoveException If move cannot be executed.
     */
    public Rebound move(String algebraic) throws MoveException {
        Rebound rebound;

        Move move = new Move(algebraic, turn);

        if (move.isCastling()) {
            /* Castlings are tricky and handled separately */
            rebound = board.castling(move, castling);
        }
        else {
            /* If no need to castle, then a regular move */
            rebound = board.move(move, enpassant);
        }

        if (turn) {
            turn = false;
        }
        else {
            turn = true;
            fullmove++;
        }

        castling.disable(rebound.castlingObstacle());

        enpassant = rebound.getEnpassant();

        checked = rebound.isKingChecked();

        /* The fifty-move rule: a player can claim a draw if no capture
         * has been made and no pawn has been moved in the last 50
         * consecutive moves.
         *
         * So, if halfmove counter hits 100, aforementioned condition
         * becomes true.
         */
        if (move.isCapturing() || move.piece().isPawn()) {
            halfmove = 0;
        }
        else {
            halfmove++;
        }

        return rebound;
    }

    /**
     * Return information about given square.
     *
     * @param loc Square location as a SAN string.
     *
     * @return The type of the piece or null.
     */
    public Type typeAt(String loc) {
        try {
            Piece piece = board.pieceAt(new Coord(loc));
            return (piece == null)? null: piece.getType();
        }
        catch (Exception pass) {}

        return null;
    }

    /**
     * Return the state of the board.
     *
     * @return Two dimensional array of type Type containing
     * the state of the board.
     */
    public Type[][] getState() {
        return board.getState();
    }

    /**
     * @return True if white holds the next move. False otherwise.
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * @return the halfmove count.
     */
    public int getHalfmove() {
        return halfmove;
    }

    /**
     * @return the fullmove count.
     */
    public int getFullmove() {
        return fullmove;
    }

    /**
     * @return Game board material status.
     */
    public int[] getMaterial() {
        return board.getMaterial();
    }

    /**
     * @return the target of en passant or a dash.
     */
    public String getEnpassant() {
        return (enpassant == null)? "-": enpassant.toString();
    }

    /**
     * @return a string of castling possibilities or a dash.
     */
    public String getCastling() {
        return castling.toString();
    }

    public boolean isChecked() {
        return checked;
    }

    /**
     * @return FEN a.k.a. current game situation in one string.
     */
    @Override
    public String toString() {
        String ep = (enpassant == null)? "-": enpassant.toString();
        String side = turn? "w": "b";

        return String.format("%s %s %s %s %d %d",
            board, side, castling, ep, halfmove, fullmove);
    }
}
