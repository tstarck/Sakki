package fi.starck.sakki.board;

/**
 * White rook.
 *
 * @author Tuomas Starck
 */
class WhiteRook extends Piece {
    public WhiteRook(Coord birthplace) {
        super(Type.R, birthplace);
        if (birthplace.equals("h1")) castlingEffect = "K";
        if (birthplace.equals("a1")) castlingEffect = "Q";
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();
        markStraight(status);
    }
}
