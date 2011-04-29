/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White rook.
 *
 * @author Tuomas Starck
 */
class WhiteRook extends Piece {
    public WhiteRook(String birthplace) {
        super(Type.r, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();
        markStraight(status);
    }
}