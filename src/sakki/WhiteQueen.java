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
    public WhiteQueen(String birthplace) {
        super(Type.q, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        markStraight(status);
        markDiagonal(status);
    }
}