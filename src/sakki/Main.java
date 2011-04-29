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
public class Main {
    /**
     * @param args the command line arguments
     */
    private static Scanner lukija = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Peli alkaa");
        Chess test = new Chess();
        System.out.println(test);
        String input = lukija.nextLine();

        while (!input.isEmpty()) {
            test.move(input.trim());
            System.out.println(test);
            input = lukija.nextLine();
        }

        System.out.println("Peli lopppui");
    }
}