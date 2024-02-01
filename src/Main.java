import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;

public class Main {
    final static int VECTOR_SIZE = 15;
    final static double pi = 3.1415626535;

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
    public static boolean launch;
    public static boolean[] conditionsMetVector = new boolean[VECTOR_SIZE];
    public static boolean[][] prelimUnlockMatrix = new boolean[VECTOR_SIZE][VECTOR_SIZE];
    public static boolean[] finalUnlockVector = new boolean[VECTOR_SIZE];

    public static void getInput(String filename) {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(filename));

            numPoints = Integer.parseInt(br.readLine());

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

                for(int j = 0; j < VECTOR_SIZE; j++) {
                    logConMatrix[i][j] = LogicalConnectors.valueOf(column[j]);
                }
            }

            for (int i = 0; i < VECTOR_SIZE; i++) {
                String value = br.readLine();
                prelimUnlockVector[i] = Boolean.parseBoolean(value);
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static double getAngle(Point2D vertex, Point2D before, Point2D after){

        //vetor 1
        double vec1X = before.getX() - vertex.getX();
        double vec1Y = before.getY() - vertex.getY();
        //vector 2
        double vec2X = after.getX() - vertex.getX();
        double vec2Y = after.getY() - vertex.getY();

        //Dot product
        double dotProduct = vec1X*vec2X + vec1Y*vec2Y;

        //Magnitude of vectors
        double mag1 = Math.sqrt(vec1X*vec1X + vec1Y*vec1Y);
        double mag2 = Math.sqrt(vec2X*vec2X + vec2Y*vec2Y);

        double angleInRadians = Math.acos(dotProduct / (mag1*mag2));

        return angleInRadians;
    }

    public static boolean lic0holds(Point2D[] points, double length1) {
        if (!(0 <= length1 && points.length >= 2)) {
            throw new IllegalArgumentException("At least 2 points are required, and length should be positive or zero.");
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].distance(points[i+1]) > length1) {
                return true;
            }
        }
        return false;
    }

    public static boolean lic1holds(Point2D[] points, double radius1) {

        if(radius1 < 0){
            return false;
        }
        Point2D p1, p2, p3;
        double[] angles = new double[3];
        for (int i = 0; i < points.length-2; i++) {

            p1 = points[i];
            p2 = points[i+1];
            p3 = points[i+2];

            angles[0] = getAngle(p1, p2, p3);
            angles[1] = getAngle(p2, p1, p3);
            angles[2] = getAngle(p3, p2, p1);
            
            for (int j = 0; j < 3; j++) {
                if (angles[j] > pi) {
                    angles[j] = 2*pi - angles[j];
                }

                if (angles[j] > pi/2) {
                    
                    if(j==0){ // Angle when p1 is vertex
                        if (calculateDistance(p2, p3)/2 > radius1) {
                            return true;
                         }
                    } else if(j==1){ //Angle when p2 is vertex
                        if (calculateDistance(p1, p3)/2 > radius1) {
                            return true;
                        }
                    } else { // Angle when p3 is vertex
                        if (calculateDistance(p1, p2)/2 > radius1){
                            return true;
                        }
                    }
                }
            }

            if (getCircumradius(p1, p2, p3) > radius1) {
                return true;
            }
            
        }
        return false;
    }

    public static boolean lic2holds(Point2D[] points, double epsilon) {
        if((points.length < 3) || epsilon < 0 || epsilon >= pi) return false;
        for(int i = 1; i <(points.length - 1); i++){
            if(!(points[i].equals(points[i-1])) && !(points[i].equals(points[i+1]))){
                double angle = getAngle(points[i], points[i-1], points[i+1]);
                double angleR = Math.abs(Math.toRadians(angle));
                if(angleR > (pi + epsilon) || angleR < (pi - epsilon)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean lic3holds(Point2D[] points, double area1) {
        if (points == null || points.length < 3) {
            throw new IllegalArgumentException("At least three points are required.");
        }

        Point2D p1, p2, p3;

        for (int i = 0; i < points.length - 2; i++) {
            p1 = points[i];
            p2 = points[i + 1];
            p3 = points[i + 2];

            if (calculateTriangleArea(p1, p2, p3) > area1) {
                return true;
            }
        }

        return false;
    }

    public static boolean lic4holds(Point2D[] points, int qPoints, int quads) {
        if (!((2 <= qPoints && qPoints <= numPoints) && (1 <= quads && quads <= 3))        ) {
            return false;
        }

        int[] quadCount = new int[4];

        for (int i = 0; i < points.length - qPoints; i++) {
            
            for (int j = 0; j < 4; j++){
                quadCount[j] = 0;
            }

            for (int j = 0; j < qPoints; j++) {
                double x = points[i+j].getX();
                double y = points[i+j].getY();

                if (x >= 0 && y >= 0) {
                    quadCount[0] = 1; // Quadrant I
                } else if (x < 0 && y >= 0) {
                    quadCount[1] = 1; // Quadrant II
                } else if (x >= 0 && y < 0) {
                    quadCount[2] = 1; // Quadrant III
                } else {
                    quadCount[3] = 1; // Quadrant IV
                }
            }

            if (IntStream.of(quadCount).sum() > quads){
                return true;
            }
        }
        
        return false;
    }

    public static boolean lic5holds(Point2D[] points) {
        if (points == null || !(points.length >= 2)) {
            throw new IllegalArgumentException("At least 2 points are required.");
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i + 1].getX() - points[i].getX() < 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean lic6holds(Point2D[] points, int numPoints, int nPoints, double dist) {
        return false;
    }

    public static boolean lic7holds(Point2D[] points, int kPoints, double length1) {
        if (points == null) {
            throw new IllegalArgumentException("points is null.");
        }

        if (points.length < Math.max(3, (kPoints + 2))) {
            return false;
        }

        if (!(1 <= kPoints && kPoints <= points.length - 2) || !(length1 >= 0)) {
            throw new IllegalArgumentException("kPoints is invalid.");
        }

        for (int i = 0; i < points.length - kPoints - 1; i++) {
            if (points[i].distance(points[i + kPoints + 1]) > length1) {
                return true;
            }
        }

        return false;
    }

    public static boolean lic8holds(Point2D[] points, int numPoints, int aPoints, int bPoints, double radius1) {
        return false;
    }

    public static boolean lic9holds(Point2D[] points, int numPoints, int cPoints, int dPoints, double epsilon) {
        return false;
    }

    public static boolean lic10holds(Point2D[] points, int numPoints, int ePoints, int fPoints, double area1) {
        return false;
    }

    public static boolean lic11holds(Point2D[] points, int numPoints, int gPoints) {
        return false;
    }

    public static boolean lic12holds(Point2D[] points, int numPoints, int kPoints, double length1, double length2) {
        return false;
    }

    public static boolean lic13holds(Point2D[] points, int numPoints, int aPoints, int bPoints, double radius1, double radius2) {
        return false;
    }

    public static boolean lic14holds(Point2D[] points, int numPoints, int ePoints, int fPoints, double area1, double area2) {
        return false;
    }

    private static double calculateTriangleArea(Point2D p1, Point2D p2, Point2D p3) {
        return 0.5 * Math.abs((p1.getX() * (p2.getY() - p3.getY()) +
                               p2.getX() * (p3.getY() - p1.getY()) +
                               p3.getX() * (p1.getY() - p2.getY())));
    }

    private static double calculateDistance(Point2D p1, Point2D p2) {
        return Math.sqrt(Math.pow(p1.getX()-p2.getX(),2) + Math.pow(p1.getY()-p2.getY(),2));
    }

    private static double getCircumradius(Point2D p1, Point2D p2, Point2D p3) {
        double lengthA = calculateDistance(p1,p2);
        double lengthB = calculateDistance(p1,p3);
        double lengthC = calculateDistance(p2,p3);

        // Find area using Heron's formula
        double s = (lengthA + lengthB + lengthC)/2;
        double area = Math.sqrt(s*(s-lengthA)*(s-lengthB)*(s-lengthC));

        return (lengthA*lengthB*lengthC)/(4*area);

    }

    private static void calcCMV() {
        conditionsMetVector = new boolean[] {
            lic0holds(points, parameters.getLength1()),
            lic1holds(points, parameters.getRadius1()),
            lic2holds(points, parameters.getEpsilon()),
            lic3holds(points, parameters.getArea1()),
            lic4holds(points, parameters.getQ_pts(), parameters.getQuads()),
            lic5holds(points),
            lic6holds(points, numPoints, parameters.getN_pts(), parameters.getDist()),
            lic7holds(points, parameters.getK_pts(), parameters.getLength1()),
            lic8holds(points, numPoints, parameters.getA_pts(), parameters.getB_pts(), parameters.getRadius1()),
            lic9holds(points, numPoints, parameters.getC_pts(), parameters.getD_pts(), parameters.getEpsilon()),
            lic10holds(points, numPoints, parameters.getE_pts(), parameters.getF_pts(), parameters.getArea1()),
            lic11holds(points, numPoints, parameters.getG_pts()),
            lic12holds(points, numPoints, parameters.getK_pts(), parameters.getLength1(), parameters.getLength2()),
            lic13holds(points, numPoints, parameters.getA_pts(), parameters.getB_pts(), parameters.getRadius1(), parameters.getRadius2()),
            lic14holds(points, numPoints, parameters.getE_pts(), parameters.getF_pts(), parameters.getArea1(), parameters.getArea2())
        };
    }

    // Combining LCM and CMV to calculate PUM
    // Outerloop: go through row. Innerloop: go through column.
    private static void calcPUM() {
        for (int i = 0; i < VECTOR_SIZE*VECTOR_SIZE; i++) {
            for (int j = 0; j < VECTOR_SIZE; j++) {
                if (logConMatrix[i][j].equals(LogicalConnectors.ANDD)) { // If it's a AND
                    prelimUnlockMatrix[i][j] = conditionsMetVector[i] && conditionsMetVector[j];
                }else if (logConMatrix[i][j].equals(LogicalConnectors.ORR)) { // If it's a OR
                    prelimUnlockMatrix[i][j] = conditionsMetVector[i] || conditionsMetVector[j];
                }else if (logConMatrix[i][j].equals(LogicalConnectors.ORR)) { // If it's a NOTUSED
                    prelimUnlockMatrix[i][j] = true;
                }else {
                    throw new IllegalArgumentException("There is something wrong in the LCM");
                }
            }
        }
    }
    public static void main(String[] args) {
        getInput("testfiles/testfile.txt");
        calcCMV();
        calcPUM();
    }
}