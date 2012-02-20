package sakki;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
