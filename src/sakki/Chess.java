/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 *
 * @author Tuomas Starck
 */
class Chess {
    // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    private Board board;
    private Turn next;
    private String castling;    // FIXME
    private boolean enpassant;  // FIXME
    private int halfmove;
    private int fullmove;

    public Chess() {
        board = new Board();
        next = Turn.white;
        castling = "KQkq";
        enpassant = false;
        halfmove = 0;
        fullmove = 1;
    }

    void move(String str) {
        Move algebraic = new Move(str, next);

        if (board.move(algebraic)) {
            // Succéss!
        }
        else {
            // Illegal move :-|
        }
    }

    public String toFen() {     // FIXME
        return String.format("%s %s %d %d",
            board.toFen(), next.toString(), halfmove, fullmove);
    }

    @Override
    public String toString() {
        return board.toString();
    }
}