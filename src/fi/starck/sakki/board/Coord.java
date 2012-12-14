package fi.starck.sakki.board;

import java.util.ArrayList;

/**
 * Translates game board coordinate between different notations.
 *
 * SAN uses letter-digit notation for file and rank. This program
 * uses internally two dimensional arrays with integer indices
 * with origin at SAN square a8.
 *
 * @author Tuomas Starck
 *
 * @see Chess
 */
class Coord {
    int file;
    int rank;
    private String readable;

    private static final String regex = "[a-h][1-8]";
    private static final String[] lookupTable = {"a", "b", "c", "d", "e", "f", "g", "h"};

    /**
     * Create new coordinate object from two indices.
     *
     * @param f File index.
     * @param r Rank index.
     *
     * @throws Exception If indices are out of bounds.
     */
    public Coord(int f, int r) throws Exception {
        file = verify(f);
        rank = verify(r);
        readable = lookupTable[f] + String.valueOf(8-r);
    }

    /**
     * Create new coordinate from SAN square string.
     *
     * @param loc SAN square string.
     *
     * @throws Exception If input is invalid.
     */
    public Coord(String loc) throws Exception {
        parse(loc);
    }

    /**
     * Unsafe create coordinate. This constructor only throws
     * unchecked exceptions and is meant to be used internally
     * only.
     *
     * @param loc SAN square string.
     * @param any Ignored.
     */
    Coord(String loc, boolean any) {
        try {
            parse(loc);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param x Index.
     *
     * @return Valid index.
     *
     * @throws Exception If index is not valid.
     */
    private int verify(int x) throws Exception {
        if (x < 0 || 8 <= x) {
            throw new Exception("Out of bounds value");
        }
        return x;
    }

    /**
     * Parse SAN square string and initialize this object.
     *
     * @param loc SAN square string.
     *
     * @throws Exception If input is invalid.
     */
    private void parse(String loc) throws Exception {
        if (loc == null) {
            throw new Exception("Null pointer");
        }

        if (loc.matches(regex)) {
            readable = loc;

            String rawFile = loc.substring(0, 1);
            int    rawRank = Character.getNumericValue(loc.charAt(1));

            for (int i=0; i<8; i++) {
                if (rawFile.equals(lookupTable[i])) {
                    file = i;
                    break;
                }
            }

            rank = 8 - rawRank;
        }
        else {
            throw new Exception("Parse error");
        }
    }

    /**
     * Create a new coordinate relative to current.
     *
     * @param fDelta Distance on file.
     * @param rDelta Distance on rank.
     *
     * @return New coordinate.
     */
    private Coord relativeCoord(int fDelta, int rDelta) {
        Coord ret = null;

        try {
            ret = new Coord(file+fDelta, rank+rDelta);
        }
        catch (Exception pass) {}

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

    /**
     * Return all coordinates, relative to current, which
     * a knight can reach with single move.
     *
     * @return List of coordinates.
     */
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

    /**
     * @param that Some coordinate.
     *
     * @return True if this and that coordinates match.
     */
    public boolean equals(Coord that) {
        if (that == null) return false;
        return (this.file == that.file && this.rank == that.rank);
    }

    /**
     * @param str Some coordinate string.
     *
     * @return True if this and that coordinates match.
     */
    public boolean equals(String str) {
        if (str == null) return false;
        return (readable.equals(str));
    }

    /**
     * @return SAN coordinate string.
     */
    @Override
    public String toString() {
        return readable;
    }
}
