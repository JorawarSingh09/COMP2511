package test;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import unsw.loopmania.Frame;


public class FrameTest {
    @Test
    public void testGetters() {
        Frame frame = new Frame(1.0, 1.0, 1.0, null, null, 1, 0);
        assertTrue(frame.getCharacterHealth() == 1.0);
        assertTrue(frame.getEnemyHealth() == 1.0);
        assertTrue(frame.getBossHealth() == 1.0);
        assertTrue(frame.getEnemiesLeft() == 1.0);
        assertTrue(frame.getNumOfAlliedSoldiers() == 0);
    }
}
