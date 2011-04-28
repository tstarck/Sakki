/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

import java.util.ArrayList;

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

    private Coord relativeCoord(int fDelta, int rDelta) {
        Coord ret = null;

        try {
            // System.out.println("New Coords at [" +(rank+rDelta) + "," + (file+fDelta) + "]");
            ret = new Coord(file+fDelta, rank+rDelta);
        }
        catch (IllegalArgumentException iCouldntBeBothered) {}

        return ret;
    }

    public Coord north(int dist) {
        return relativeCoord(0, -1*dist);
    }

    public Coord northeast(int dist) {
        return relativeCoord(dist, -1*dist);
    }

    public Coord east(int dist) {
        return relativeCoord(dist, 0);
    }

    public Coord southeast(int dist) {
        return relativeCoord(dist, dist);
    }

    public Coord south(int dist) {
        return relativeCoord(0, dist);
    }

    public Coord southwest(int dist) {
        return relativeCoord(-1*dist, dist);
    }

    public Coord west(int dist) {
        return relativeCoord(-1*dist, 0);
    }

    public Coord northwest(int dist) {
        return relativeCoord(-1*dist, -1*dist);
    }

    public ArrayList<Coord> knightsCoords() {
        int[] x = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] y = {2, 1, -1, -2, -2, -1, 1, 2};

        ArrayList<Coord> coords = new ArrayList<Coord>();

        for (int i=0; i<x.length; i++) {
            Coord co = relativeCoord(x[i], y[i]);

            if (co != null) {
                coords.add(co);
            }
        }

        return coords;
    }

    @Override
    public String toString() {
        return readable;
    }
}