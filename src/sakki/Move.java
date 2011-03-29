/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tuomas Starck
 */
class Move {
    //                      1:piece   2:from  3:x 4:to          6:prom    7:act
    private String regex = "([NBRQK])?([a-h])?(x)?([a-h][1-8])(=([NBRQ]))?([#+])?";
    private Pattern ptrn;
    private Matcher match;

    private Piece piece;
    private Piece promote;
    private Coord from;
    private Coord to;

    private boolean kingside;
    private boolean queenside;
    private boolean capture;
    private boolean check;
    private boolean mate;

    public Move(String str, Turn turn) {
        piece = null;
        promote = null;
        from = null;
        to = null;

        kingside = false;
        queenside = false;
        capture = false;
        check = false;
        mate = false;

        ptrn = Pattern.compile(regex);
        match = ptrn.matcher(str);

        if (match.matches()) {
            // 1: Piece which is to be moved
            piece = resolvePiece(match.group(1), turn);

            // 2: From
            System.out.println("From: " + match.group(2));

            // 3: Capture indicator
            if (match.group(3) != null) {
                capture = true;
            }

            // 4: To
            to = new Coord(match.group(4));

            // 6: Officer to which pawn is to be promoted
            if (match.group(6) != null) {
                promote = resolvePiece(match.group(6), turn);
            }

            // 7: FIXME
            if (match.groupCount() > 6) {
                System.out.println("Xtra: " + match.group(7));
            }
        }
        else if (str.matches("0-0")) {
            kingside = true;
        }
        else if (str.matches("0-0-0")) {
            queenside = true;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private Piece resolvePiece(String input, Turn turn) {
        String str = (input == null)? "p": input;

        System.out.println("DEBUG::" + str);

        if (turn == Turn.white) {
            return Piece.valueOf(str.toUpperCase());
        } else {
            return Piece.valueOf(str.toLowerCase());
        }
    }

    public Piece piece() {
        return piece;
    }

    public Piece getPromotion() {
        return promote;
    }

    public Coord comeFrom() {
        return from;
    }

    public Coord goTo() {
        return to;
    }

    public boolean castlingKingside() {
        return kingside;
    }

    public boolean castlingQueenside() {
        return queenside;
    }

    public boolean claimCapture() {
        return capture;
    }

    public boolean claimCheck() {
        return check;
    }

    public boolean claimMate() {
        return mate;
    }

    @Override
    public String toString() {
        return "not implemented";
    }
}