package sakki;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tuomas Starck
 */
public class MoveTest {
    public MoveTest() {
    }

    @Test
    public void simpleMove() throws MoveException {
        Move foo = new Move("e4", Side.w);
        Coord bar = new Coord(4,4);

        assertEquals(foo.piece(), Type.P);
        assertTrue(!foo.isCapturing());
        assertEquals(foo.to().toString(), bar.toString());
    }

    @Test
    public void capturingMove() throws MoveException {
        Move foo = new Move("Nexd5", Side.w);
        Coord bar = new Coord(3,3);

        assertEquals(foo.piece(), Type.N);
        assertTrue(foo.isCapturing());
        assertEquals(foo.to().toString(), bar.toString());
    }

    @Test
    public void nontrivialMove() throws MoveException {
        Move foo = new Move("b1=Q+", Side.b);
        Coord bar = new Coord(1,7);

        assertEquals(foo.piece(), Type.p);
        assertTrue(!foo.isCapturing());
        assertEquals(foo.to().toString(), bar.toString());
    }

    @Test
    public void castlingMove() throws MoveException {
        Move kingside = new Move("0-0", Side.b);
        Move queenside = new Move("0-0-0+", Side.w);
        assertTrue(kingside.castling() == kingside.KINGSIDE);
        assertFalse(kingside.castling() == kingside.QUEENSIDE);
        assertTrue(queenside.castling() == queenside.QUEENSIDE);
        assertFalse(queenside.castling() == queenside.KINGSIDE);
    }

    @Test(expected = MoveException.class)
    public void invalidMove() throws MoveException {
        Move fail = new Move("Nf9", Side.w);
    }
}
