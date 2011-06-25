package sakki;

/**
 * White pawn.
 *
 * @author Tuomas Starck
 */
class WhitePawn extends Piece {
    private final int INITIAL_RANK = 6;
    private final int PROMOTION_RANK = 0;

    public WhitePawn(Coord birthplace) {
        super(Type.P, birthplace);
    }

    @Override
    public void update(Type[][] status, Coord enpassant) {
        reset();

        if (markIfMovable(loc.north(1), status)) {
            if (loc.rank == INITIAL_RANK) {
                markIfMovable(loc.north(2), status);
            }
        }

        Coord ne = loc.northeast(1);
        Coord nw = loc.northwest(1);

        markIfCapturable(ne, status);
        markIfCapturable(nw, status);

        if (enpassant == null) return;

        if (enpassant.equals(ne)) {
            view[ne.rank][ne.file] = Type.capturable;
        }
        if (enpassant.equals(nw)) {
            view[nw.rank][nw.file] = Type.capturable;
        }
    }

    @Override
    public Rebound move(Move move) {
        Coord target = move.to();
        Rebound rebound = new Rebound();

        if (target.rank == PROMOTION_RANK) {
            rebound.promotionAvailable();
        }

        if (loc.rank == INITIAL_RANK && target.rank == (INITIAL_RANK-2)) {
            rebound.setEnpassant(loc.north(1));
        }

        loc = target;

        return rebound;
    }
}
