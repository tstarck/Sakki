/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tuomas Starck
 */
public class CoordTest {

    public CoordTest() {
    }

    @Test
    public void firstTest() {
        Coord foo = new Coord("c3");
        Coord bar = new Coord(2, 2);
        assertEquals(foo.toString(), bar.toString());
        assertTrue(foo.file == bar.file);
        assertTrue(foo.rank == bar.rank);
    }

    @Test
    public void secondTest() {
        Coord foo = new Coord("h8");
        Coord bar = new Coord(7, 7);
        assertTrue(foo.file == bar.file);
        assertTrue(foo.rank == bar.rank);
    }

    @Test(expected = IllegalArgumentException.class)
    public void firstExceptionTest() {
        Coord fail = new Coord(0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondExceptionTest() {
        Coord fail = new Coord("3a");
    }
}