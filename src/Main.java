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

    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}