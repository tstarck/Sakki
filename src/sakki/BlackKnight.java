package sakki;

/**
 * Black knight.
 *
 * @author Tuomas Starck
 */
class BlackKnight extends Piece {
    public BlackKnight(Coord birthplace) {
        super(Type.n, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord ep) {
        reset();

        for (Coord tmp : loc.knightsCoords()) {
            if (!markIfMoveable(tmp, status)) {
                markIfCapturable(tmp, status);
            }
        }
    }
}
