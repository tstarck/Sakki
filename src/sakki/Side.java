package sakki;

/**
 * In the game of Chess either White or Black holds the next move.
 *
 * @author Tuomas Starck
 */
public enum Side {
    w(0) {
        @Override
        public String toString() {
            return "White";
        }
    },

    b(1) {
        @Override
        public String toString() {
            return "Black";
        }
    };

    final int index;

    Side(int i) {
        index = i;
    }
}
