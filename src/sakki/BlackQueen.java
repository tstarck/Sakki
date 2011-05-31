package sakki;

/**
 * Black queen.
 *
 * @author Tuomas Starck
 */
class BlackQueen extends Piece {
    public BlackQueen(Coord birthplace) {
        super(Type.q, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markStraight(status);
        markDiagonal(status);
    }
}
