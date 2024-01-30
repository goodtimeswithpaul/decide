import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    final static int VECTOR_SIZE = 15;

    public enum LogicalConnectors {
        ANDD,
        ORR,
        NOTUSED;
    }

    // --- Inputs ---
    public static int numPoints;
    public static Point2D[] points;
    public static Parameters parameters;
    public static LogicalConnectors[][] logConMatrix = new LogicalConnectors[VECTOR_SIZE][VECTOR_SIZE];
    public static boolean[] prelimUnlockVector = new boolean[VECTOR_SIZE];

    // --- Outputs ---
    boolean launch;
    boolean[] conditionsMetVector = new boolean[VECTOR_SIZE];
    boolean[][] prelimUnlockMatrix = new boolean[VECTOR_SIZE][VECTOR_SIZE];
    boolean[] finalUnlockVector = new boolean[VECTOR_SIZE];

    public static void getInput(String filename) {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(filename));

            numPoints = Integer.parseInt(br.readLine()); //Line 1

            if(numPoints < 3) {
                br.close();
                throw new IllegalArgumentException("At least three points are required.");
            }

            points = new Point2D[numPoints];
            
            // Putting the points into array
            for (int i = 0; i < numPoints; i++) {
                String[] coordinates = br.readLine().split(" ");
                points[i] = new Point2D.Double(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
            }
            
            //Filling in the parameters, there are 8 doubles and 11 ints
            double[] paramDoubles = new double[8];
            int[] paramInts = new int[11];

            for (int i = 0; i < 8; i++) {
                paramDoubles[i] = Double.parseDouble(br.readLine());
            }

            for (int i = 0; i < 11; i++) {
                paramInts[i] = Integer.parseInt(br.readLine());
            }

            parameters = new Parameters(paramDoubles, paramInts);
            
            // Putting LCM values into the martix
            for (int i = 0; i < VECTOR_SIZE; i++) {
                String row = br.readLine();
                String[] column = row.split(" ");

                for(int j = 0; j < VECTOR_SIZE; j++){
                    logConMatrix[i][j] = LogicalConnectors.valueOf(column[j]);
                }
            }

            for (int i = 0; i < VECTOR_SIZE; i++) {
                String value = br.readLine();
                prelimUnlockVector[i] = Boolean.parseBoolean(value);
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public boolean lic0holds() {
        return false;
    }

    public boolean lic1holds() {
        return false;
    }

    public boolean lic2holds() {
        return false;
    }

    public static boolean lic3holds(Point2D[] points, double area) {
        if (points == null || points.length < 3) {
            throw new IllegalArgumentException("At least three points are required.");
        }

        Point2D p1, p2, p3;

        for (int i = 0; i < points.length - 2; i++) {
            p1 = points[i];
            p2 = points[i + 1];
            p3 = points[i + 2];

            if (calculateTriangleArea(p1, p2, p3) > area) {
                return true;
            }
        }

        return false;
    }

    public boolean lic4holds() {
        return false;
    }

    public boolean lic5holds() {
        return false;
    }

    public boolean lic6holds() {
        return false;
    }

    public boolean lic7holds() {
        return false;
    }

    public boolean lic8holds() {
        return false;
    }

    public boolean lic9holds() {
        return false;
    }

    public boolean lic10holds() {
        return false;
    }

    public boolean lic11holds() {
        return false;
    }

    public boolean lic12holds() {
        return false;
    }

    public boolean lic13holds() {
        return false;
    }

    public boolean lic14holds() {
        return false;
    }

    private static double calculateTriangleArea(Point2D p1, Point2D p2, Point2D p3) {
        return 0.5 * Math.abs((p1.getX() * (p2.getY() - p3.getY()) +
                               p2.getX() * (p3.getY() - p1.getY()) +
                               p3.getX() * (p1.getY() - p2.getY())));
    }

    public static void main(String[] args) {
        getInput("testfiles/testfile.txt");
        System.out.println(lic3holds(points, parameters.getArea1()));
        
    }
}