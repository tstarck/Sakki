package fi.starck.sakki.ai;

import fi.starck.sakki.board.Chess;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Chess game tree for Minimax to consume.
 *
 * @see ChessNode
 *
 * @author Tuomas Starck
 */
class ChessTree implements Iterable<ChessTree> {
    private ChessNode game;
    private ArrayList<ChessTree> nodes;

    ChessTree(Chess g) {
        game = new ChessNode(g.toString());
        nodes = new ArrayList<ChessTree>();
    }

    ChessTree(ChessNode n) {
        game = n;
        nodes = new ArrayList<ChessTree>();
    }

    ChessNode getNode() {
        return game;
    }

    int getValue() {
        return game.getValue();
    }

    boolean getPlayer() {
        return game.getTurn();
    }

    @Override
    public Iterator<ChessTree> iterator() {
        for (ChessNode n : game) {
            nodes.add(new ChessTree(n));
        }

        return nodes.iterator();
    }

    @Override
    public String toString() {
        return game.toString();
    }
}
