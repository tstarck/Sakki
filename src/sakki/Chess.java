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
    private int halfmove;
    private int fullmove;

    public Chess() {
        board = new Board();
        turn = Turn.white;
        halfmove = 0;
        fullmove = 1;
    }

    void move(String algebraic) {
        Move move = new Move(algebraic, turn);

        // TODO Pitää tarkistaa, että move on varmasti kunnossa

        /*
        if (board.move(move, turn)) {
            System.out.println("Move done.");
        }
        else {
            System.out.println(" *** Move failed *** :-(");
            // Illegal move :-|
        }
        */
    }

    @Override
    public String toString() {
        return String.format("\n %s %s %d %d\n%s\n",
            board.toFen(), turn.toString(), halfmove, fullmove,
            board.toString());
    }
}