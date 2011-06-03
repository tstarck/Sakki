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

	private static final String help = "FIXME\n  new   Initiates an new game";

    private static void help(boolean longform) {
        if (longform) {
            System.out.println(help);
        }
        else {
            System.out.println("Usage: h[elp], fen, new, u[ndo], q[uit]");
            System.out.println("Type help for more information!");
        }
    }

    public static void main(String[] argv) {
        boolean tryFen = false;
        Chess game = null;
        String args = "";
        String input = null;
        ArrayList<String> history = new ArrayList<String>();

        try {
            game = new Chess(argv);
        }
        catch (IllegalArgumentException e) {
            game = new Chess();
        }

        for (int i=0; i<argv.length; i++) {
            args += " " + argv[i];
            if (i >= 5) break;
        }

        if (argv.length > 0) {
            System.out.format("\nInput:\n %s\n", args.substring(1));
            System.out.format("Interpretation:\n %s\n", game.toFen());
        }

        System.out.print(game + game.prompt());

        while (true) {
            input = lukija.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(game.prompt());
                tryFen = false;
                continue;
            }

            if (tryFen) {
                Chess reset;

                try {
                    reset = new Chess(input);
                    game = reset;
                }
                catch (IllegalArgumentException e) {
                    System.out.println("");
                }

                System.out.println(game + game.prompt());
                tryFen = false;
                continue;
            }

            if (input.equals("h")) {
                help(false);
                System.out.print(game.prompt());
                continue;
            }

            if (input.equals("help")) {
                help(true);
                System.out.println(game.prompt());
                continue;
            }

            if (input.equals("fen")) {
                System.out.format("Current FEN:\n %s\n", game.toFen());
                System.out.format("Set new FEN?\n >");
                tryFen = true;
                continue;
            }

            if (input.equals("new")) {
                game = new Chess();
                System.out.print(game + game.prompt());
                continue;
            }

            if (input.equals("u") || input.equals("undo")) {
                int index = history.size()-2;

                if (index < 0) {
                    System.out.println("\nNo available history");
                }
                else {
                    game = new Chess(history.remove(index));
                    System.out.print(game + game.prompt());
                }

                continue;
            }

            if (input.equals("q") || input.equals("quit")) {
                break;
            }

            try {
                game.move(input);
            }
            catch (MoveException me) {
                System.out.println("\n" + me);
                System.out.print(game.prompt());
                continue;
            }

            history.add(game.toFen());

            /* * * * DEBUG * * * */
            for (String tmp : history) {
                System.out.println(tmp);
            } System.out.println("");
            /* * * * DEBUG * * * */

            System.out.print(game + game.prompt());
        }

        System.out.println();
    }
}
