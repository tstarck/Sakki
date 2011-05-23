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
            Coord co = new Coord(file, rank);

            if (chr == '/') {
                file = 0;
                rank++;
            }
            else if (Character.isDigit(chr)) {
                file += Character.digit(chr, 10);
            }
            else if (Character.isLetter(chr)) {
                board.add(pieceByName(chr, co));
                file++;
            }
        }

        update();
    }

    private Piece pieceByType(Type type, Coord co) {
        return pieceByName(type.name().charAt(0), co);
    }

    private Piece pieceByName(char chr, Coord co) {
        switch (chr) {
            case 'P': return new WhitePawn(co);
            case 'p': return new BlackPawn(co);
            case 'B': return new WhiteBishop(co);
            case 'b': return new BlackBishop(co);
            case 'N': return new WhiteKnight(co);
            case 'n': return new BlackKnight(co);
            case 'R': return new WhiteRook(co);
            case 'r': return new BlackRook(co);
            case 'Q': return new WhiteQueen(co);
            case 'q': return new BlackQueen(co);
            case 'K': return new WhiteKing(co);
            case 'k': return new BlackKing(co);
        }

        return null;
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

    private Piece whichPiece(Move move) throws MoveException {
        int index = 0;
        ArrayList<Piece> alt = new ArrayList<Piece>();

        for (Piece pc : board) {
            if (pc.who() == move.piece()) {
                if (pc.canGoto(move.to())) {
                    alt.add(pc);
                }
            }
        }

        if (alt.isEmpty()) {
            throw new MoveException("No such move available");
        }

        if (alt.size() == 1) {
            return alt.get(index);
        }

        if (move.from() == null) {
            throw new MoveException("Ambiguous move - hint required");
        }

        for (int i=1; i<alt.size(); i++) {
            if (move.odds(alt.get(index)) < move.odds(alt.get(i))) {
                index = i;
                System.out.println("D choose " + alt.get(index).toString() +
                        " < " + alt.get(i).toString());
            }
            else {
                System.out.println("D choose " + alt.get(index).toString() +
                        " >= " + alt.get(i).toString());

            }
        }

        return alt.get(index);
    }

    public Rebound move(Move move) throws MoveException {
        if (move == null) return null;

        Piece piece = whichPiece(move);

        Piece capture = null;
        Rebound rebound = null;

        System.out.println(piece);

        for (Piece pc : board) {
            if (pc.where().equals(move.to())) {
                capture = pc;
                System.out.println("Capturing " + pc.toString() + "!");
                break;
            }
        }

        if (capture != null) {
            if (move.isClaimingCapture()) {
                board.remove(capture);
            }
            else {
                throw new MoveException("Unclaimed capture");
            }
        }
        else if (move.isClaimingCapture()) {
            throw new MoveException("Capture claimed in vain");
        }

        rebound = piece.move(move);

        if (rebound.canPromote()) {
            Type officer = move.promotion();

            if (officer != null) {
                board.remove(piece);
                board.add(pieceByType(officer, move.to()));
            }
        }

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