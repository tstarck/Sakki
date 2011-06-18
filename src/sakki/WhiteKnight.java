package sakki;

/**
 * White knight.
 *
 * @author Tuomas Starck
 */
class WhiteKnight extends Piece {
    public WhiteKnight(Coord birthplace) {
        super(Type.N, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();

        for (Coord tmp : loc.knightsCoords()) {
            if (!markIfMovable(tmp, status)) {
                markIfCapturable(tmp, status);
            }
        }
    }
}
