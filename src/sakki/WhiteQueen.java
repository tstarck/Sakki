/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White queen.
 *
 * @author Tuomas Starck
 */
class WhiteQueen extends Piece {
    public WhiteQueen(Coord birthplace) {
        super(Type.Q, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markStraight(status);
        markDiagonal(status);
    }
}
