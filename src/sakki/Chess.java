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
        halfmove = Integer.parseInt(fenArray[4]);
        fullmove = Integer.parseInt(fenArray[5]);
        history = new ArrayList<String>();
    }

    void move(String algebraic) throws MoveException {
        int prev = history.size() - 1;
        Move move = new Move(algebraic, turn);

        board.move(move);

        if (turn == Turn.w) {
            turn = Turn.b;
        }
        else {
            turn = Turn.w;
            fullmove++;
        }

        if (move.claimCapture() || move.piece().isPawn()) {
            halfmove = 0;
        }
        else {
            halfmove++;
        }

        if (prev >= 0) {
            System.out.println("Prev: " + history.get(prev));
        }

        history.add(String.format("%s %s KQkq - %d %d",
            board, turn.name(), halfmove, fullmove));
    }

    public String prompt() {
        String fiftyMoveRule = "";

        if (0 <= halfmove) {
            fiftyMoveRule = "Halfmove count: " + halfmove + "\n";
        }

        return String.format("\n%s%s's %d. move> ",
            fiftyMoveRule, turn, fullmove);
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