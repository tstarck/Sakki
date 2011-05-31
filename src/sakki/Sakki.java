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

    public static void main(String[] args) {
        boolean tryFen = false;
        Chess game = null;
        String input = null;
        ArrayList<String> history = new ArrayList<String>();

        game = new Chess(args);

        System.out.print(game + game.prompt());

        while (true) {
            input = lukija.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(game.prompt());
                continue;
            }

            if (tryFen) {
                tryFen = false;
                game = new Chess(input);
                System.out.println(game + game.prompt());
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
                tryFen = true;
                System.out.println("FEN>");
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
