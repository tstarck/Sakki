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
        String[] files = new String[] {"a", "b", "c", "d", "e", "f", "g", "h"};

        board = new ArrayList<Piece>();
        status = new Type[8][8];

        for (String f : files) {
            board.add(new WhitePawn(f + 2));
        }

        board.add(new WhiteRook("a1"));
        board.add(new WhiteKnight("b1"));
        board.add(new WhiteBishop("c1"));
        board.add(new WhiteQueen("d1"));
        board.add(new WhiteKing("e1"));
        board.add(new WhiteBishop("f1"));
        board.add(new WhiteKnight("g1"));
        board.add(new WhiteRook("h1"));

        for (String f : files) {
            board.add(new BlackPawn(f + 7));
        }

        board.add(new BlackRook("a8"));
        board.add(new BlackKnight("b8"));
        board.add(new BlackBishop("c8"));
        board.add(new BlackQueen("d8"));
        board.add(new BlackKing("e8"));
        board.add(new BlackBishop("f8"));
        board.add(new BlackKnight("g8"));
        board.add(new BlackRook("h8"));

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

    public boolean move(Move move, Turn turn) {
        if (move == null) return false;

        ArrayList<Piece> possibles = new ArrayList<Piece>();

        for (Piece pc : board) {
            if (pc.what() == move.piece()) {
                if (pc.canGoto(move.to())) {
                    possibles.add(pc);
                }
            }
        }

        if (possibles.isEmpty()) {
            System.out.println(" *** Cannot make move :-(");
            return false;
        }
        else if (possibles.size() == 1) {
            Piece pc = possibles.get(0);

            if (move.claimCapture()) {
                int harvest = -1;

                for (int i=0; i<board.size(); i++) {
                    if (board.get(i).where().toString().equals(move.to().toString())) {
                        harvest = i;
                    }
                }

                if (harvest >= 0) {
                    board.remove(harvest);
                }
            }

            pc.move(move);
        }
        else {
            System.out.println(" *** Cannot make move :-[");
            return false;
        }

        update();

        return true;
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