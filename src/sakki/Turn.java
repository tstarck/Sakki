/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

/**
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