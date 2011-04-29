/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black king.
 *
 * @author Tuomas Starck
 */
class BlackKing extends Piece {
    public BlackKing(String birthplace) {
        super(Type.K, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        markAdjacent(status);
    }
}