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

    public Piece(Type type, String birthplace) {
        me = type;
        loc = new Coord(birthplace);
        view = new Type[8][8];

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }

        view[loc.rank][loc.file] = me;

    }

    public Type what() {
        return me;
    }

    public Coord where() {
        return loc;
    }

    public boolean move(Move move) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void update(Type[][] status) {
        throw new UnsupportedOperationException("Not implemented");
    }

    protected boolean moveable(Coord target, Type[][] status) {
        if (target == null) return false;

        System.out.println("Testing moveability at [" + target + "]");

        if (status[target.rank][target.file] == Type.empty) {
            view[target.rank][target.file] = Type.moveable;
            return true;
        }

        return false;
    }

    protected void capturable(Coord target, Type[][] status) {
        if (target == null) return;

        System.out.println("Testing capturable at [" + target + "]");

        if (me.enemy(status[target.rank][target.file])) {
            view[target.rank][target.file] = Type.capturable;
        }
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