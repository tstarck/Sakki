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
enum Side {
    w(0) {
        @Override
        public String toString() {
            return "White";
        }
    },

    b(1) {
        @Override
        public String toString() {
            return "Black";
        }
    };

    final int index;

    Side(int i) {
        index = i;
    }
}