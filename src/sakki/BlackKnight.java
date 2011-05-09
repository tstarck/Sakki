/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black knight.
 *
 * @author Tuomas Starck
 */
class BlackKnight extends Piece {
    public BlackKnight(Coord birthplace) {
        super(Type.n, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();

        for (Coord tmp : loc.knightsCoords()) {
            if (!moveable(tmp, status)) {
                capturable(tmp, status);
            }
        }
    }
}
