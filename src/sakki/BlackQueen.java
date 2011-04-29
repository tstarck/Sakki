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
    public BlackQueen(String birthplace) {
        super(Type.Q, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        markStraight(status);
        markDiagonal(status);
    }
}