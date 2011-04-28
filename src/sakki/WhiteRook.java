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
        int i;

        for (i=1; i<8; i++) {
            if (!moveable(loc.north(i), status)) {
                break;
            }
        }

        capturable(loc.north(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.east(i), status)) {
                break;
            }
        }

        capturable(loc.east(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.south(i), status)) {
                break;
            }
        }

        capturable(loc.south(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.west(i), status)) {
                break;
            }
        }

        capturable(loc.west(i), status);

        System.out.println(this);
    }
}