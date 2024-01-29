public class Main {
    final int VECTOR_SIZE = 15;

    public enum LogicalConnectors {
        ANDD,
        ORR,
        NOTUSED;
    }

    // --- Inputs ---
    int numPoints;
    int points;
    // parameters
    LogicalConnectors[][] logConMatrix = new LogicalConnectors[VECTOR_SIZE][VECTOR_SIZE];
    boolean[] prelimUnlockVector = new boolean[VECTOR_SIZE];

    // --- Outputs ---
    boolean launch;
    boolean[] conditionsMetVector = new boolean[VECTOR_SIZE];
    boolean[][] prelimUnlockMatrix = new boolean[VECTOR_SIZE][VECTOR_SIZE];
    boolean[] finalUnlockVector = new boolean[VECTOR_SIZE];

    static public boolean lic0holds() {
        return false;
    }

    static public boolean lic1holds() {
        return false;
    }

    static public boolean lic2holds() {
        return false;
    }

    static public boolean lic3holds() {
        return false;
    }

    static public boolean lic4holds() {
        return false;
    }

    static public boolean lic5holds() {
        return false;
    }

    static public boolean lic6holds() {
        return false;
    }

    static public boolean lic7holds() {
        return false;
    }

    static public boolean lic8holds() {
        return false;
    }

    static public boolean lic9holds() {
        return false;
    }

    static public boolean lic10holds() {
        return false;
    }

    static public boolean lic11holds() {
        return false;
    }

    static public boolean lic12holds() {
        return false;
    }

    static public boolean lic13holds() {
        return false;
    }

    static public boolean lic14holds() {
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}