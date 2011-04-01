/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * List of pieces available in the game of chess.
 * 
 * Pieces are named according to the common convention amongst English-speaking players. Capital letters indicate white pieces and vice versa (in compliance with Forsyth–Edwards Notation).
 *
 * @see <a href="http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Forsyth–Edwards Notation</a>
 *
 * @author Tuomas Starck
 */
public enum Piece {
    /**
     * This value marks the absence of a piece.
     */
    e {
        @Override
        public String toString() {
            return ".";
        }
    },

    p, P, b, B, n, N, r, R, q, Q, k, K
}