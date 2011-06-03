package sakki;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tuomas Starck
 */
public class CoordTest {
    public CoordTest() {
    }

    @Test
    public void firstTest() {
        Coord foo = new Coord("a1");
        Coord bar = new Coord(0, 7);

        assertTrue(foo.file == bar.file);
        assertTrue(foo.rank == bar.rank);
        assertEquals(foo.toString(), bar.toString());
    }

    @Test
    public void secondTest() {
        Coord foo = new Coord("c3");
        Coord bar = new Coord(2, 5);

        assertTrue(foo.file == bar.file);
        assertTrue(foo.rank == bar.rank);
        assertEquals(foo.toString(), bar.toString());
    }

    @Test
    public void thirdTest() {
        Coord foo = new Coord("h8");
        Coord bar = new Coord(7, 0);

        assertTrue(foo.file == bar.file);
        assertTrue(foo.rank == bar.rank);
        assertEquals(foo.toString(), bar.toString());
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
