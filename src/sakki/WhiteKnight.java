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
    public boolean update(Type[][] status, Coord ep) {
        reset();

        boolean checked = false;

        for (Coord tmp : loc.knightsCoords()) {
            if (!markIfMoveable(tmp, status)) {
                if (markIfCapturable(tmp, status)) {
                    checked = true;
                }
            }
        }

        return checked;
    }
}
