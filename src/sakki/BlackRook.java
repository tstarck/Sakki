/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Black rook.
 *
 * @author Tuomas Starck
 */
class BlackRook extends Piece {
    public BlackRook(Coord birthplace) {
        super(Type.r, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markStraight(status);
    }

    @Override
    public Rebound move(Move move) {
        Rebound rebound = new Rebound();

        if (loc.equals("a8")) rebound.preventCastling("q");
        if (loc.equals("h8")) rebound.preventCastling("k");

        loc = move.to();

        return rebound;
    }
}
