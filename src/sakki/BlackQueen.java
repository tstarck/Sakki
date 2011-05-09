/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black queen.
 *
 * @author Tuomas Starck
 */
class BlackQueen extends Piece {
    public BlackQueen(Coord birthplace) {
        super(Type.q, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();
        markStraight(status);
        markDiagonal(status);
    }
}
