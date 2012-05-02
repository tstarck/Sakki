package sakki;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author Tuomas Starck
 */
public class CastleTest {
    public CastleTest() {
    }

    /**
     * Testing parameterless constructor
     * and disable() method.
     */
    @Test
    public void disableCastlingOptions() {
        Castle castle = new Castle();
        castle.disable("Kq");
        assertEquals("Qk", castle.toString());
        castle.disable("qqq");
        assertEquals("Qk", castle.toString());
        castle.disable("Q");
        assertEquals("k",  castle.toString());
    }

    /**
     * Testing String constructor and
     * disable() method.
     */
    @Test
    public void castlingStringHandling() {
        Castle castle = new Castle("Klonkku");
        assertEquals("Kk", castle.toString());
        castle.disable("k");
        assertEquals("K", castle.toString());
        castle.disable("K");
        assertEquals("-", castle.toString());
    }

    /**
     * Testing index() method.
     */
    @Test
    public void testIndex() throws MoveException {
        Castle castle = new Castle();
        Move foo = new Move("0-0-0+", Side.w);
        Move bar = new Move("0-0", Side.b);
        assertTrue(castle.index(foo) == 1);
        assertTrue(castle.index(bar) == 2);
    }

    /**
     * Basic castling integration test.
     */
    @Test
    public void castlingIntegration() {
        String expectation = "rnbq1rk1/pppp1ppp/5n2/2b5/3PPp2/3B1N2/PPP3PP/RNBQ1RK1 b - - 6 6";
        Chess game = new Chess("rnbqk2r/pppp1ppp/5n2/2b5/3PPp2/3B1N2/PPP3PP/RNBQK2R b KQkq - 4 5");

        try {
            game.move("0-0");
            game.move("O-O");
        }
        catch (MoveException me) {
            assertTrue(false);
        }

        assertEquals(expectation, game.toString());
    }

    /**
     * Checked king test.
     */
    @Test(expected = MoveException.class)
    public void checkedKing() throws MoveException {
        Chess game = new Chess("r3k2r/p1p2ppp/1pn5/4Nb1q/4BB2/2NQ4/PPP3PP/R3K2R w KQkq - 5 13");
        game.move("O-O-O");
    }

    /**
     * Threatened passage test.
     */
    @Test(expected = MoveException.class)
    public void threatenedPassage() throws MoveException {
        Chess game = new Chess("r3k2r/p1p2ppp/1pn5/4Nb1q/4BB2/2NQ4/PPP3PP/R3K2R w KQkq - 5 13");
        game.move("Bxc6");
        game.move("0-0");
    }
}
