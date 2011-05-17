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
    private int[] material;
    private Type[][] state;
    private ArrayList<Piece> board;

    public Board() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public Board(String fen) {
        int file = 0;
        int rank = 0;

        material = new int[2];
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

    public int[] getMaterial() {
        return material;
    }

    public Type[][] getState() {
        return state;
    }

    public boolean isOccupied(Coord co) {
        return (state[co.rank][co.file] != Type.empty);
    }

    private void update() {
        material = new int[2];

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                state[i][j] = Type.empty;
            }
        }

        for (Piece pc : board) {
            Type tp = pc.who();
            Coord loc = pc.where();

            state[loc.rank][loc.file] = tp;

            if (tp.isWhite()) {
                material[0] += tp.getValue();
            }

            if (tp.isBlack()) {
                material[1] += tp.getValue();
            }
        }

        for (Piece pc : board) {
            pc.update(state);
        }
    }

    public void castle(Turn turn, boolean side) throws MoveException {
        throw new MoveException("Not yet implemented");
    }

    public Rebound move(Move move) throws MoveException {
        Rebound rebound = null;
        ArrayList<Piece> possibles = new ArrayList<Piece>();

        if (move == null) return rebound;

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

        rebound = pc.move(move);

        update();

        return rebound;
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