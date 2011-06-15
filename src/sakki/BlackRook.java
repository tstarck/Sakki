package sakki;

/**
 * Black rook.
 *
 * @author Tuomas Starck
 */
class BlackRook extends Piece {
    public BlackRook(Coord birthplace) {
        super(Type.r, birthplace);
        if (birthplace.equals("h8")) castlingEffect = "k";
        if (birthplace.equals("a8")) castlingEffect = "q";
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markStraight(status);
    }
}
