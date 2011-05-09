/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

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

    private static boolean validateFen(String[] fenArray) {
        if (fenArray.length != 6) return false;

        if (!fenArray[0].matches("[1-8/pPrRnNbBqQkK]+")) return false;

        if (!fenArray[1].matches("[wb]")) return false;

        try {
            Integer.parseInt(fenArray[4]);
            Integer.parseInt(fenArray[5]);
        }
        catch (NumberFormatException e) {
            return false;
        }

        System.out.println("ArgFen on tosi :-o");

        return true;
    }

    public static void main(String[] args) {
        Chess game = null;
        String input = null;
        boolean loop = true;

        if (validateFen(args)) {
            game = new Chess(args);
        }
        else {
            game = new Chess();
        }

        System.out.print(game + game.prompt());

        do {
            input = lukija.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print(game.prompt());
                continue;
            }

            if (input.equals("h") || input.equals("help")) {
                // print usage
            }

            if (input.equals("u") || input.equals("undo")) {
                // game.undo();
            }

            if (input.equals("q") || input.equals("quit")) {
                loop = false;
                break;
            }

            try {
                game.move(input);
                System.out.print(game + game.prompt());
            }
            catch (MoveException me) {
                System.out.println("\n" + me);
                System.out.print(game.prompt());
            }
        } while (loop);

        System.out.println();
    }
}