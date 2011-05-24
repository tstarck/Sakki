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
    public WhiteRook(Coord birthplace) {
        super(Type.R, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markStraight(status);
    }

    @Override
    public Rebound move(Move move) {
        Rebound rebound = new Rebound();

        if (loc.equals("a1")) rebound.preventCastling("Q");
        if (loc.equals("h1")) rebound.preventCastling("K");

        loc = move.to();

        return rebound;
    }
}
