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
    boolean launch;
    boolean[] conditionsMetVector = new boolean[VECTOR_SIZE];
    boolean[][] prelimUnlockMatrix = new boolean[VECTOR_SIZE][VECTOR_SIZE];
    boolean[] finalUnlockVector = new boolean[VECTOR_SIZE];

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
        if (epsilon < 0 || epsilon >= pi) {
            throw new IllegalArgumentException("At least three points are required, and 0 <= epsilon < pi");
        }
        if(points.length < 3){
            return false;
        }
        for (int i = 1; i <(points.length - 1); i++) {
            if (!(points[i].equals(points[i-1])) && !(points[i].equals(points[i+1]))) {
                double angle = getAngle(points[i], points[i-1], points[i+1]);
                double angleR = Math.abs(Math.toRadians(angle));
                if (angleR > (pi + epsilon) || angleR < (pi - epsilon)) {
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
        if (qPoints < 2 || qPoints > points.length || quads < 1 || quads > 3) {
            throw new IllegalArgumentException("q_pts must be equal to or greater than 2, equal to or smaller than the number of points. quad must be equal to or greater than 1, equal to or greater than 3");
        }

        int[] quadCount = new int[4];

        for (int i = 0; i <= (points.length - qPoints); i++) {
            
            for (int j = 0; j < 4; j++){
                quadCount[j] = 0;
            }

            for (int j = 0; j < qPoints; j++) {
                double x = points[i + j].getX();
                double y = points[i + j].getY();

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

            if (IntStream.of(quadCount).sum() > quads) {
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

        if(numPoints < 5 || 1 > aPoints || 1 > bPoints || aPoints + bPoints > numPoints-3){
            return false;
        }
        Point2D p1, p2, p3;
        double[] angles = new double[3];
        for (int i = 0; i < points.length-(2+aPoints+bPoints); i++) {

            p1 = points[i];
            p2 = points[i + aPoints + 1];
            p3 = points[i + aPoints + bPoints +2];

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

    public static boolean lic9holds(Point2D[] points, int cPoints, int dPoints, double epsilon) {
        if (1 > cPoints || 1 > dPoints || (cPoints + dPoints) > (points.length-3)) {
            throw new IllegalArgumentException("At least 5 points are required, c_pts and d_pts must be eqaul to or great than zero, their sum must be equal to or smaller than the number or point minus 3");
        }

        if (points.length < 5) {
            return false;
        }

        int totalset = cPoints + dPoints + 2;
        for (int i = 0; i < points.length - totalset; i++) {
            if (!(points[i + cPoints + 1].equals(points[i]) && points[i + cPoints + dPoints + 2].equals(points[i]))) {
                double angleR = Math.abs(getAngle(points[i + cPoints + 1], points[i], points[i + cPoints + dPoints + 2]));
                if(angleR <(pi - epsilon) || angleR > (pi + epsilon)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean lic10holds(Point2D[] points, int numPoints, int ePoints, int fPoints, double area1) {
        return false;
    }

    public static boolean lic11holds(Point2D[] points, int gPoints) {
        if (1 > gPoints || (points.length - 2) < gPoints) {
            throw new IllegalArgumentException("At least 3 points are required, g_pts must be equal to or smaller than the number or point minus 2\"");
        }

        for (int i = 0; i < points.length - gPoints - 1; i++) {
            int j = i + gPoints + 1;
            if (points[i].getX() > points[j].getX()) {
                return true;
            }            
        }

        return false;
    }

    public static boolean lic12holds(Point2D[] points, int kPoints, double length1, double length2) {
        if (points == null) {
            throw new IllegalArgumentException("points must not be null.");
        }

        if (!(length2 >= 0)) {
            throw new IllegalArgumentException("length1 and length2 must be positive.");
        }

        if (points.length < Math.max(3, (kPoints + 2))) {
            return false;
        }

        for (int i = 0; i < points.length - kPoints - 1; i++) {
            if (points[i].distance(points[i + kPoints + 1]) > length1) {
                for (int j = 0; j < points.length - kPoints - 1; j++) {
                    if (points[j].distance(points[j + kPoints + 1]) < length2) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static boolean lic13holds(Point2D[] points, int numPoints, int aPoints, int bPoints, double radius1, double radius2) {
        if(!lic8holds(points, numPoints, aPoints, bPoints, radius1)){
            return false;
        }
        if(radius2 < 0){
            return false;
        }
        Point2D p1, p2, p3;
        double[] angles = new double[3];
        for (int i = 0; i < points.length-(2+aPoints+bPoints); i++) {

            p1 = points[i];
            p2 = points[i + aPoints + 1];
            p3 = points[i + aPoints + bPoints +2];

            angles[0] = getAngle(p1, p2, p3);
            angles[1] = getAngle(p2, p1, p3);
            angles[2] = getAngle(p3, p2, p1);

            for (int j = 0; j < 3; j++) {
                if (angles[j] > pi) {
                    angles[j] = 2*pi - angles[j];
                }

                if (angles[j] > pi/2) {

                    if(j==0){ // Angle when p1 is vertex
                        if (calculateDistance(p2, p3)/2 <= radius2) {
                            return true;
                         }
                    } else if(j==1){ //Angle when p2 is vertex
                        if (calculateDistance(p1, p3)/2 <= radius2) {
                            return true;
                        }
                    } else { // Angle when p3 is vertex
                        if (calculateDistance(p1, p2)/2 <= radius2){
                            return true;
                        }
                    }
                }
            }

            if (getCircumradius(p1, p2, p3) <= radius2) {
                return true;
            }

        }
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

    public static void main(String[] args) {
        getInput("testfiles/testfile.txt");
        System.out.println(lic2holds(points, parameters.getEpsilon()));
    }
}