package sakki;

import java.util.Iterator;
import java.util.Random;

/**
 * Chess game tree node with an evaluation of game status.
 *
 * @author Tuomas Starck
 */
class ChessNode extends Chess implements Iterable<ChessNode>, Iterator<ChessNode> {
    public ChessNode(String s) {
        super(s);
    }

    int getValue() {
        return new Random().nextInt(1000);
    }

    @Override
    public Iterator iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ChessNode next() {
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
