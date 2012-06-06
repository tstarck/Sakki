package sakki;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
        return new Random().nextInt(1000);
    }

    @Override
    public Iterator iterator() {
        for (Piece piece : board.getPieces()) {
            if (piece.getSide() == this.getTurn()) {
                moves.addAll(piece.getMoves());
            }
        }

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
