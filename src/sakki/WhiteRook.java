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
}
