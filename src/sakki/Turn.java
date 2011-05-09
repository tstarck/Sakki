/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

/**
 * In the game of Chess either White or Black holds the next move.
 *
 * @author Tuomas Starck
 */
public enum Turn {
    w {
        @Override
        public String toString() {
            return "White";
        }
    },

    b {
        @Override
        public String toString() {
            return "Black";
        }
    }
}