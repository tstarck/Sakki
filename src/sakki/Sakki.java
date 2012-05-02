package sakki;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simple but effective UI for Sakki chess model.
 *
 * @see Chess
 *
 * @author Tuomas Starck
 */
public class Sakki {
    private static Scanner read = new Scanner(System.in);

    private static final String[]
        H_MSG = {
            "\nCommands: h[elp], fen, new, s[how], u[ndo], q[uit]",
            "Type help for more information!"
        },
        HELP_MSG = {
            "\nAvailable commands:",
            "  h       Quick usage",
            "  help    More verbose usage",
            "  fen     Initiate a new game with FEN",
            "  new     Reset game to initial position",
            "  s[how]  Display FEN of the current game",
            "  u[ndo]  Undo the last halfmove",
            "  q[uit]  Quits the program",
            "",
            "If input does not match any commands above, it is",
            "  interpreted as standard english Algebraic chess",
            "  notation and applied to current game.",
            "",
            "FEN stands for Forsyth-Edwards Notation which is used",
            "  in chess for describing a particular game position."
        };

    private static void help(String[] msg) {
        for (String rivi : msg) {
            System.out.println(rivi);
        }
    }

    /**
     * @param n Some integer.
     *
     * @return A string with English ordinal postfix.
     */
    private static String ordinal(int n) {
        int tmp = n % 10;

        if (n % 100 - tmp == 10) return "th";

        switch (tmp) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
        }

        return "th";
    }

    /**
     * @return Nicely formatted query prompt.
     */
    private static String prompt(Chess game) {
        String fiftyMoveRule = "";
        int fullmove = game.getFullmove();

        if (100 <= game.getHalfmove()) {
            fiftyMoveRule = "Fifty-move rule is active\n";
        }

        return String.format("\n%s%s's %d%s> ",
            fiftyMoveRule, game.getTurn(), fullmove, ordinal(fullmove));
    }

    /**
     * @return Nicely drawn game board and status.
     */
    private static String draw(Chess game) {
        String boardStr = "";
        String fileLegend = "a b c d e f g h";

        Type[][] state = game.getState();
        int[] material = game.getMaterial();

        for (int i=0; i<state.length; i++) {
            boardStr += "\n" + (8-i) + " ";

            for (Type file : state[i]) boardStr += " " + file;

            if (i == 0) boardStr += "  " + material[Side.b.index];
            if (i == 7) boardStr += "  " + material[Side.w.index];
        }

        return String.format("\n   %s  %s %s\n%s\n", fileLegend,
            game.getCastling(), game.getEnpassant(), boardStr);
    }

    /**
     * @param argv An array of FEN primitives. Up to 6 array element
     * are handled even if more are given.
     *
     * @see Chess
     */
    public static void main(String[] argv) {
        boolean tryParsingFen = false;
        Chess game;
        String input;
        String current;
        String fen = "";
        ArrayList<String> history = new ArrayList<String>();

        try {
            game = new Chess(argv);
        }
        catch (IllegalArgumentException iae) {
            /* If given arguments were rubbish,
             * start with initial position.
             */
            game = new Chess();
        }

        for (int i=0; i<argv.length; i++) {
            fen += " " + argv[i];
            if (i >= 5) break;
        }

        if (argv.length > 0) {
            System.out.format("\nInput:\n %s\n", fen.substring(1));
            System.out.format("Interpretation:\n %s\n", game);
        }

        System.out.print(draw(game) + prompt(game));

        while (true) {
            current = game.toString();
            input = read.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(prompt(game));
                tryParsingFen = false;
                continue;
            }

            if (tryParsingFen) {
                Chess reset;
                tryParsingFen = false;

                try {
                    reset = new Chess(input);
                    game = reset;
                    history.clear();
                }
                catch (IllegalArgumentException iae) {
                    System.out.println("\nUnable to parse given FEN!");
                }

                System.out.format("\nInput:\n %s\n", input);
                System.out.format("Interpretation:\n %s\n", game);
                System.out.print(draw(game) + prompt(game));
                continue;
            }

            if (input.equals("h")) {
                help(H_MSG);
                System.out.print(prompt(game));
                continue;
            }

            if (input.equals("help")) {
                help(HELP_MSG);
                System.out.print(prompt(game));
                continue;
            }

            if (input.equals("fen")) {
                System.out.print("FEN> ");
                tryParsingFen = true;
                continue;
            }

            if (input.equals("new")) {
                game = new Chess();
                System.out.print(draw(game) + prompt(game));
                continue;
            }

            if (input.equals("s") || input.equals("show")) {
                System.out.println("\n" + current);
                System.out.print(draw(game) + prompt(game));
                continue;
            }

            if (input.equals("u") || input.equals("undo")) {
                if (!history.isEmpty()) {
                    game = new Chess(history.remove(0));
                    System.out.print(draw(game) + prompt(game));
                }
                else {
                    System.out.println("\nNo available history");
                    System.out.print(prompt(game));
                }

                continue;
            }

            if (input.equals("q") || input.equals("quit")) {
                break;
            }

            try {
                game.move(input);
                history.add(0, current);
            }
            catch (MoveException me) {
                System.out.println("\n" + me);
                System.out.print(prompt(game));

                /* If move went too far before failing, game is left
                 * in an inconsistent state and must be restored.
                 */
                if (me.isDirty()) {
                    game = new Chess(current);
                }

                continue;
            }

            System.out.print(draw(game) + prompt(game));
        }

        System.out.println();
    }
}
