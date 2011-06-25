package sakki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Reads and parses Algebraic chess notation.</p>
 *
 * <p>Algebraic chess notation (SAN) is used to describe the moves in
 * a game of chess. This program uses following flavor of SAN:</p>
 *
 * <p>
 * <ol>
 * <li>Capitalized character of the piece to be moved. None if
 *     piece is pawn. Following common character set is used:
 *       K - king,
 *       Q - queen,
 *       R - rook,
 *       B - bishop,
 *       N - knight</li>
 * <li>Optional hint of the square of departure (file, rank or
 *     both). Required only if move would be ambiguous otherwise.</li>
 * <li>Character x if and only if this is capturing move.</li>
 * <li>Target square.</li>
 * <li>Optional = character followed by character of an officer.</li>
 * <li>Character + or # used to claim check or mate accordingly.</li>
 * <li>Optional ! or ? characters are silently discarded.</li>
 * </ol>
 * </p>
 *
 * <p>Castlings may be written either with 0 (zero) or O (big-o).</p>
 *
 * <p>{@link http://en.wikipedia.org/wiki/Algebraic_chess_notation}</p>
 *
 * @see Chess
 *
 * @author Tuomas Starck
 */
class Move {
    public final int KINGSIDE = 1;
    public final int QUEENSIDE = 2;

    private final String castlingregex ="[0O]((-[0O]){1,2})([#+])?";

    private final String regularregex =
    "([NBRQK])?([a-h]?[1-8]?)(x)?([a-h][1-8])(=?([NBRQ]))?([#+])?(ep)?[!?]*";
    /*1:piece   2:from       3:x  4:to           6:promo   7:act */

    private Matcher move;
    private Matcher castle;

    private Side side;
    private Coord to;
    private Type piece;
    private Type promote;
    private String from;
    private int castling;
    private boolean capture;
    private boolean check;
    private boolean mate;

    /**
     * Parse SAN.
     *
     * @param str SAN string.
     * @param turn Which side hold move.
     *
     * @throws MoveException If SAN cannot be parsed.
     */
    public Move(String str, Side turn) throws MoveException {
        move = Pattern.compile(regularregex).matcher(str);
        castle = Pattern.compile(castlingregex).matcher(str);

        side = turn;
        to = null;
        piece = null;
        promote = null;
        from = "";
        castling = 0;
        capture = false;
        check = false;
        mate = false;

        if (move.matches()) {
            /* 1: Piece which is to be moved */
            piece = resolvePiece(move.group(1), side);

            /* 2: From (square hint) */
            if (move.group(2) != null) {
                from = move.group(2);
            }

            /* 3: Capture indicator */
            if (move.group(3) != null) {
                capture = true;
            }

            /* 4: To (target square) */
            to = new Coord(move.group(4));

            /* 6: Officer to which pawn is to be promoted */
            if (move.group(6) != null) {
                promote = resolvePiece(move.group(6), side);
            }

            /* 7: Check or mate status */
            parseCheckMate(move.group(7));
        }
        else if (castle.matches()) {
            /* Not strictly required, but for consistency */
            piece = resolvePiece("k", side);

            /* To which side to castle */
            castling = (castle.group(1).length() <3)? KINGSIDE: QUEENSIDE;

            /* Check or mate status */
            parseCheckMate(castle.group(3));
        }
        else {
            throw new MoveException("Incomprehensible command");
        }
    }

    /**
     * Resolve input to a type of piece.
     *
     * @param input Character of the piece.
     * @param side Side of piece.
     *
     * @return Piece type.
     */
    private Type resolvePiece(String input, Side side) {
        String str = (input == null)? "p": input;

        if (side == Side.w) {
            return Type.valueOf(str.toUpperCase());
        }
        else /* side == Side.b */ {
            return Type.valueOf(str.toLowerCase());
        }
    }

    /**
     * @param xtra Plus or hash character.
     */
    private void parseCheckMate(String xtra) {
        if (xtra != null) {
            if (xtra.equals("+")) {
                check = true;
            }
            if (xtra.equals("#")) {
                check = true;
                mate = true;
            }
        }
    }

    /**
     * @return Side of piece.
     */
    public Side side() {
        return side;
    }

    /**
     * @return Target coordinate.
     */
    public Coord to() {
        return to;
    }

    /**
     * @return Type of piece.
     */
    public Type piece() {
        return piece;
    }

    /**
     * @return Type of piece to be promoted to.
     */
    public Type promotion() {
        return promote;
    }

    /**
     * @return Hint of the square of departure.
     */
    public String from() {
        return from;
    }

    /**
     * @return Castling option.
     */
    public int castling() {
        return castling;
    }

    /**
     * @return True if this is castling move.
     */
    public boolean isCastling() {
        return (castling != 0);
    }

    /**
     * @return True if this should be capturing move.
     */
    public boolean isCapturing() {
        return capture;
    }

    /**
     * @return True if this should be checking move.
     */
    public boolean isChecking() {
        return check;
    }

    /**
     * @return True if this should be mating move.
     */
    public boolean isMating() {
        return mate;
    }
}
