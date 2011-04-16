/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * White knight.
 *
 * @author Tuomas Starck
 */
class WhiteKnight extends Piece {
    public WhiteKnight(String birthplace) {
        me = Type.n;
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
    public void update(Type[][] status) {
        System.out.println(this);
    }
}