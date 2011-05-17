/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Tracking conditions for castling.
 *
 * @author tuomas
 */
public class Castle {
    private final String[] labels = {"K", "Q", "k", "q"};

    private final String[][] freeSquares = {
        {"f1", "g1"}, {"b1", "c1", "d1"},
        {"f8", "g8"}, {"b8", "c8", "d8"}
    };

    private boolean[] canCastle;

    public Castle() {
        this("KQkq");
    }

    public Castle(String str) {
        canCastle = new boolean[4];

        for (char c : str.toCharArray()) {
            switch (c) {
                case 'K':
                    canCastle[0] = true; break;
                case 'Q':
                    canCastle[1] = true; break;
                case 'k':
                    canCastle[2] = true; break;
                case 'q':
                    canCastle[3] = true; break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

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

    public void drop(char[] array) {
        for (int i=0; i<labels.length; i++) {
            for (char c : array) {
                if (labels[i].equals(c)) {
                    canCastle[i] = false;
                }
            }
        }
    }

    public void done(Turn turn) {
        if (Turn.w == turn) {
            canCastle[0] = false;
            canCastle[1] = false;
        }
        else {
            canCastle[2] = false;
            canCastle[3] = false;
        }
    }

    @Override
    public String toString() {
        String str = "";

        for (int i=0; i<canCastle.length; i++) {
            if (canCastle[i]) {
                str += labels[i];
            }
        }

        return str;
    }
}