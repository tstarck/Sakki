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
public class ChessTest {
    public ChessTest() {
    }

    /**
     * Test of move method, of class Chess.
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
            String result = game.toFen();
            assertEquals(fen, result);
        }
    }
}