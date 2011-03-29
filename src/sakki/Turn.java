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
        public String toString() {
            return "w";
        }
    },
    
    black {
        public String toString() {
            return "b";
        }
    }

}
