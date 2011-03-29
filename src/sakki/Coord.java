/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

/**
 *
 * @author Tuomas Starck
 */
class Coord {
    public int file;
    public int rank;
    private String readable;

    private static final String regex = "[a-h][1-8]";
    private static final String[] lookupTable = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Coord(String str) {
        if (str == null) {
            throw new NullPointerException();
        }

        if (str.matches(regex)) {
            readable = str;

            String fi = String.valueOf(str.charAt(0));
            int ri = Character.getNumericValue(str.charAt(1));

            for (int i=0; i<8; i++) {
                if (fi.equals(lookupTable[i])) {
                    file = i;
                }
            }

            rank = verify(--ri);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public Coord(int f, int r) {
        file = verify(f);
        rank = verify(r);
        readable = lookupTable[f] + String.valueOf(++r);
    }

    private int verify(int x) {
        if (x < 0 || 8 <= x) {
            throw new IllegalArgumentException();
        }
        return x;
    }

    /*
    public boolean equals(Coord that) {
        if (this == that) {
            return true;
        }
        if (this.file == that.file &&
            this.rank == that.rank &&
            this.toString().equals(that.toString())) {
            return true;
        }
        return false;
    }
    */

    @Override
    public String toString() {
        return readable;
    }
}