/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black rook.
 *
 * @author Tuomas Starck
 */
class BlackRook extends Piece {
    public BlackRook(String birthplace) {
        super(Type.R, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        markStraight(status);
    }
}