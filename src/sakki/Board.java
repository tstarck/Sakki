/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.ArrayList;

/**
 * Model of a Chess board and its rules.
 *
 * Board is standard sized with eight files and ranks.
 *
 * @author Tuomas Starck
 */
public class Board {
    private ArrayList<Piece> board;
    private Type[][] status;

    public Board() {
        board = new ArrayList<Piece>();

        board.add(new WhitePawn("c2"));
        board.add(new WhitePawn("d2"));
        board.add(new WhiteRook("a1"));
        board.add(new WhiteKnight("b1"));
        board.add(new WhiteBishop("c1"));
        board.add(new WhiteKing("e1"));

        board.add(new BlackPawn("e3"));
        board.add(new BlackKnight("g8"));
        board.add(new BlackKing("e8"));

        status = new Type[8][8];
        update();
    }

    private void update() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                status[i][j] = Type.empty;
            }
        }

        for (Piece pc : board) {
            Coord loc = pc.where();
            status[loc.rank][loc.file] = pc.what();
        }

        for (Piece pc : board) {
            pc.update(status);
        }
    }

    public boolean move(Move move) {
        if (move == null) {
            return false;
        }

        return false; // DEBUG
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

    public String toFen() {
        String fen = "";

        for (Type[] rank : status) {
            fen += packRank(rank);
        }

        return fen.substring(1);
    }

    @Override
    public String toString() {
        String str = "";

        for (Type[] rank : status) {
            str += "\n";
            for (Type file : rank) {
                str += " " + file;
            }
        }

        return str;
    }
}