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
    private Castle castling;
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
        castling = new Castle(fenArray[2]);

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
        return String.format("%s %s %s %s %d %d",
            board, turn.name(), castling, ep, halfmove, fullmove);
    }

    private void castle(Move move) throws MoveException {
        boolean side = move.castlingSide();

        if (!castling.isDoable(turn, side)) {
            throw new MoveException("Castling no longer allowed");
        }

        for (String sqr : castling.requiredFree(turn, side)) {
            if (board.isOccupied(new Coord(sqr))) {
                throw new MoveException("Castling blocked");
            }
        }

        board.castle(turn, side);

        castling.done(turn);
    }

    void move(String algebraic) throws MoveException {
        int prev = history.size() - 1;

        Move move = new Move(algebraic, turn);

        if (move.isCastling()) {
            castle(move);
        }
        else {
            move = board.move(move);
        }

        if (turn == Turn.w) {
            turn = Turn.b;
        }
        else {
            turn = Turn.w;
            fullmove++;
        }

        // castling.drop(move.preventCastling());

        // enpassant = move.enpassant();

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
        String boardStr = "";
        String fileIndex = "a b c d e f g h";
        Type[][] state = board.getState();
        int[] material = board.getMaterial();

        for (int i=0; i<state.length; i++) {
            boardStr += "\n" + (8-i) + " ";

            for (Type file : state[i]) {
                boardStr += " " + file;
            }

            if (i == 0) {
                boardStr += "  " + material[1];
            }

            if (i == 7) {
                boardStr += "  " + material[0];
            }
        }

        return String.format("\n   %s\n%s\n", fileIndex, boardStr);
    }
}