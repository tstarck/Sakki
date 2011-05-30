/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 * An implementation of Chess for two players.
 *
 * @author Tuomas Starck
 */
public class Chess {
    private Board board;
    private Side turn;
    private Castle castling;
    private Coord enpassant;
    private int halfmove;
    private int fullmove;

    public Chess() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Chess(String fenString) {
        this(fenString.split(" "));
    }

    public Chess(String[] fenArray) {
        try {
            enpassant = new Coord(fenArray[3]);
        }
        catch (IllegalArgumentException pass) {
            enpassant = null;
        }

        board = new Board(fenArray[0], enpassant);

        turn = Side.valueOf(fenArray[1]);

        castling = new Castle(fenArray[2]);

        halfmove = Integer.parseInt(fenArray[4]);
        fullmove = Integer.parseInt(fenArray[5]);
    }

    void move(String algebraic) throws MoveException {
        Rebound rebound = null;

        Move move = new Move(algebraic, turn);

        if (move.isCastling()) {
            rebound = board.castling(move, castling);
        }
        else {
            rebound = board.move(move, enpassant);
        }

        if (turn == Side.w) {
            turn = Side.b;
        }
        else {
            turn = Side.w;
            fullmove++;
        }

        castling.disable(rebound.castlingObstacle());

        enpassant = rebound.getEnpassant();

        if (move.isCapturing() || move.piece().isPawn()) {
            halfmove = 0;
        }
        else {
            halfmove++;
        }
    }

    private String ordinal(int n) {
        int tmp = n % 10;

        if (n % 100 - tmp == 10) return "th";

        switch (tmp) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
        }

        return "th";
    }

    public String prompt() {
        String fiftyMoveRule = "";

        if (42 <= halfmove) {
            fiftyMoveRule = "Halfmoves: " + halfmove + "\n";
        }

        return String.format("\n%s%s's %d%s> ",
            fiftyMoveRule, turn, fullmove, ordinal(fullmove));
    }

    public String toFen() {
        String ep = (enpassant == null)? "-": enpassant.toString();
        return String.format("%s %s %s %s %d %d",
            board, turn.name(), castling, ep, halfmove, fullmove);
    }

    @Override
    public String toString() {
        String boardStr = "";
        String fileLegend = "a b c d e f g h";
        String enpassantStr = (enpassant == null)? "-": enpassant.toString();
        Type[][] state = board.getState();
        int[] material = board.getMaterial();

        for (int i=0; i<state.length; i++) {
            boardStr += "\n" + (8-i) + " ";

            for (Type file : state[i]) {
                boardStr += " " + file;
            }

            if (i == 0) {
                boardStr += "  " + material[Side.b.index];
            }

            if (i == 7) {
                boardStr += "  " + material[Side.w.index];
            }
        }

        return String.format("\n   %s  %s %s\n%s\n", fileLegend,
            castling.toString(), enpassantStr, boardStr);
    }
}