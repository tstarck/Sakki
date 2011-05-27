/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Abstraction and common operations for various Chess pieces.
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

        reset();
    }

    protected final void reset() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }

        view[loc.rank][loc.file] = me;
    }

    public Type type() {
        return me;
    }

    public Coord location() {
        return loc;
    }

    public void update(Type[][] status, Coord enpassant) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Rebound move(Move move) {
        loc = move.to();
        return new Rebound();
    }

    public Rebound move(Coord target) {
        loc = target;
        return null;
    }

    public boolean canMove(Coord target) {
        return (view[target.rank][target.file] == Type.moveable);
    }

    public boolean canCapture(Coord target) {
        return (view[target.rank][target.file] == Type.capturable);
    }

    protected boolean markIfMoveable(Coord target, Type[][] status) {
        if (target == null) return false;

        if (status[target.rank][target.file] == Type.empty) {
            view[target.rank][target.file] = Type.moveable;
            return true;
        }

        return false;
    }

    protected void markIfCapturable(Coord sqr, Type[][] status) {
        if (sqr == null) return;

        Type target = status[sqr.rank][sqr.file];

        if (me.isEnemy(target)) {
            if (target.name().toLowerCase().equals("k")) {
                view[sqr.rank][sqr.file] = Type.checked;
            }
            else {
                view[sqr.rank][sqr.file] = Type.capturable;
            }
        }
    }

    protected void markAdjacent(Type[][] status) {
        if (!markIfMoveable(loc.north(1), status)) {
            markIfCapturable(loc.north(1), status);
        }

        if (!markIfMoveable(loc.northeast(1), status)) {
            markIfCapturable(loc.northeast(1), status);
        }

        if (!markIfMoveable(loc.east(1), status)) {
            markIfCapturable(loc.east(1), status);
        }

        if (!markIfMoveable(loc.southeast(1), status)) {
            markIfCapturable(loc.southeast(1), status);
        }

        if (!markIfMoveable(loc.south(1), status)) {
            markIfCapturable(loc.south(1), status);
        }

        if (!markIfMoveable(loc.southwest(1), status)) {
            markIfCapturable(loc.southwest(1), status);
        }

        if (!markIfMoveable(loc.west(1), status)) {
            markIfCapturable(loc.west(1), status);
        }

        if (!markIfMoveable(loc.northwest(1), status)) {
            markIfCapturable(loc.northwest(1), status);
        }
    }

    protected void markStraight(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.north(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.north(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.east(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.east(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.south(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.south(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.west(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.west(i), status);
    }

    protected void markDiagonal(Type[][] status) {
        int i;

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.northeast(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.northeast(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.southeast(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.southeast(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.southwest(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.southwest(i), status);

        for (i=1; i<8; i++) {
            if (!markIfMoveable(loc.northwest(i), status)) {
                break;
            }
        }

        markIfCapturable(loc.northwest(i), status);
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