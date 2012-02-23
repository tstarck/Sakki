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

    /**
     * Castling integration tests.
     */
    @Test
    public void castlingIntegration() {
        Chess game;

        String exception;
        String expectation = "Kings passage must be free";

        String foo = "rnbqk2r/pppp1ppp/5n2/2b5/3PPp2/3B1N2/PPP3PP/RNBQK2R b KQkq - 4 5";
        String bar = "r3k2r/p1p2ppp/1pn5/4Nb1q/4BB2/2NQ4/PPP3PP/R3K2R w KQkq - 5 13";

        game = new Chess(foo);

        try {
            game.move("0-0");
            game.move("0-0");
        }
        catch (MoveException me) {
            assertTrue(false);
        }

        exception = "";
        game = new Chess(bar);

        try {
            game.move("0-0-0");
        }
        catch (MoveException me) {
            exception = me.toString();
        }

        assertEquals(expectation, exception);

        exception = "";
        game = new Chess(bar);

        try {
            game.move("Nxc6");
            game.move("0-0-0");
        }
        catch (MoveException me) {
            exception = me.toString();
        }

        assertEquals(expectation, exception);
    }
}
