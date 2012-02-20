package sakki;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Tuomas Starck
 */
public class ChessTest {
    public ChessTest() {
    }

    /**
     * Immortal game for testing simple moves.
     */
    @Test
    public void immortalGame() throws MoveException {
        String exp = "r1bk3r/p2pBpNp/n4n2/1p1NP2P/6P1/3P4/P1P1K3/q5b1 b - - 1 23";

        String[] immortal = {
            "e4",   "e5",   "f4",   "exf4", "Bc4",  "Qh4+",
            "Kf1",  "b5?!", "Bxb5", "Nf6",  "Nf3",  "Qh6",
            "d3",   "Nh5",  "Nh4",  "Qg5",  "Nf5",  "c6",
            "g4",   "Nf6",  "Rg1!!","cxb5?","h4!",  "Qg6",
            "h5",   "Qg5",  "Qf3",  "Ng8",  "Bxf4", "Qf6",
            "Nc3",  "Bc5",  "Nd5",  "Qxb2", "Bd6!", "Bxg1?",
            "e5!",  "Qxa1+","Ke2",  "Na6",  "Nxg7+","Kd8",
            "Qf6+!","Nxf6", "Be7#"
        };

        Chess game = new Chess();

        for (String move : immortal) {
            game.move(move);
        }

        assertEquals(exp, game.toString());
    }

    /**
     * Test to see if FEN parsing and formatting works.
     */
    @Test
    public void fenPassThrough() {
        String[] fence = {
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            "5rk1/1p3pp1/6q1/p1P3bp/NP5n/P1Q4P/2Pr1BP1/R3R1K1 b - - 0 1",
            "2B5/8/4pN1K/R1B1qkP1/4p3/7p/5P1P/4Q3 w - - 3 23"
        };

        for (String fen : fence) {
            Chess game = new Chess(fen);
            String res = game.toString();
            assertEquals(fen, res);
        }
    }

    /**
     * Thomas Taverner problem initialization test.
     */
    @Test
    public void initThomasTaverner() {
        String[] fen = {"3brrb1/2N4B/8/2p4Q/2p2k2/5P2/4P1KR/2N2RB1"};
        String exp = "3brrb1/2N4B/8/2p4Q/2p2k2/5P2/4P1KR/2N2RB1 w - - 0 1";

        Chess game = new Chess(fen);
        assertEquals(exp, game.toString());
    }

    /**
     * Godfrey Heathcote problem initialization test.
     */
    @Test
    public void initGodfreyHeathcote() {
        String[] fen = {"6K1/pN2R1PQ/p7/r2k3r/N2n4/1P2p3/BB5p/2Rb2bQ"};
        String exp = "6K1/pN2R1PQ/p7/r2k3r/N2n4/1P2p3/BB5p/2Rb2bQ w - - 0 1";

        Chess game = new Chess(fen);
        assertEquals(exp, game.toString());
    }

    /**
     * Unambiguous piece selection test.
     */
    @Test
    public void unambiguousSelection() throws MoveException {
        String fen = "8/3nq1q1/6n1/3Rp2R/3P1P2/8/8/q3q3";
        String exp1 = "8/3nq3/8/4q2R/5P2/8/8/q3q3 w - - 0 3";
        String exp2 = "8/4q1q1/6n1/3Rq3/3P4/8/8/q7 w - - 0 3";

        String[] mv1 = {
            "dxe5",
            "N6xe5",
            "Rdxe5",
            "Qgxe5"
        };
        String[] mv2 = {
            "fxe5",
            "Ndxe5",
            "Rhxe5",
            "Qe1xe5"
        };

        Chess g1 = new Chess(fen);
        Chess g2 = new Chess(fen);

        for (String move : mv1) {
            g1.move(move);
        }
        for (String move : mv2) {
            g2.move(move);
        }

        assertEquals(exp1, g1.toString());
        assertEquals(exp2, g2.toString());
    }

    /**
     * Test disabling of castling options.
     */
    @Test
    public void disableCastlings() throws Exception {
        String fen = "rnbqkbnr/2pppppp/1p6/p7/8/P5P1/1PPPPPBP/RNBQK1NR w KQkq - 0 4";

        String[] moves = {
            "Bxa8",
            "e6",
            "Ra2",
            "Bxa3",
            "Nf3",
            "Kf8",
            "0-0"
        };

        String[] results = {
            "Bnbqkbnr/2pppppp/1p6/p7/8/P5P1/1PPPPP1P/RNBQK1NR b KQk - 0 4",
            "Bnbqkbnr/2pp1ppp/1p2p3/p7/8/P5P1/1PPPPP1P/RNBQK1NR w KQk - 0 5",
            "Bnbqkbnr/2pp1ppp/1p2p3/p7/8/P5P1/RPPPPP1P/1NBQK1NR b Kk - 1 5",
            "Bnbqk1nr/2pp1ppp/1p2p3/p7/8/b5P1/RPPPPP1P/1NBQK1NR w Kk - 0 6",
            "Bnbqk1nr/2pp1ppp/1p2p3/p7/8/b4NP1/RPPPPP1P/1NBQK2R b Kk - 1 6",
            "Bnbq1knr/2pp1ppp/1p2p3/p7/8/b4NP1/RPPPPP1P/1NBQK2R w K - 2 7",
            "Bnbq1knr/2pp1ppp/1p2p3/p7/8/b4NP1/RPPPPP1P/1NBQ1RK1 b - - 3 7"
        };

        int lim = (moves.length < results.length)? moves.length: results.length;
        Chess game = new Chess(fen);

        for (int i=0; i<lim; i++) {
            game.move(moves[i]);
            assertEquals(results[i], game.toString());
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
        assertEquals(exp, game.toString());
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
     * Combined castling and checking test.
     */
    @Test
    public void checkCastling() throws MoveException {
        String fen = "5k2/3p2pp/4p3/8/8/P5P1/1PP4P/3QK2R w K - 0 1";
        String exp = "5k2/3p2pp/4p3/8/8/P5P1/1PP4P/3Q1RK1 b - - 1 1";

        Chess game = new Chess(fen);
        game.move("0-0+");
        assertEquals(exp, game.toString());
    }
}
