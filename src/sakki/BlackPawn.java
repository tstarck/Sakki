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

    public BlackPawn(Coord birthplace) {
        super(Type.p, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();

        markIfMoveable(loc.south(1), status);

        if (loc.rank == INITIAL_RANK) {
            markIfMoveable(loc.south(2), status);
        }

        markIfCapturable(loc.southeast(1), status);
        markIfCapturable(loc.southwest(1), status);
    }

    @Override
    public Rebound move(Move move) {
        Coord target = move.to();
        Rebound rebound = new Rebound();

        if (loc.rank == INITIAL_RANK && target.rank == (INITIAL_RANK+2)) {
            rebound.setEnpassant(loc.south(1));
        }

        loc = target;

        return rebound;
    }
}