/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 *
 * @author Tuomas Starck
 */
public enum Piece {
    e {
        @Override
        public String toString() {
            return ".";
        }
    },

    p, P, b, B, n, N, r, R, q, Q, k, K
}