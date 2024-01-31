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

    public void testLIC0() {
        Point2D[] points1 = {new Point2D.Double(0, 0),
                            new Point2D.Double(0, 1),
                            new Point2D.Double(0, 5)};

        Point2D[] points2 = {new Point2D.Double(0, 0)};

        Point2D[] points3 = {new Point2D.Double(0, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(0, 2),
                new Point2D.Double(0, 10),
                new Point2D.Double(15, 1),
                new Point2D.Double(100, 100)};
        // Negative test
        assertFalse(Main.lic0holds(points1, 4));

        // Positive tests
        assertTrue(Main.lic0holds(points1, 3));
        assertTrue(Main.lic0holds(points3, 60));

        // Params checking
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic0holds(points2, 1000);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic0holds(points1, -19);
        });
    }

    @Test
    public void testLIC9() {
        Point2D[] points1 = {new Point2D.Double(0, 0),
            new Point2D.Double(0, 1),
            new Point2D.Double(0, 2),
            new Point2D.Double(0, 3),
            new Point2D.Double(1, 4),
            new Point2D.Double(2, 5)};

        Point2D[] points2 = {new Point2D.Double(0, 0),
            new Point2D.Double(0, 1),
            new Point2D.Double(0, 2),
            new Point2D.Double(0, 3),
            new Point2D.Double(1, 4),
            new Point2D.Double(2, 5)};
        
        // Positive tests
        assertTrue(Main.lic9holds(points1, 1, 1, 0.1));
        
        // Negative test
        assertFalse(Main.lic9holds(points1, 1, 1, 3));
        
         // Params checking
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic9holds(points2, 0, 1, 0.1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic9holds(points1, 1, 0, 0.1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic9holds(points1, 1, 0, 1000);
        });
    }
    public void testExample() {
        assertTrue(true);
    }
}