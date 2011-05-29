/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White king.
 *
 * @author Tuomas Starck
 */
class WhiteKing extends Piece {
    public WhiteKing(Coord birthplace) {
        super(Type.K, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markAdjacent(status);
    }

    @Override
    public Rebound move(Coord target) {
        Rebound rebound = new Rebound();
        rebound.disableCastling(castleffect);
        loc = target;
        return rebound;
    }
}