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
    public WhiteKnight(String birthplace) {
        super(Type.n, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        for (Coord tmp : loc.knightsCoords()) {
            if (!moveable(tmp, status)) {
                capturable(tmp, status);
            }
        }
    }
}