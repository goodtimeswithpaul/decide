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
    public void testLIC1(){
        Point2D[] testPoints = {new Point2D.Double(0,1), new Point2D.Double(1,0), new Point2D.Double(1,1)};
        // Positive test, the points are not contained by the circle of the given radius
        assertTrue(Main.lic1holds(testPoints,0.5));
        // Negative test, the points are contained by the circle of the given radius
        assertFalse(Main.lic1holds(testPoints,1));
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
    public void testLIC3() {
        Point2D[] points1 = {
            new Point2D.Double(0, 0),
            new Point2D.Double(0, 2),
            new Point2D.Double(2, 0)
        };

        double area1 = 1;
        double area2 = 3;
        
        assertTrue(Main.lic3holds(points1, area1));
        assertFalse(Main.lic3holds(points1, area2));
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic3holds(points1, -1);
        });
    }

    @Test
    public void testLIC4() {
        Point2D[] points1 = {new Point2D.Double(0, 0),
                            new Point2D.Double(0, -1),
                            new Point2D.Double(-1, 0)};

        Point2D[] points2 = {new Point2D.Double(0, 0),
                            new Point2D.Double(0, 1),
                            new Point2D.Double(2, 4)};

        
        // Negative test
        assertFalse(Main.lic4holds(points1, 2, 3));
        assertFalse(Main.lic4holds(points2, 2, 2));

        // Positive tests
        assertTrue(Main.lic4holds(points1, 3, 2));

        // Params checking
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic4holds(points2, 1, 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic4holds(points1, 2, 4);
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
    public void testLIC6() {
        Point2D[] points = {new Point2D.Double(0,0),
            new Point2D.Double(0,1),
            new Point2D.Double(1,0),
            new Point2D.Double(2,0),
            new Point2D.Double(5,0),
            new Point2D.Double(0,10),
            new Point2D.Double(0,10),
            new Point2D.Double(0,0)};
        
        // Positive Test, one of the points lies a greater distance than dist from the line joining the first and last of the nPoints
        assertTrue(Main.lic6holds(points, 8, 4, 3));
        // Positve Test, for when start and end are identical
        assertTrue(Main.lic6holds(points, 8, 8, 3));
        // Negative Test, no point lies further from the line than 100
        assertFalse(Main.lic6holds(points, 8, 4, 100));
        // Throws exception test, nPoints is larger than numPoints
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic6holds(points, 8, 9, 10);
        });
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

    @Test
    public void testLIC11() {
        Point2D[] points1 = {new Point2D.Double(0, 0),
            new Point2D.Double(0, 1),
            new Point2D.Double(1, 2),
            new Point2D.Double(-1, 3)};

        Point2D[] points2 = {new Point2D.Double(0, 0),
            new Point2D.Double(0, 1),
            new Point2D.Double(1, 2),
            new Point2D.Double(2, 3)};
        
        // Positive tests
        assertTrue(Main.lic11holds(points1, 1));
        
        // Negative test
        assertFalse(Main.lic11holds(points2, 1));
        
         // Params checking
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic11holds(points2, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic11holds(points1, 3);
        });
    }

    @Test
    public void testLIC8() {
        Point2D[] testPoints = {new Point2D.Double(0,1),
            new Point2D.Double(5,5),
            new Point2D.Double(6,1),
            new Point2D.Double(1,0),
            new Point2D.Double(3,4),
            new Point2D.Double(8,1),
            new Point2D.Double(1,1)};
        // Positive Test, there exists a set of 3 data points that cannot be contained by a circle of radius1
        assertTrue(Main.lic8holds(testPoints, 7, 2, 2,0.5));
        // Negative Test, there does not exists a set of 3 data points that cannot be contained by a circle of radius1
        assertFalse(Main.lic8holds(testPoints, 7, 2, 2,1));
        // Negative Test
        assertFalse(Main.lic8holds(testPoints, 7, 5, 3, 0.5));
    }

    @Test
    public void testLIC10() {
        Point2D[] p1 = {
            new Point2D.Double(0, 0),
            new Point2D.Double(0, 2),
            new Point2D.Double(2, 0),
            new Point2D.Double(0, 4),
            new Point2D.Double(0, 3),
            new Point2D.Double(1, 4),
        };

        double area1 = 1;
        int e_pts = 1;
        int f_pts = 2;


        assertTrue(Main.lic10holds(p1, area1, e_pts, f_pts));
        assertFalse(Main.lic10holds(p1, area1 * 5, e_pts, f_pts));
    }
  
    @Test
    public void testLIC13() {
        Point2D[] testPoints = {new Point2D.Double(0,1),
            new Point2D.Double(5,5),
            new Point2D.Double(6,1),
            new Point2D.Double(1,0),
            new Point2D.Double(3,4),
            new Point2D.Double(8,1),
            new Point2D.Double(1,1)};
        // Positive Test, LIC8 returns true and there exists a set of 3 data points that can be contained by a circle of radius2
        assertTrue(Main.lic13holds(testPoints, 7, 2, 2,0.5, 1));
        // Negative Test, LIC8 returns true but there does not exist a set of 3 data points that can be contained by a circle of radius2
        assertFalse(Main.lic13holds(testPoints, 7, 2, 2,0.5, 0.5));
    }

    @Test
    public void testLIC14() {
        Point2D[] points1 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(2, 0),
                new Point2D.Double(0, 4),
                new Point2D.Double(0, 3),
                new Point2D.Double(1, 4),
        };

        Point2D[] points2 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(2, 0),
                new Point2D.Double(0, 4),
                new Point2D.Double(0, 3),
                new Point2D.Double(1, 4),
        };

        Point2D[] points3 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(2, 0),
                new Point2D.Double(0, 4),
        };

        int e_pts = 1;
        int f_pts = 2;
        double area1 = 3.9;
        double area2 = 4.1;

        assertTrue(Main.lic14holds(points1, area1, area2, e_pts, f_pts));
        assertFalse(Main.lic14holds(points1, area1, area1, e_pts, f_pts));
        assertThrows(IllegalArgumentException.class, () -> {
            Main.lic14holds(points2, area1, area1 - area2, e_pts, f_pts);
        });
        assertFalse(Main.lic14holds(points3, area1, area2, e_pts, f_pts));
    }
}