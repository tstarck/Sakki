/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black pawn.
 *
 * @author Tuomas Starck
 */
class BlackPawn extends Piece {
    private final int INITIAL_RANK = 1;

    public BlackPawn(String birthplace) {
        super(Type.P, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();

        moveable(loc.south(1), status);

        if (loc.rank == INITIAL_RANK) {
            moveable(loc.south(2), status);
        }

        capturable(loc.southeast(1), status);
        capturable(loc.southwest(1), status);
    }
}