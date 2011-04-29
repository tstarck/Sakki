/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black bishop.
 *
 * @author Tuomas Starck
 */
class BlackBishop extends Piece {
    public BlackBishop(String birthplace) {
        super(Type.B, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        markDiagonal(status);
    }
}