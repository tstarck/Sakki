/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White knight.
 *
 * @author Tuomas Starck
 */
class WhiteKnight extends Piece {
    public WhiteKnight(Coord birthplace) {
        super(Type.N, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();

        for (Coord tmp : loc.knightsCoords()) {
            if (!markIfMoveable(tmp, status)) {
                markIfCapturable(tmp, status);
            }
        }
    }
}
