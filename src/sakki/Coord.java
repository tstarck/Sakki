/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

/**
 *
 * @author Tuomas Starck
 */
class Coord {
    public int file;
    public int rank;
    private String pretty;
    
    private static final String regex = "[a-h][1-8]";
    private static final String[] lookupTable = {"a", "b", "c", "d", "e", "f", "g", "h"};
    
    public Coord(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        
        if (str.matches(regex)) {
            pretty = str;
            
            String input = String.valueOf(str.charAt(0));
            
            for (int i=0; i<8; i++) {
                if (input.equals(lookupTable[i])) {
                    file = i;
                }
            }
            
            rank = Character.getNumericValue(str.charAt(1));
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    public Coord(int f, int r) {
        file = verify(f);
        rank = verify(r);
        pretty = lookupTable[f] + String.valueOf(r);
    }
    
    private int verify(int x) {
        if (x < 0 || 8 <= x) {
            throw new IllegalArgumentException();
        }
        return x;
    }
    
    public String toString() {
        return pretty;
    }
}