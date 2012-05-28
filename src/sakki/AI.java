package sakki;

/**
 * Artificial intelligence for Chess.
 *
 * @author Tuomas Starck
 */
class AI {
    private final int MAX_DEPTH = 4;

    private ChessTree tree;

    AI(ChessTree t) {
        tree = t;
    }

    Chess move() {
        System.out.println("<<<" + minimax(tree, 0, Integer.MIN_VALUE, Integer.MAX_VALUE) + ">>>");
        return null;
    }

    /**
     * Minimax algorithm with alpha-beta pruning.
     */
    private int minimax(ChessTree tree, int depth, int alpha, int beta) {
        System.out.println("Minimax @depth " + depth + " with [" + alpha + ", " + beta + "]");

        if (depth >= MAX_DEPTH) {
            return tree.getValue();
        }

        if (tree.getPlayer()) {
            for (ChessTree child : tree) {
                alpha = Math.max(alpha, minimax(child, depth+1, alpha, beta));

                if (beta <= alpha) {
                    System.out.println("Pruning");
                    break;
                }
            }

            System.out.println("Max out with " + alpha);

            return alpha;
        }
        else {
            for (ChessTree child : tree) {
                beta  = Math.min(beta,  minimax(child, depth+1, alpha, beta));

                if (beta <= alpha) {
                    System.out.println("Pruning");
                    break;
                }
            }

            System.out.println("Min out with " + beta);

            return beta;
        }

    }
}
