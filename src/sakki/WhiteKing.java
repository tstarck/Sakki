/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White king.
 *
 * @author Tuomas Starck
 */
class WhiteKing extends Piece {
    public WhiteKing(Coord birthplace) {
        super(Type.K, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();
        markAdjacent(status);
    }
}
