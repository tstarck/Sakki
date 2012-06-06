package sakki;

/**
 * Artificial intelligence for Chess.
 *
 * @author Tuomas Starck
 */
class AI {
    private final int MAX_DEPTH = 4;

    private ChessTree tree;
    private ChessNode node;

    private int mm_laskuri;
    private int pr_laskuri;

    AI(ChessTree t) {
        tree = t;
        node = null;
        mm_laskuri = 0;
        pr_laskuri = 0;
    }

    Chess move() {
        int tmp = minimax(tree, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println(mm_laskuri + " <- minimax | pruned -> " + pr_laskuri);
        System.out.println("<<<" +  tmp + ">>>");
        return node;
    }

    /**
     * Minimax algorithm with alpha-beta pruning.
     */
    private int minimax(ChessTree tree, int depth, int alpha, int beta) {
        // System.out.println("Minimax @depth " + depth + " with [" + alpha + ", " + beta + "]");
        // System.out.println("  " + tree.toString());

        mm_laskuri++;

        if (depth >= MAX_DEPTH) {
            return tree.getValue();
        }

        if (tree.getPlayer()) {
            for (ChessTree child : tree) {
                alpha = Math.max(alpha, minimax(child, depth+1, alpha, beta));

                if (beta <= alpha) {
                    pr_laskuri += 30 * (MAX_DEPTH - depth);
                    break;
                }
            }

            if (depth == 1) {
                node = tree.getNode();
            }

            return alpha;
        }
        else {
            for (ChessTree child : tree) {
                beta = Math.min(beta, minimax(child, depth+1, alpha, beta));

                if (beta <= alpha) {
                    pr_laskuri += 30 * (MAX_DEPTH - depth);
                    break;
                }
            }

            if (depth == 1) {
                node = tree.getNode();
            }

            return beta;
        }
    }
}
