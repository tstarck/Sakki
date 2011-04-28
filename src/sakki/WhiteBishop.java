/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White bishop.
 *
 * @author Tuomas Starck
 */
class WhiteBishop extends Piece {
    public WhiteBishop(String birthplace) {
        super(Type.b, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!moveable(loc.northeast(i), status)) {
                break;
            }
        }

        capturable(loc.northeast(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.southeast(i), status)) {
                break;
            }
        }

        capturable(loc.southeast(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.southwest(i), status)) {
                break;
            }
        }

        capturable(loc.southwest(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.northwest(i), status)) {
                break;
            }
        }

        capturable(loc.northwest(i), status);

        System.out.println(this);
    }
}
