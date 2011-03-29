/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
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

    public boolean move(Move algebraic) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String toFen() {
        return "fen";
    }

    @Override
    public String toString() {
        String str = "";

        for (Piece[] rank : this.board) {
            str += "\n";
            for (Piece file : rank) {
                str += " " + file;
            }
        }

        return str;
    }
}