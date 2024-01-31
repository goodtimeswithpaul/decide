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
    public void testLIC2() {
        Point2D[] points1 = {new Point2D.Double(0, 0),
                            new Point2D.Double(0, 1),
                            new Point2D.Double(0, 5)};

        Point2D[] points2 = {new Point2D.Double(0, 0)};

        Point2D[] points3 = {new Point2D.Double(0, 1),
                new Point2D.Double(0, 1),
                new Point2D.Double(0, 1),
                new Point2D.Double(100, 100)};
        
        // Positive test
        assertTrue(Main.lic2holds(points1, 3));

        // Negative tests
        assertFalse(Main.lic2holds(points3, 2));
        assertFalse(Main.lic2holds(points1, 3.1));

        // Params checking
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic2holds(points3, 1000);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic0holds(points2, 1);
        });
    }

    @Test
    public void testLIC5() {
        Point2D[] points1 = {new Point2D.Double(0, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(0, 5)};

        Point2D[] points2 = {new Point2D.Double(0, 0),
                new Point2D.Double(65, 1),
                new Point2D.Double(5, 5)};

        Point2D[] points3 = {new Point2D.Double(1000, 1000)};

        Point2D[] points4 = {new Point2D.Double(0, 0),
                new Point2D.Double(5, 1),
                new Point2D.Double(45, 5)};


        assertFalse(Main.lic5holds(points1));
        assertTrue(Main.lic5holds(points2));
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic5holds(points3);
        });
        assertFalse(Main.lic5holds(points4));
    }

    @Test
    public void testLIC7() {
        Point2D[] points1 = {new Point2D.Double(0,0),
                new Point2D.Double(0,1),
                new Point2D.Double(0,0),
                new Point2D.Double(0,0),
                new Point2D.Double(0,0),
                new Point2D.Double(0,10),
                new Point2D.Double(0,0)};

        // The second and the 6th point are more than 8 away
        assertTrue(Main.lic7holds(points1, 3, 8));
        // The first and the 6th point are more than 8 away
        assertTrue(Main.lic7holds(points1, 4, 8));
        // No two points should match
        assertFalse(Main.lic7holds(points1, 3, 9));
        assertFalse(Main.lic7holds(points1, 5, 8));

        // The function should not accept null as points
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic7holds(null, 1000, 1000);
        });

        // With kPoints = 7, it should be false, even with length1 = 0
        assertFalse(Main.lic7holds(points1, 7, 0));

        // With two points, it should return false whatever the other parameters are
        Point2D[] points2 = {new Point2D.Double(0,0), new Point2D.Double(1,0)};
        assertFalse(Main.lic7holds(points2, 1, 0.5));
        assertFalse(Main.lic7holds(points2, 1, -6));
        assertFalse(Main.lic7holds(points2, 8, 0.5));

    }
}