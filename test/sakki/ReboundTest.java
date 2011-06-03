package sakki;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tuomas Starck
 */
public class ReboundTest {
    public ReboundTest() {
    }

    /**
     * Rebound class is really too simple to be tested seriously.
     */
    @Test
    public void trivialTests() {
        Rebound re = new Rebound();

        re.promotionAvailable();
        re.disableCastling("Kk");
        re.setEnpassant(new Coord("a1"));

        assertTrue(re.canPromote());
        assertEquals(re.castlingObstacle(), "Kk");
        assertEquals(re.getEnpassant().toString(), "a1");
    }
}
