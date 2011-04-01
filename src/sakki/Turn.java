/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

/**
 * In the game of Chess either white or black hold the next move.
 *
 * @author Tuomas Starck
 */
public enum Turn {
    white {
        @Override
        public String toString() {
            return "w";
        }
    },

    black {
        @Override
        public String toString() {
            return "b";
        }
    }
}