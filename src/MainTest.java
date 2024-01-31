import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.rules.Timeout;

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
    public void testLIC1Positive(){
        Point2D[] testPoints = {new Point2D.Double(0,1), new Point2D.Double(1,0), new Point2D.Double(1,1)};
        assertTrue(Main.lic1holds(testPoints,0.5));
    }

    @Test
    public void testLIC1Negative(){
        Point2D[] testPoints = {new Point2D.Double(0,1), new Point2D.Double(1,0), new Point2D.Double(1,1)};
        assertFalse(Main.lic1holds(testPoints,1));
    }

    
}