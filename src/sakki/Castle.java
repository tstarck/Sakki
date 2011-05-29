/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.HashSet;

/**
 * Tracking conditions for castling.
 *
 * @author Tuomas Starck
 */
class Castle {
    private final String valid = "KQkq";

    private final Coord[] kingsSquares = {
        new Coord("e1"), new Coord("e8")
    };

    private final Coord[] kingsTargets = {
        new Coord("g1"), new Coord("c1"),
        new Coord("g8"), new Coord("c8")
    };

    private final Coord[] rooksSquares = {
        new Coord("h1"), new Coord("a1"),
        new Coord("h8"), new Coord("a8")
    };

    private final Coord[] rooksTargets = {
        new Coord("f1"), new Coord("d1"),
        new Coord("f8"), new Coord("d8")
    };

    private final Coord[][] freeSquares = {
        {new Coord("f1"), new Coord("g1")},
        {new Coord("b1"), new Coord("c1"), new Coord("d1")},
        {new Coord("f8"), new Coord("g8")},
        {new Coord("b8"), new Coord("c8"), new Coord("d8")}
    };

    private final Coord[][] safeSquares = {
        {new Coord("e1"), new Coord("f1"), new Coord("g1")},
        {new Coord("c1"), new Coord("d1"), new Coord("e1")},
        {new Coord("e8"), new Coord("f8"), new Coord("g8")},
        {new Coord("c8"), new Coord("d8"), new Coord("e8")}
    };

    private HashSet<Character> castling;

    public Castle() {
        this("KQkq");
    }

    public Castle(String str) {
        castling = new HashSet<Character>();
        enable(str);
    }

    private boolean isValid(Character c) {
        return (valid.indexOf(c) != -1);
    }

    public final void enable(String str) {
        for (Character c : str.toCharArray()) {
            if (isValid(c)) {
                castling.add(c);
            }
        }
    }

    public void disable(String str) {
        if (str == null) return;

        for (Character c : str.toCharArray()) {
            castling.remove(c);
        }
    }

    public int index(Move move) {
        if (move.side() == Side.w) {
            return (move.castling() == move.KINGSIDE)? 0: 1;
        }
        else /* Side.b */ {
            return (move.castling() == move.KINGSIDE)? 2: 3;
        }
    }

    public boolean isAllowed(Move move) {
        return castling.contains(valid.charAt(index(move)));
    }

    public Coord getKingsSqr(Move move) {
        int index = (move.side() == Side.w)? 0: 1;
        return kingsSquares[index];
    }

    public Coord getKingsTarget(Move move) {
        return kingsTargets[index(move)];
    }

    public Coord getRooksSqr(Move move) {
        return rooksSquares[index(move)];
    }

    public Coord getRooksTarget(Move move) {
        return rooksTargets[index(move)];
    }

    public Coord[] getFreeSqrs(Move move) {
        return freeSquares[index(move)];
    }

    public Coord[] getSafeSqrs(Move move) {
        return safeSquares[index(move)];
    }

    @Override
    public String toString() {
        String str = "";

        for (Character c : valid.toCharArray()) {
            if (castling.contains(c)) {
                str += c;
            }
        }

        return (str.equals(""))? "-": str;
    }
}