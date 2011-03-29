/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 *
 * @author Tuomas Starck
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Peli alkaa");
        Chess test = new Chess();
        test.move("Nf6");
        System.out.println(test);
        System.out.println("Peli lopppui");
    }
}