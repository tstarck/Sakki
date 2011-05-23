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
    private final int PROMOTION_RANK = 0;

    public WhitePawn(Coord birthplace) {
        super(Type.P, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();

        markIfMoveable(loc.north(1), status);

        if (loc.rank == INITIAL_RANK) {
            markIfMoveable(loc.north(2), status);
        }

        markIfCapturable(loc.northeast(1), status);
        markIfCapturable(loc.northwest(1), status);
    }

    @Override
    public Rebound move(Move move) {
        Coord target = move.to();
        Rebound rebound = new Rebound();

        if (target.rank == PROMOTION_RANK) {
            rebound.promotionAvailable();
        }

        if (loc.rank == INITIAL_RANK && target.rank == (INITIAL_RANK-2)) {
            rebound.setEnpassant(loc.north(1));
        }

        loc = target;

        return rebound;
    }
}