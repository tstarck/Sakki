package sakki;

/**
 * White bishop.
 *
 * @author Tuomas Starck
 */
class WhiteBishop extends Piece {
    public WhiteBishop(Coord birthplace) {
        super(Type.B, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markDiagonal(status);
    }
}
