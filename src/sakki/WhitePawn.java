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

    // Type me;
    // Coord loc;
    // Type[][] view;

    public WhitePawn(String birthplace) {
        super(Type.p, birthplace);
    }

    @Override
    public boolean move(Move move) {
        return false;
    }

    @Override
    public void update(Type[][] status) {
        moveable(loc.north(1), status);

        if (loc.rank == INITIAL_RANK) {
            moveable(loc.north(2), status);
        }

        capturable(loc.northeast(1), status);
        capturable(loc.northwest(1), status);
    }
}