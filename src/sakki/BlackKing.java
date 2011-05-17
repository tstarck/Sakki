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
    public BlackKing(Coord birthplace) {
        super(Type.k, birthplace);
    }

    @Override
    public void update(Type[][] status) {
        reset();
        markAdjacent(status);
    }

    @Override
    public Rebound move(Move move) {
        Rebound rebound = new Rebound();

        rebound.preventCastling("kq");

        loc = move.to();

        return rebound;
    }
}