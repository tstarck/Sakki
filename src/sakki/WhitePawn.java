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

        markIfMoveable(loc.north(1), status);

        if (loc.rank == INITIAL_RANK) {
            markIfMoveable(loc.north(2), status);
        }

        markIfCapturable(loc.northeast(1), status);
        markIfCapturable(loc.northwest(1), status);
    }

    @Override
    public Move move(Move move) {
        Coord target = move.to();

        /* Pitää siirtää reboundiin tää soodi!
        if (loc.rank == INITIAL_RANK && target.rank == (INITIAL_RANK - 2)) {
            move.markEnpassant(loc.north(1));
        }
        */

        loc = move.to();

        return move;
    }
}