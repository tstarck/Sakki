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
        Move foo = new Move("e4", Turn.white);
        Coord bar = new Coord(4,3);

        assertEquals(foo.piece(), Piece.P);
        assertTrue(!foo.claimCapture());
        assertEquals(foo.goTo().toString(), bar.toString());
    }

    @Test
    public void capturingMove() {
        Move foo = new Move("Nexd5", Turn.white);
        Coord bar = new Coord(3,4);

        assertEquals(foo.piece(), Piece.N);
        assertTrue(foo.claimCapture());
        assertEquals(foo.goTo().toString(), bar.toString());
    }

    @Test
    public void nontrivialMove() {
        Move foo = new Move("b1=Q+", Turn.black);
        Coord bar = new Coord(1,0);

        assertEquals(foo.piece(), Piece.p);
        assertTrue(!foo.claimCapture());
        assertEquals(foo.goTo().toString(), bar.toString());
    }

    @Test
    public void castlingMove() {
        Move kingside = new Move("0-0", Turn.black);
        Move queenside = new Move("0-0-0", Turn.white);
        assertTrue(kingside.castlingKingside());
        assertTrue(queenside.castlingQueenside());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMove() {
        Move fail = new Move("Nf9", Turn.white);
    }
}