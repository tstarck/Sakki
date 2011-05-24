/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Test class for running Sakki project.
 *
 * @author Tuomas Starck
 */
public class Sakki {
    /**
     * @param args the command line arguments
     */
    private static Scanner lukija = new Scanner(System.in);

    private static void help() {
        System.out.println("Usage: h[elp], new, u[ndo], q[uit]");
    }

    public static void main(String[] args) {
        Chess game = null;
        String input = null;
        ArrayList<String> history = new ArrayList<String>();

        game = new Chess();

        System.out.print(game + game.prompt());

        do {
            input = lukija.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(game.prompt());
                continue;
            }

            if (input.equals("h") || input.equals("help")) {
                help();
                System.out.print(game.prompt());
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
                history.add(game.toFen());
                System.out.print(game + game.prompt());
            }
            catch (MoveException me) {
                System.out.println("\n" + me);
                System.out.print(game.prompt());
            }
        } while (true);

        System.out.println();
    }
}