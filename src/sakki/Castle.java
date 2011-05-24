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
public class Castle {
    private final String valid = "KQkq";

    private HashSet<Character> castling;

    /*
    private final String[][] freeSquares = {
        {"f1", "g1"}, {"b1", "c1", "d1"},
        {"f8", "g8"}, {"b8", "c8", "d8"}
    };
    */

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



    /*
    private int index(Turn turn, boolean side) {
        int index = 0;
        if (!side) index++;
        if (Turn.b == turn) index += 2;
        return index;
    }

    public boolean isDoable(Turn turn, boolean side) {
        return canCastle[index(turn, side)];
    }

    public String[] requiredFree(Turn turn, boolean side) {
        return freeSquares[index(turn, side)];
    }
    */

    @Override
    public String toString() {
        String str = "";

        for (Character c : valid.toCharArray()) {
            if (castling.contains(c)) {
                str += c;
            }
        }

        return str;
    }
}