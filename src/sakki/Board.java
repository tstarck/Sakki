/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 *
 * @author tljstarc
 */
class Board {

    private Piece[][] board;

    private final Piece[][] boardAtBeginning = {
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
        this.board = this.boardAtBeginning;
    }

    public Board(String fen) {
        this.board = this.boardAtBeginning;
    }

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
