/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 *
 * @author tljstarc
 */
class Peli {

    Board board;

    public Peli() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Peli(String fen) {
        this.board = new Board(fen);
    }

    void move(String move) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String toString() {
        return this.board.toString();
    }
}
