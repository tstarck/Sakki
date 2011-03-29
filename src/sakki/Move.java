/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sakki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tuomas Starck
 */
class Move {
    //                      1:piece   2:from  3:x 4:to        5:prom    6:
    private String regex = "([NBRQK]?)([a-h]?)(x?)([a-h][1-8])(=[NBRQ])?(#?+?)[!?]*";
    private Pattern ptrn;
    private Matcher match;

    private Boolean castleKingside;
    private Boolean castleQueenside;

    private String piece;
    private String promote;
    private Boolean capture;
    private Boolean check;
    private Boolean mate;
    private Coord from;
    private Coord to;

    public Move(String str) {
        ptrn = Pattern.compile(regex);
        match = ptrn.matcher(str);

        if (match.find()) {
            // TODO
        }
        else if (str.matches("O-O")) {
            castleKingside = true;
        }
        else if (str.matches("O-O-O")) {
            castleQueenside = true;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

}