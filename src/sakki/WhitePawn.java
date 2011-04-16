/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White pawn.
 *
 * @author Tuomas Starck
 */
public class WhitePawn extends Piece {
    public WhitePawn(String birthplace) {
        me = Type.p;
        loc = new Coord(birthplace);
        view = new Type[8][8];

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                view[i][j] = Type.empty;
            }
        }

        view[loc.rank][loc.file] = me;
    }

    @Override
    public boolean move(Move move, Turn turn) {
        return false;
    }

    @Override
    public void update(Type[][] status) {
        if (loc.rank > 0) {
            view[loc.rank-1][loc.file] = Type.moveable;
        }

        if (loc.rank == 6) {
            view[4][loc.file] = Type.moveable;
        }

        System.out.println(this);
    }
}