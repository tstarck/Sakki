package sakki;

/**
 * <p>An implementation of The Game of Chess for two players.</p>
 *
 * <p><i>Standard Algebraic Notation</i> (SAN) a.k.a. <i>Algebraic chess
 * notation</i> is used to describe the moves in a game of chess. Besides
 * the moves, this program uses SAN coordinate system for game board
 * ranks (1-8) and files (a-h).</p>
 *
 * <p>{@link http://en.wikipedia.org/wiki/Algebraic_chess_notation}</p>
 *
 * <p><i>Forsyth-Edwards Notation</i> (FEN) is a standard notation
 * for describing any particular position of a chess game. FEN is
 * used extensively throughout this program and it can be used
 * to initialize a new game with arbitrary position.</p>
 *
 * <p>
 * FEN has six parts:
 * <ol>
 * <li>Piece placement from 8th rank to 1st and from a file to h with
 *     '/' separating ranks. Pieces are marked following SAN (white
 *     pieces capitalized and black pieces lower case) and digits (1-8)
 *     mark the number of consecutive empty squares.</li>
 * <li>Letter w (white) or b (black) depending on which side moves next.</li>
 * <li>Letters K, Q (white), k and q (black) mark if castling is
 *     available to kingside or queenside. Character '-' marks the
 *     absence of any possibility.</li>
 * <li>En passant target square or '-' if en passant is not available.</li>
 * <li>Halfmove counter. Required for compliance with fifty-move rule.</li>
 * <li>Fullmove counter.</li>
 * </ol>
 * </p>
 *
 * <p>{@link http://en.wikipedia.org/wiki/Forsyth-Edwards_Notation}</p>
 * <p>{@link http://en.wikipedia.org/wiki/Fifty-move_rule}</p>
 *
 * @see Move
 *
 * @author Tuomas Starck
 */
public class Chess {
    private Board board;
    private Side turn;
    private Castle castling;
    private Coord enpassant;
    private int halfmove;
    private int fullmove;

    /**
     * Constructs the initial position. Pieces and game settings
     * are created and set to their standard start-of-game values.
     */
    public Chess() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    /**
     * Constructs a new game by parsing given string as FEN.
     *
     * @param fenString FEN to be used to initialize a game.
     */
    public Chess(String fenString) {
        this(fenString.split(" "));
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
        turn = Side.w;
        enpassant = null;
        halfmove = 0;
        fullmove = 1;

        if (fenArray.length >= 4) {
            try {
                enpassant = new Coord(fenArray[3]);
            }
            catch (IllegalArgumentException pass) {}
        }

        if (fenArray.length >= 1) {
            board = new Board(fenArray[0], enpassant);
        }
        else {
            board = new Board();
        }

        if (fenArray.length >= 2) {
            if (fenArray[1].equals("b")) {
                turn = Side.b;
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
     * Check if given location has a piece which has the turn to move.
     *
     * This method is for GUI interactions. When given a SAN string,
     * check that there is a piece and that the piece is on side which
     * holds the next move.
     *
     * Method name pun intended.
     *
     * @param loc SAN location.
     *
     * @return null or the type of the piece.
     */
    public Type moveableType(String loc) { //:D
        Piece pc = board.pieceAt(new Coord(loc));

        if (pc == null) return null;

        Type tp = pc.type();

        if (tp.getSide() == turn) {
            return tp;
        }

        return null;
    }

    /**
     * Make a move.
     *
     * @param algebraic Move in Algebraic chess notation.
     *
     * @throws MoveException If move cannot be executed.
     */
    public void move(String algebraic) throws MoveException {
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

        if (turn == Side.w) {
            turn = Side.b;
        }
        else {
            turn = Side.w;
            fullmove++;
        }

        castling.disable(rebound.castlingObstacle());

        enpassant = rebound.getEnpassant();

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
     * @return the Side which holds next move.
     */
    public Side getTurn() {
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

    /**
     * @return FEN a.k.a. current game situation in one string.
     */
    @Override
    public String toString() {
        String ep = (enpassant == null)? "-": enpassant.toString();
        return String.format("%s %s %s %s %d %d",
            board, turn.name(), castling, ep, halfmove, fullmove);
    }
}
