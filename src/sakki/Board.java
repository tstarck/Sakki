/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Model of a Chess board and its rules.
 *
 * Board is standard sized with eight files and ranks.
 *
 * @author Tuomas Starck
 */
class Board {

    private Piece[][] board;

    private static final Piece[][] boardAtBeginning = {
        {Piece.r, Piece.n, Piece.b, Piece.q, Piece.k, Piece.b, Piece.n, Piece.r},
        {Piece.p, Piece.p, Piece.p, Piece.p, Piece.p, Piece.p, Piece.p, Piece.p},
        {Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e},
        {Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e},
        {Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e},
        {Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e, Piece.e},
        {Piece.P, Piece.P, Piece.P, Piece.P, Piece.P, Piece.P, Piece.P, Piece.P},
        {Piece.R, Piece.N, Piece.B, Piece.Q, Piece.K, Piece.B, Piece.N, Piece.R}
    };

    public Board() {
        board = Board.boardAtBeginning;
    }

    public boolean move(Move move, Turn turn) {
        if (move == null) {
            return false;
        }
        else if (move.castlingKingside()) {
            // FIXME return castleKingside();
        }
        else if (move.castlingQueenside()) {
            // FIXME return castleQueenside();
        }
        else {
            /* About move:
             *   move.piece         Liikutettava (saatavilla)
             *   move.getPromotion  Null tai ylennys
             *   move.comeFrom      null
             *   move.goTo          Kohde (saatavilla)
             *   move.claimCapture
             *   move.claimCheck
             *   move.claimMate
             */
        }

        return false; // FIXME
    }

    public String toFen() {
        String fen = "";

        for (Piece[] rank : board) {
            for (Piece file : rank) {
                fen += file;
            }
        }

        return fen;
    }

    @Override
    public String toString() {
        String str = "";

        for (Piece[] rank : board) {
            str += "\n";
            for (Piece file : rank) {
                str += " " + file;
            }
        }

        return str;
    }
}