/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.ArrayList;

/**
 * An implementation of Chess for two players.
 *
 * @author Tuomas Starck
 */
class Chess {
    private Board board;
    private Turn turn;
    private Coord enpassant;
    private int halfmove;
    private int fullmove;
    private ArrayList<String> history;

    public Chess() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Chess(String fenString) {
        this(fenString.split(" "));
    }

    public Chess(String[] fenArray) {
        board = new Board(fenArray[0]);
        turn = Turn.valueOf(fenArray[1]);

        try {
            enpassant = new Coord(fenArray[3]);
        }
        catch (IllegalArgumentException pass) {
            enpassant = null;
        }

        halfmove = Integer.parseInt(fenArray[4]);
        fullmove = Integer.parseInt(fenArray[5]);

        history = new ArrayList<String>();
    }

    private String toFen() {
        String ep = (enpassant == null)? "-": enpassant.toString();
        return String.format("%s %s KQkq %s %d %d",
            board, turn.name(), ep, halfmove, fullmove);
    }

    void move(String algebraic) throws MoveException {
        int prev = history.size() - 1;

        Move move = new Move(algebraic, turn);

        move = board.move(move);

        if (turn == Turn.w) {
            turn = Turn.b;
        }
        else {
            turn = Turn.w;
            fullmove++;
        }

        // FIXME Insert castling code

        enpassant = move.enpassant();

        if (move.isClaimingCapture() || move.piece().isPawn()) {
            halfmove = 0;
        }
        else {
            halfmove++;
        }

        if (prev >= 0) {
            System.out.println(history.get(prev));
        }

        history.add(toFen());
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

    @Override
    public String toString() {
        int rankIndex = 8;
        String boardStr = "";
        String fileIndex = "a b c d e f g h";

        for (Type[] rank : board.getState()) {
            boardStr += "\n" + rankIndex-- + " ";
            for (Type file : rank) {
                boardStr += " " + file;
            }
        }

        return String.format("\n   %s\n%s\n",
            fileIndex, boardStr);
    }
}