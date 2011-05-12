/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Abstraction for various Chess pieces.
 *
 * @author Tuomas Starck
 */
abstract class Piece {
    protected Type me;
    protected Coord loc;
    protected Type[][] view;

    public Piece(Type type, Coord birthplace) {
        if (type == null || birthplace == null) {
            throw new IllegalArgumentException();
        }

        me = type;
        loc = birthplace;
        view = new Type[8][8];

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }
    }

    public Type who() {
        return me;
    }

    public Coord where() {
        return loc;
    }

    protected void reset() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }
    }

    public void update(Type[][] status) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Move move(Move move) {
        loc = move.to();
        return move;
    }

    public boolean canGoto(Coord target) {
        if (view[target.rank][target.file] == Type.moveable ||
            view[target.rank][target.file] == Type.capturable) {
            return true;
        }

        return false;
    }

    protected boolean moveable(Coord target, Type[][] status) {
        if (target == null) return false;

        if (status[target.rank][target.file] == Type.empty) {
            view[target.rank][target.file] = Type.moveable;
            return true;
        }

        return false;
    }

    protected void capturable(Coord target, Type[][] status) {
        if (target == null) return;

        if (me.isEnemy(status[target.rank][target.file])) {
            view[target.rank][target.file] = Type.capturable;
        }
    }

    protected void markAdjacent(Type[][] status) {
        if (!moveable(loc.north(1), status)) {
            capturable(loc.north(1), status);
        }

        if (!moveable(loc.northeast(1), status)) {
            capturable(loc.northeast(1), status);
        }

        if (!moveable(loc.east(1), status)) {
            capturable(loc.east(1), status);
        }

        if (!moveable(loc.southeast(1), status)) {
            capturable(loc.southeast(1), status);
        }

        if (!moveable(loc.south(1), status)) {
            capturable(loc.south(1), status);
        }

        if (!moveable(loc.southwest(1), status)) {
            capturable(loc.southwest(1), status);
        }

        if (!moveable(loc.west(1), status)) {
            capturable(loc.west(1), status);
        }

        if (!moveable(loc.northwest(1), status)) {
            capturable(loc.northwest(1), status);
        }
    }

    protected void markStraight(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!moveable(loc.north(i), status)) {
                break;
            }
        }

        capturable(loc.north(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.east(i), status)) {
                break;
            }
        }

        capturable(loc.east(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.south(i), status)) {
                break;
            }
        }

        capturable(loc.south(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.west(i), status)) {
                break;
            }
        }

        capturable(loc.west(i), status);
    }

    protected void markDiagonal(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!moveable(loc.northeast(i), status)) {
                break;
            }
        }

        capturable(loc.northeast(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.southeast(i), status)) {
                break;
            }
        }

        capturable(loc.southeast(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.southwest(i), status)) {
                break;
            }
        }

        capturable(loc.southwest(i), status);

        for (i=1; i<8; i++) {
            if (!moveable(loc.northwest(i), status)) {
                break;
            }
        }

        capturable(loc.northwest(i), status);
    }

    @Override
    public String toString() {
        String str = "";

        for (Type[] rank : view) {
            str += "\n";
            for (Type file : rank) {
                str += " " + file;
            }
        }

        return str;
    }
}