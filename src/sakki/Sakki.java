package sakki;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simple but effective UI for Sakki chess model.
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
     * @param argv An array of FEN primitives. Up to 6 array element
     * are handled even if more are given.
     *
     * @see Chess
     */
    public static void main(String[] argv) {
        boolean tryParsingFen = false;
        Chess game = null;
        String fen = "";
        String input = null;
        String current = null;
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
            System.out.format("Interpretation:\n %s\n", game.toFen());
        }

        System.out.print(game + game.prompt());

        while (true) {
            current = game.toFen();
            input = read.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(game.prompt());
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
                System.out.format("Interpretation:\n %s\n", game.toFen());
                System.out.print(game + game.prompt());
                continue;
            }

            if (input.equals("h")) {
                help(H_MSG);
                System.out.print(game.prompt());
                continue;
            }

            if (input.equals("help")) {
                help(HELP_MSG);
                System.out.print(game.prompt());
                continue;
            }

            if (input.equals("fen")) {
                System.out.print("FEN> ");
                tryParsingFen = true;
                continue;
            }

            if (input.equals("new")) {
                game = new Chess();
                System.out.print(game + game.prompt());
                continue;
            }

            if (input.equals("s") || input.equals("show")) {
                System.out.println("\n" + current);
                System.out.print(game + game.prompt());
                continue;
            }

            if (input.equals("u") || input.equals("undo")) {
                if (!history.isEmpty()) {
                    game = new Chess(history.remove(0));
                    System.out.print(game + game.prompt());
                }
                else {
                    System.out.println("\nNo available history");
                    System.out.print(game.prompt());
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
                System.out.print(game.prompt());

                /* If move went too far before failing, game is left
                 * in an inconsistent state and must be restored.
                 */
                if (me.isDirty()) {
                    game = new Chess(current);
                }

                continue;
            }

            System.out.print(game + game.prompt());
        }

        System.out.println();
    }
}
