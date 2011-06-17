package sakki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads and parses Algebraic chess notation.
 *
 * Algebraic chess notation (SAN) is used to describe the moves in
 * a game of chess. This program uses following flavor of SAN:
 *
 * (1) Capitalized character of the piece to be moved. None if
 *     piece is pawn. Following common character set is used:
 *       K - king
 *       Q - queen
 *       R - rook
 *       B - bishop
 *       N - knight
 * (2) Optional hint of the square of departure (file, rank or
 *     both). Required only if move would be ambiguous otherwise.
 * (3) Character x if and only if this is capturing move.
 * (4) Target square.
 * (5) Used only if pawn is to be promoted. Character = followed
 *     by character of an officer.
 * (6) Character + or # used to claim check or mate accordingly.
 * (7) Optional ! or ? characters are silently discarded.
 *
 * Castlings 0-0 (kingside) and 0-0-0 (queenside) are written
 * with zeros.
 *
 * {@link http://en.wikipedia.org/wiki/Algebraic_chess_notation}
 *
 * @see Chess
 *
 * @author Tuomas Starck
 */
class Move {
    public final int
            KINGSIDE = 1,
            QUEENSIDE = 2;

    private final String
        castlingre = "0-0(-0)?([#+])?",
        re = "([NBRQK])?([1-8a-h]{1,2})?(x)?([a-h][1-8])(=([NBRQ]))?([#+])?[!?]*";
            /*  1:piece  2:from          3:x 4:to        6:promo     7:act */

    private Matcher move;
    private Matcher castlemove;

    private Side side;
    private Coord to;
    private Type piece;
    private Type promote;
    private String from;
    private int castling;
    private boolean capture;
    private boolean check;
    private boolean mate;

    public Move(String str, Side turn) throws MoveException {
        move = Pattern.compile(re).matcher(str);
        castlemove = Pattern.compile(castlingre).matcher(str);

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
            // 1: Piece which is to be moved
            piece = resolvePiece(move.group(1), side);

            // 2: From (square hint)
            if (move.group(2) != null) {
                from = move.group(2);
            }

            // 3: Capture indicator
            if (move.group(3) != null) {
                capture = true;
            }

            // 4: To (target square)
            to = new Coord(move.group(4));

            // 6: Officer to which pawn is to be promoted
            if (move.group(6) != null) {
                promote = resolvePiece(move.group(6), side);
            }

            // 7: Check or mate status
            parseCheckMate(move.group(7));
        }
        else if (castlemove.matches()) {
            // Not strictly required, but helps to avoid NPE's
            piece = resolvePiece("k", side);

            // To which side to castle
            castling = (castlemove.group(1) == null)? KINGSIDE: QUEENSIDE;

            // Check or mate status
            parseCheckMate(castlemove.group(2));
        }
        else {
            throw new MoveException("Incomprehensible command");
        }
    }

    private Type resolvePiece(String input, Side side) {
        String str = (input == null)? "p": input;

        if (side == Side.w) {
            return Type.valueOf(str.toUpperCase());
        }
        else /* side == Side.b */ {
            return Type.valueOf(str.toLowerCase());
        }
    }

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

    public int odds(Piece piece) {
        String loc = piece.toString();

        if (loc.equals(from)) return 2;

        if (loc.indexOf(from) != -1) return 1;

        return 0;
    }

    public Side side() {
        return side;
    }

    public Coord to() {
        return to;
    }

    public Type piece() {
        return piece;
    }

    public Type promotion() {
        return promote;
    }

    public String from() {
        return from;
    }

    public int castling() {
        return castling;
    }

    public boolean isCastling() {
        return (castling != 0);
    }

    public boolean isCapturing() {
        return capture;
    }

    public boolean isChecking() {
        return check;
    }

    public boolean isMating() {
        return mate;
    }
}
