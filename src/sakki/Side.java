package sakki;

/**
 * In the game of Chess either White or Black holds the next move.
 *
 * @author Tuomas Starck
 */
enum Side {
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

    /**
     * Return a valid side based on input.
     *
     * @param input Should be w or b.
     *
     * @return Valid side.
     */
    public Side valid(String input) {
        if (input == null) return Side.w;
        if (input.equals("b")) return Side.b;
        return Side.w;
    }
}
