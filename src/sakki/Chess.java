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
        Move move = null;

        try {
            move = new Move(algebraic, turn);
        }
        catch (IllegalArgumentException fixme) {}

        if (board.move(move, turn)) {
            if (turn == Turn.white) {
                turn = Turn.black;
            }
            else {
                turn = Turn.white;
                fullmove++;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("\n %s %s %d %d\n%s\n",
            board.toFen(), turn.toString(), halfmove, fullmove,
            board.toString());
    }
}