package sakki;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simple and effective UI class for Sakki chess model.
 *
 * @author Tuomas Starck
 */
public class Sakki {
    /**
     * @param args the command line arguments
     */

    private static Scanner lukija = new Scanner(System.in);

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
            "  s[how]  Display FEN of the current status",
            "  u[ndo]  Undoes the last halfmove",
            "  q[uit]  Quits the program",
            "",
            "If input does not match any commands above, it is",
            "  interpreted as standard english Algebraic chess",
            "  notation and applied to current game."
        };

    private static void help(String[] msg) {
        for (String rivi : msg) {
            System.out.println(rivi);
        }
    }

    public static void main(String[] argv) {
        boolean fen = false;
        Chess game = null;
        String args = "";
        String input = null;
        String current = null;
        ArrayList<String> history = new ArrayList<String>();

        try {
            game = new Chess(argv);
        } catch (IllegalArgumentException e) {
            game = new Chess();
        }

        for (int i = 0; i < argv.length; i++) {
            args += " " + argv[i];
            if (i >= 5) {
                break;
            }
        }

        if (argv.length > 0) {
            System.out.format("\nInput:\n %s\n", args.substring(1));
            System.out.format("Interpretation:\n %s\n", game.toFen());
        }

        System.out.print(game + game.prompt());

        while (true) {
            current = game.toFen();
            input = lukija.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(game.prompt());
                fen = false;
                continue;
            }

            if (fen) {
                Chess reset;

                try {
                    reset = new Chess(input);
                    game = reset;
                    history.clear();
                }
                catch (IllegalArgumentException e) {
                    System.out.println("\nUnable to parse given FEN!");
                }

                System.out.format("\nInput:\n %s\n", input);
                System.out.format("Interpretation:\n %s\n", game.toFen());
                System.out.print(game + game.prompt());
                fen = false;
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
                fen = true;
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
                } else {
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
                continue;
            }

            System.out.print(game + game.prompt());
        }

        System.out.println();
    }
}
