package fi.starck.sakki.board;

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

        Move foo = new Move("0-0-0+", true);
        Move bar = new Move("0-0", false);

        assertTrue(castle.index(foo) == 1);
        assertTrue(castle.index(bar) == 2);
    }

    /**
     * Basic castling integration test.
     */
    @Test
    public void basicCastling() throws MoveException {
        String expect = "rnbq1rk1/pppp1ppp/5n2/2b5/3PPp2/3B1N2/PPP3PP/RNBQ1RK1 b - - 6 6";
        Chess game = new Chess("rnbqk2r/pppp1ppp/5n2/2b5/3PPp2/3B1N2/PPP3PP/RNBQK2R b KQkq - 4 5");

        game.move("0-0");
        game.move("O-O");

        assertEquals(expect, game.toString());
    }

    /**
     * Rook effect test.
     */
    @Test
    public void rookEffect() throws MoveException {
        String expect = "3rkbnr/ppp1qppp/2np4/8/4Pp2/P2B1N1R/RPPP2P1/1NBQK3 b k - 2 8";
        Chess game = new Chess("r3kbnr/ppp1qppp/2np4/8/4Pp2/P2B1N1b/1PPP2P1/RNBQK2R w KQkq - 0 7");

        game.move("Rxh3");
        game.move("Rd8");
        game.move("Ra2");

        assertEquals(expect, game.toString());
    }

    /**
     * Checked or threatened king test.
     */
    @Test
    public void threatenedPassage() {
        Chess game;

        String msg = "King must have safe passage";

        String[] cases = {
            "r3k2r/p1p2ppp/1pB/4Nb1q/5B/2NQ3n/PPP4P/R3K2R w",
            "r3k2r/p1p2ppp/1pB/4Nb1q/5B/2NQ3n/PPP4P/R3K2R b"
        };

        String[] moves = {
            "0-0", "0-0-0"
        };

        for (String test : cases) {
            game = new Chess(test);

            for (String move : moves) {
                try {
                    game.move(move);
                    assertTrue(false);
                }
                catch (MoveException me) {
                    assertEquals(msg, me.toString());
                }
            }
        }
    }
}
