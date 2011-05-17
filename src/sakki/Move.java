/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Read and parse a certain subset of <i>Algebraic chess notation</i>.
 *
 * Flavor of notation:
 *   Capture is marked with 'x'
 *   Pawn promotion prefix is '='
 *   Castling is written with zeros
 *   Check (+) and mate (#) are accepted
 *
 * @see <a href="http://en.wikipedia.org/wiki/Algebraic_chess_notation">
 * Algebraic chess notation in Wikipedia</a>
 *
 * @author Tuomas Starck
 */
class Move {
    private final int KINGSIDE = 1;
    private final int QUEENSIDE = 2;

    private final String castleregex = "0-0(-0)?([#+])?";

    private final String regex =
    /*   1:piece   2:from  3:x 4:to          6:promo   7:act  */
        "([NBRQK])?([a-h])?(x)?([a-h][1-8])(=([NBRQ]))?([#+])?";

    private Matcher move;
    private Matcher castlemove;

    private Type piece;
    private Type promote;
    private Coord from;
    private Coord to;

    private boolean capture;
    private boolean check;
    private boolean mate;
    private int castling;

    public Move(String str, Turn turn) throws MoveException {
        move = Pattern.compile(regex).matcher(str);
        castlemove = Pattern.compile(castleregex).matcher(str);

        piece = null;
        promote = null;
        from = null;
        to = null;

        capture = false;
        check = false;
        mate = false;
        castling = 0;

        if (move.matches()) {
            // 1: Piece which is to be moved
            piece = resolvePiece(move.group(1), turn);

            // 2: From
            // System.out.println("From: " + match.group(2));
            // -> null, e, null

            // 3: Capture indicator
            if (move.group(3) != null) {
                capture = true;
            }

            // 4: To
            to = new Coord(move.group(4));

            // 6: Officer to which pawn is to be promoted
            if (move.group(6) != null) {
                promote = resolvePiece(move.group(6), turn);
            }

            // 7: Check or Mate status
            parseCheckMate(move.group(2));
        }
        else if (castlemove.matches()) {
            castling = (castlemove.group(1) == null)? KINGSIDE: QUEENSIDE;

            parseCheckMate(castlemove.group(2));
        }
        else {
            throw new MoveException("Incomprehensible request");
        }
    }

    private Type resolvePiece(String input, Turn turn) {
        String str = (input == null)? "p": input;

        if (turn == Turn.w) {
            return Type.valueOf(str.toUpperCase());
        }
        else {
            return Type.valueOf(str.toLowerCase());
        }
    }

    private void parseCheckMate(String xtra) {
        if (xtra != null) {
            if (xtra.equals("+")) check = true;
            if (xtra.equals("#")) mate = true;
        }
    }

    public Type piece() {
        return piece;
    }

    public Type promotion() {
        return promote;
    }

    public Coord from() {
        return from;
    }

    public Coord to() {
        return to;
    }

    public boolean isCastling() {
        return (castling != 0);
    }

    public boolean castlingSide() {
        return (castling == KINGSIDE);
    }

    public boolean isClaimingCapture() {
        return capture;
    }

    public boolean isClaimingCheck() {
        return check;
    }

    public boolean isClaimingMate() {
        return mate;
    }
}