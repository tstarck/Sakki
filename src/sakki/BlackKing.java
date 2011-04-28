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

    public void update(Type[][] status) {
        if (!moveable(loc.north(1), status)) {
            capturable(loc.north(1), status);
        }

        if (!moveable(loc.northeast(1), status)) {
            capturable(loc.northeast(1), status);
        }

        if (!moveable(loc.east(1), status)) {
            capturable(loc.east(1), status);
        }

        if (!moveable(loc.southeast(1), status)) {
            capturable(loc.southeast(1), status);
        }

        if (!moveable(loc.south(1), status)) {
            capturable(loc.south(1), status);
        }

        if (!moveable(loc.southwest(1), status)) {
            capturable(loc.southwest(1), status);
        }

        if (!moveable(loc.west(1), status)) {
            capturable(loc.west(1), status);
        }

        if (!moveable(loc.northwest(1), status)) {
            capturable(loc.northwest(1), status);
        }

        System.out.println(this);
    }
}