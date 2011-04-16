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

    public Type what() {
        return me;
    }

    public Coord where() {
        return loc;
    }

    public boolean move(Move move, Turn turn) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void update(Type[][] status) {
        throw new UnsupportedOperationException("Not implemented");
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