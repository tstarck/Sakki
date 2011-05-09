/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White pawn.
 *
 * @author Tuomas Starck
 */
public class WhitePawn extends Piece {
    private final int INITIAL_RANK = 6;

    public WhitePawn(Coord birthplace) {
        super(Type.P, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();

        moveable(loc.north(1), status);

        if (loc.rank == INITIAL_RANK) {
            moveable(loc.north(2), status);
        }

        capturable(loc.northeast(1), status);
        capturable(loc.northwest(1), status);
    }
}
