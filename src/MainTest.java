import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.rules.Timeout;

import java.awt.geom.Point2D;
import java.util.*;

// Test Class!
public class MainTest {
    @Rule
    // Global variables.
    public Timeout globalTimeout = Timeout.seconds(60);
    public int example;

    @Before
    public void setUp() {
        // If something "global" is needed, ex, set up parameters or something
        example = 1;
    }

    /**
     * Example test
     * all test should be named:
     * testSomethingThatDescribesTheTest()
     */
    @Test
    public void testExample() {
        assertTrue(true);
    }
    @Test
    public void testLIC12() {
        Point2D[] points1 = {new Point2D.Double(0,0),
                new Point2D.Double(0,1),
                new Point2D.Double(0,0),
                new Point2D.Double(0,4),
                new Point2D.Double(0,0),
                new Point2D.Double(0,10)};

        assertTrue(Main.lic12holds(points1, 1, 5, 4));
        assertTrue(Main.lic12holds(points1, 2, 5, 4));
        assertTrue(Main.lic12holds(points1, 3, 5, 4));
        assertFalse(Main.lic12holds(points1, 4, 5, 4));
    }
}