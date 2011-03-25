/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sakki;

/**
 *
 * @author tljstarc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Peli alkaa");
        Peli peli = new Peli();
        peli.move("Nf6");
        System.out.println("Peli lopppui");
    }
}
