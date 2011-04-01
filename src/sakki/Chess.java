/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * An implementation of Chess for two players.
 *
 * @author Tuomas Starck
 */
class Chess {
    // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    private Board board;
    private Turn turn;
    private String castling;    // FIXME
    private boolean enpassant;  // FIXME
    private int halfmove;
    private int fullmove;

    public Chess() {
        board = new Board();
        turn = Turn.white;
        castling = "KQkq";
        enpassant = false;
        halfmove = 0;
        fullmove = 1;
    }

    void move(String algebraic) {
        Move move = new Move(algebraic, turn);

        if (board.move(move, turn)) {
            System.out.println("Move done.");
        }
        else {
            System.out.println(" *** Move failed *** :-(");
            // Illegal move :-|
        }

        System.out.println("FEN :: " + board.toFen());
        System.out.println(board);
    }

    public String toFen() {     // FIXME
        return String.format("%s %s %d %d",
            board.toFen(), turn.toString(), halfmove, fullmove);
    }

    @Override
    public String toString() {
        return board.toString();
    }
}