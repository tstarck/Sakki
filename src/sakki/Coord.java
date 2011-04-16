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

    public Coord(String loc) {
        if (loc == null) {
            throw new NullPointerException();
        }

        if (loc.matches(regex)) {
            readable = loc;

            String fi = String.valueOf(loc.charAt(0));
            int ri = Character.getNumericValue(loc.charAt(1));

            for (int i=0; i<8; i++) {
                if (fi.equals(lookupTable[i])) {
                    file = verify(i);
                }
            }

            rank = 7 - verify(--ri);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public Coord(int f, int r) {
        file = verify(f);
        rank = verify(r);
        readable = lookupTable[f] + String.valueOf(8-r);
    }

    private int verify(int x) {
        if (x < 0 || 8 <= x) {
            throw new IllegalArgumentException();
        }
        return x;
    }

    @Override
    public String toString() {
        return readable;
    }
}