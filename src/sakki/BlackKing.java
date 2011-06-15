package sakki;

/**
 * Black king.
 *
 * @author Tuomas Starck
 */
class BlackKing extends Piece {
    public BlackKing(Coord birthplace) {
        super(Type.k, birthplace);
        castlingEffect = "kq";
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markAdjacent(status);
    }

    @Override
    public Rebound move(Coord target) {
        Rebound rebound = new Rebound();
        rebound.disableCastling(castlingEffect);
        loc = target;
        return rebound;
    }
}
