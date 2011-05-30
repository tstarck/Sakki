/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tuomas Starck
 */
public class CastleTest {
    public CastleTest() {
    }

    /**
     * Castle 1st: Testing parameterless constructor,
     * toString() and a bunch of methods.
     */
    @Test
    public void disableCastlingOptions() {
        Castle castle = new Castle();
        castle.disable("Kq");
        assertEquals(castle.toString(), "Qk");
        castle.disable("qqq");
        assertEquals(castle.toString(), "Qk");
        castle.disable("Q");
        assertEquals(castle.toString(), "k");
    }

    /**
     * Castle 2nd: Testing String constructor, toString()
     * and other stuff.
     */
    @Test
    public void noCastlingAllowed() {
        Castle castle = new Castle("kongkku");
        assertEquals(castle.toString(), "k");
        castle.disable("k");
        assertEquals(castle.toString(), "-");
    }

    /**
     * Castle 3rd: Testing index() method.
     */
    @Test
    public void testIndex() throws MoveException {
        Castle castle = new Castle();
        Move foo = new Move("0-0-0+", Side.w);
        Move bar = new Move("0-0", Side.b);

        assertTrue(castle.index(foo) == 1);
        assertTrue(castle.index(bar) == 2);
    }
}