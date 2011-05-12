/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.ArrayList;

/**
 * Model of a Chess board and pieces on it.
 *
 * Board is standard sized with eight files and ranks.
 *
 * @author Tuomas Starck
 */
public class Board {
    private ArrayList<Piece> board;
    private Type[][] state;

    public Board() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public Board(String fen) {
        int file = 0;
        int rank = 0;

        state = new Type[8][8];
        board = new ArrayList<Piece>();

        for (char chr : fen.toCharArray()) {
            Coord co = null;

            try {
                co = new Coord(file, rank);
            }
            catch (IllegalArgumentException e) {}

            switch (chr) {
                case '/':
                    file = 0;
                    rank++;
                    break;
                case 'P':
                    board.add(new WhitePawn(co));   file++; break;
                case 'p':
                    board.add(new BlackPawn(co));   file++; break;
                case 'B':
                    board.add(new WhiteBishop(co)); file++; break;
                case 'b':
                    board.add(new BlackBishop(co)); file++; break;
                case 'N':
                    board.add(new WhiteKnight(co)); file++; break;
                case 'n':
                    board.add(new BlackKnight(co)); file++; break;
                case 'R':
                    board.add(new WhiteRook(co));   file++; break;
                case 'r':
                    board.add(new BlackRook(co));   file++; break;
                case 'Q':
                    board.add(new WhiteQueen(co));  file++; break;
                case 'q':
                    board.add(new BlackQueen(co));  file++; break;
                case 'K':
                    board.add(new WhiteKing(co));   file++; break;
                case 'k':
                    board.add(new BlackKing(co));   file++; break;
                default:
                    file += Character.digit(chr, 10);
                    break;
            }
        }

        update();
    }

    private void update() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                state[i][j] = Type.empty;
            }
        }

        for (Piece pc : board) {
            Coord loc = pc.where();
            state[loc.rank][loc.file] = pc.who();
        }

        for (Piece pc : board) {
            pc.update(state);
        }
    }

    private Move castle(Move move) throws MoveException {
        throw new MoveException("Castling not implemented");
    }

    public Move move(Move move) throws MoveException {
        if (move == null) return move;

        if (move.isKingsideCastling() || move.isQueensideCastling()) {
            return castle(move);
        }

        ArrayList<Piece> possibles = new ArrayList<Piece>();

        for (Piece pc : board) {
            if (pc.who() == move.piece()) {
                if (pc.canGoto(move.to())) {
                    possibles.add(pc);
                }
            }
        }

        if (possibles.isEmpty()) {
            throw new MoveException("No such move available");
        }

        if (possibles.size() != 1) {
            throw new MoveException("Unable to distinguish between moves");
        }

        int capture = -1;
        Piece pc = possibles.get(0);

        for (int i=0; i<board.size(); i++) {
            if (board.get(i).where().equals(move.to())) {
                capture = i;
            }
        }

        if (capture != -1) {
            if (move.isClaimingCapture()) {
                board.remove(capture);
            }
            else {
                throw new MoveException("Unclaimed capture detected");
            }
        }
        else {
            if (move.isClaimingCapture()) {
                throw new MoveException("Capture claimed in vain");
            }
        }

        move = pc.move(move);

        update();

        return move;
    }

    public Type[][] getState() {
        return state;
    }

    private String packRank(Type[] rank) {
        int empty = 0;
        String str = "";

        for (Type file : rank) {
            if (file == Type.empty) {
                empty++;
            }
            else if (empty != 0) {
                str += String.valueOf(empty) + file;
                empty = 0;
            }
            else {
                str += file;
            }
        }

        if (empty != 0) {
            str += String.valueOf(empty);
        }

        return "/" + str;
    }

    @Override
    public String toString() {
        String fen = "";

        for (Type[] rank : state) {
            fen += packRank(rank);
        }

        return fen.substring(1);
    }
}