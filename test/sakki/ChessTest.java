package sakki;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tuomas Starck
 */
public class ChessTest {
    public ChessTest() {
    }

    /**
     * Test of move() method, of class Chess.
     */
    @Test
    public void disableQueensCastlings() throws Exception {
        String
            alg = "Rxa1",
            fen = "rnbqkbnr/1ppppppp/8/8/8/8/1PPPPPPP/RNBQKBNR b KQkq - 0 2",
            exp = "1nbqkbnr/1ppppppp/8/8/8/8/1PPPPPPP/rNBQKBNR w Kk - 0 3";

        Chess game = new Chess(fen);
        game.move(alg);
        String res = game.toFen();
        assertEquals(exp, res);
    }

    /**
     * Test to see if FEN parsing and formatting works.
     */
    @Test
    public void fenPassThrough() {
        String[] fence = {
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            "5rk1/1p3pp1/6q1/p1P3bp/NP5n/P1Q4P/2Pr1BP1/R3R1K1 b - - 0 1"
        };

        for (String fen : fence) {
            Chess game = new Chess(fen);
            String res = game.toFen();
            assertEquals(fen, res);
        }
    }

    /**
     * Test using en passant move.
     */
    @Test
    public void enPassant() throws MoveException {
        String fen = "1k1r3r/pp3ppp/8/1Pp1n3/8/3B4/N1PP1PPP/5RK1 w - c6 0 14";
        String exp = "1k1r3r/pp3ppp/2P5/4n3/8/3B4/N1PP1PPP/5RK1 b - - 0 14";

        Chess game = new Chess(fen);
        game.move("xc6");
        String res = game.toFen();
        assertEquals(exp, res);
    }

    /**
     * Testing check and mate recognition.
     */
    @Test
    public void checkAndMate() throws MoveException {
        String exception = null;
        String expectation = "Illegal move";
        String fen = "8/KN6/8/8/1k6/4B3/8/3Q4 w - -";

        Chess game = new Chess(fen);
        game.move("Qd6+");
        game.move("Kc3");
        game.move("Qd4+");
        game.move("Kb3");
        game.move("Nc5+");
        game.move("Ka3");
        game.move("Bc1+");
        game.move("Ka2");
        game.move("Qb2#");

        try {
            game.move("Ka1");
        }
        catch (MoveException me) {
            exception = me.toString();
        }

        assertEquals(expectation, exception);
    }

    /**
     * Castling with checking test
     */
    @Test
    public void checkCastling() throws MoveException {
        String fen = "5k2/3p2pp/4p3/8/8/P5P1/1PP4P/3QK2R w K - 0 1";
        String exp = "5k2/3p2pp/4p3/8/8/P5P1/1PP4P/3Q1RK1 b - - 1 1";

        Chess game = new Chess(fen);
        game.move("0-0+");
        assertEquals(exp, game.toFen());
    }

    /**
     * Thomas Taverner problem initialization test.
     */
    @Test
    public void initThomasTaverner() {
        String[] init = {"3brrb1/2N4B/8/2p4Q/2p2k2/5P2/4P1KR/2N2RB1"};
        String expect = "3brrb1/2N4B/8/2p4Q/2p2k2/5P2/4P1KR/2N2RB1 w KQkq - 0 1";

        Chess game = new Chess(init);
        assertEquals(expect, game.toFen());
    }

    /**
     * Godfrey Heathcote problem initialization test.
     */
    @Test
    public void initGodfreyHeathcote() {
        String[] init = {"6K1/pN2R1PQ/p7/r2k3r/N2n4/1P2p3/BB5p/2Rb2bQ"};
        String expect = "6K1/pN2R1PQ/p7/r2k3r/N2n4/1P2p3/BB5p/2Rb2bQ w KQkq - 0 1";

        Chess game = new Chess(init);
        assertEquals(expect, game.toFen());
    }
}
