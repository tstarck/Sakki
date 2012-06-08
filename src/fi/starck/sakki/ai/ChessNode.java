package fi.starck.sakki.ai;

import fi.starck.sakki.board.Chess;
import fi.starck.sakki.board.MoveException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Chess game tree node (todo: with an evaluation of game status).
 *
 * @author Tuomas Starck
 */
class ChessNode extends Chess implements Iterable<ChessNode>, Iterator<ChessNode> {
    private ChessNode child;
    private ArrayList<String> moves;

    public ChessNode(String fen) {
        super(fen);
        moves = new ArrayList<String>();
    }

    private ChessNode(String fen, String move) throws MoveException {
        super(fen);
        moves = new ArrayList<String>();
        this.move(move);
    }

    int getValue() {
        return 3;
    }

    @Override
    public Iterator iterator() {
        moves.addAll(board.getAllMoves(this.getTurn()));
        
        return this;
    }

    @Override
    public boolean hasNext() {
        String move;

        try {
            move = moves.remove(0);
        }
        catch (IndexOutOfBoundsException ioob) {
            child = null;
            return false;
        }

        try {
            child = new ChessNode(this.toString(), move);
        }
        catch (MoveException me) {
            /* FIXME:
             * Which moves are invalid and why?
             */
            return hasNext();
        }

        return true;
    }

    @Override
    public ChessNode next() {
        return child;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
