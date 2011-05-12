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
public class MoveTest {

    public MoveTest() {
    }

    @Test
    public void simpleMove() {
        Move foo = new Move("e4", Turn.w);
        Coord bar = new Coord(4,4);

        assertEquals(foo.piece(), Type.p);
        assertTrue(!foo.isClaimingCapture());
        assertEquals(foo.to().toString(), bar.toString());
    }

    @Test
    public void capturingMove() {
        Move foo = new Move("Nexd5", Turn.w);
        Coord bar = new Coord(3,3);

        assertEquals(foo.piece(), Type.n);
        assertTrue(foo.isClaimingCapture());
        assertEquals(foo.to().toString(), bar.toString());
    }

    @Test
    public void nontrivialMove() {
        Move foo = new Move("b1=Q+", Turn.b);
        Coord bar = new Coord(1,7);

        assertEquals(foo.piece(), Type.P);
        assertTrue(!foo.isClaimingCapture());
        assertEquals(foo.to().toString(), bar.toString());
    }

    @Test
    public void castlingMove() {
        Move kingside = new Move("0-0", Turn.b);
        Move queenside = new Move("0-0-0", Turn.w);
        assertTrue(kingside.isKingsideCastling());
        assertTrue(queenside.isQueensideCastling());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMove() {
        Move fail = new Move("Nf9", Turn.w);
    }
}