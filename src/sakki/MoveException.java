/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * Custom exception class for better error handling.
 *
 * @author Tuomas Starck
 */
class MoveException extends Exception {
    private String msg;

    public MoveException(String str) {
        super(str);
        msg = str;
    }

    @Override
    public String toString() {
        return msg;
    }
}